package com.integraObra.integraobra_api_rest.repositories;

import com.integraObra.integraobra_api_rest.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository  extends JpaRepository<Client, Long> {
    //OBTENER LOS CLIENTES PAGINADOS POR TERMINO DE BUSQUEDA(nombre, telefono o mail) SINO TODOS
    Page<Client> findByNameIgnoreCaseContainingOrEmailIgnoreCaseContainingOrPhoneIgnoreCaseContaining(
            String name, String email,  String phone, Pageable pageable
    );

    Client getClientById(Long id);
}
