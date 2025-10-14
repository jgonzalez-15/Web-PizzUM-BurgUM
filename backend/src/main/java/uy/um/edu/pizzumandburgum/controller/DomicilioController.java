package uy.um.edu.pizzumandburgum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.um.edu.pizzumandburgum.service.DomicilioService;

@RestController
@RequestMapping("/api/domicilio")
@CrossOrigin(origins = "http://localhost:5173") // para permitir peticiones desde React
public class DomicilioController {
    @Autowired
    private DomicilioService domicilioService;
}
