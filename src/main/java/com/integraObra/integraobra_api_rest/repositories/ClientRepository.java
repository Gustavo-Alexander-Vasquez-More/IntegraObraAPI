package com.integraObra.integraobra_api_rest.repositories;

import com.integraObra.integraobra_api_rest.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository  extends JpaRepository<Client, Long> {

}
