package com.integraObra.integraobra_api_rest.controllers;

import com.integraObra.integraobra_api_rest.dto.rents.CreateRentRequestDTO;
import com.integraObra.integraobra_api_rest.models.Rent;
import com.integraObra.integraobra_api_rest.services.rents.RentGeneralCrudServiceJPA;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/api/rents")
public class RentController {
    private final RentGeneralCrudServiceJPA rentGeneralCrudServiceJPA;

    public RentController(RentGeneralCrudServiceJPA rentGeneralCrudServiceJPA) {
        this.rentGeneralCrudServiceJPA = rentGeneralCrudServiceJPA;
    }

    @PostMapping
    public ResponseEntity<Rent> createRent(@Valid @RequestBody CreateRentRequestDTO createRentRequestDTO) {
       return ResponseEntity.ok(rentGeneralCrudServiceJPA.createRentForClient(createRentRequestDTO));
    }

}
