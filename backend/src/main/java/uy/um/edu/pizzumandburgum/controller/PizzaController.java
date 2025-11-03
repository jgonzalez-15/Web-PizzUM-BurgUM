package uy.um.edu.pizzumandburgum.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.PizzaRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PizzaResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.ProductoResponseDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.PizzaService;

import java.util.List;

@RestController
@RequestMapping("/api/pizza")
@CrossOrigin(origins = "http://localhost:5173")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @PostMapping("/crear")
    public ResponseEntity<PizzaResponseDTO> crearPizza(@RequestBody PizzaRequestDTO dto, HttpSession sesion) {
        String rol = (String) sesion.getAttribute("rol");

        if (rol == null || !rol.equals("CLIENTE")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        PizzaResponseDTO nuevaPizza = pizzaService.crearPizza(dto);
        return ResponseEntity.ok(nuevaPizza);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PizzaResponseDTO>> mostrarPizzas() {
        List<PizzaResponseDTO> pizzas = pizzaService.listarPizzas();
        return ResponseEntity.ok(pizzas);
    }

    @GetMapping("/{idCreacion}/ingredientes")
    public ResponseEntity<List<ProductoResponseDTO>> mostrarIngredientesPizza(@PathVariable Long idCreacion){
        List<ProductoResponseDTO> ingredientes = pizzaService.obtenerIngredientesPizza(idCreacion);
        return ResponseEntity.ok(ingredientes);
    }

}
