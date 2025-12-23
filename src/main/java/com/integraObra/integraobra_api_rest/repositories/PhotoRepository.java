package com.integraObra.integraobra_api_rest.repositories;

import com.integraObra.integraobra_api_rest.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByProductIdIn(List<Long> productIds);
    void deleteByProductIdIn(List<Long> productIds);

    // Variante segura usando propiedad anidada
    List<Photo> findByProduct_IdIn(List<Long> productIds);
    void deleteByProduct_IdIn(List<Long> productIds);
}
