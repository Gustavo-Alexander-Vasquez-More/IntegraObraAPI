package com.integraObra.integraobra_api_rest.controllers.exceptions;

import com.integraObra.integraobra_api_rest.services.rents.RentGeneralCrudServiceJPA;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/api/rents")
public class RentController {
    private final RentGeneralCrudServiceJPA rentGeneralCrudServiceJPA;

    public RentController(RentGeneralCrudServiceJPA rentGeneralCrudServiceJPA) {
        this.rentGeneralCrudServiceJPA = rentGeneralCrudServiceJPA;
    }

}
