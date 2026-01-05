package com.integraObra.integraobra_api_rest.repositories;

import com.integraObra.integraobra_api_rest.models.Rent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {

    //OBTENER RENTAS POR TERMINO DE BUSQUEDA numero de renta por prefijo, PAGINADOS
    @Query(value = "SELECT r.* FROM rents r " +
            "WHERE CAST(r.number_rent AS CHAR) LIKE CONCAT(:term, '%')",
            nativeQuery = true)
    Page<Rent> findByNumberRentStartingWith(@Param("term") String term, Pageable pageable);

}
