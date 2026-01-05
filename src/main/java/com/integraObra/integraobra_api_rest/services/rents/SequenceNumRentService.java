package com.integraObra.integraobra_api_rest.services.rents;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class SequenceNumRentService {
    private final EntityManager entityManager;

    public SequenceNumRentService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Long generateNextNumber() {
        try {
            Object currentObj = null;
            try {
                currentObj = entityManager.createNativeQuery("SELECT current_value FROM sequence_num_rent_control LIMIT 1 FOR UPDATE").getSingleResult();
            } catch (Exception e) {
                currentObj = null;
            }

            if (currentObj == null) {
                // Insertar fila inicial con valor 500
                entityManager.createNativeQuery("INSERT INTO sequence_num_rent_control (current_value, creation_date) VALUES (:val, :created)")
                        .setParameter("val", 500L)
                        .setParameter("created", LocalDateTime.now())
                        .executeUpdate();
                // leer la fila recién creada con FOR UPDATE
                currentObj = entityManager.createNativeQuery("SELECT current_value FROM sequence_num_rent_control LIMIT 1 FOR UPDATE").getSingleResult();
            }

            Number currentNumber = (Number) currentObj;
            long current = currentNumber.longValue();
            long next = current + 1;

            int updated = entityManager.createNativeQuery("UPDATE sequence_num_rent_control SET current_value = :next WHERE current_value = :current")
                    .setParameter("next", next)
                    .setParameter("current", current)
                    .executeUpdate();

            if (updated == 0) {
                throw new RuntimeException("Concurrent update detected while incrementing rent sequence");
            }

            return next;
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Error generating sequence number", ex);
        }
    }
}
