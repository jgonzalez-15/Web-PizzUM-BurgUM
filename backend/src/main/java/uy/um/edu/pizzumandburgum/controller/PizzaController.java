package uy.um.edu.pizzumandburgum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.response.PizzaResponseDTO;
import uy.um.edu.pizzumandburgum.service.PizzaService;

@RestController
@RequestMapping("/api/pizza")
@CrossOrigin(origins = "http://localhost:5173") // para permitir peticiones desde React
public class PizzaController {
    @Autowired
    private PizzaService pizzaService;

    @PostMapping("/crear")
    public ResponseEntity<PizzaResponseDTO> crearPizza(@RequestBody PizzaResponseDTO pizza) {
        PizzaResponseDTO nuevaPizza = pizzaService.crearPizza(pizza);
        return ResponseEntity.ok(nuevaPizza);
    }

}
