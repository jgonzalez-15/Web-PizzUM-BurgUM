package uy.um.edu.pizzumandburgum.mapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.HamburguesaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.HamburguesaRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaProductoResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;
import uy.um.edu.pizzumandburgum.entities.HamburguesaProducto;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.repository.ProductoRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class HamburguesaMapper {

    @Autowired
    private ProductoRepository productoRepository;

    public HamburguesaResponseDTO toResponseDTO(Hamburguesa hamburguesa) {
        List<HamburguesaProductoRequestDTO> ingredientesDTO = new ArrayList<>();

        for (HamburguesaProducto hp : hamburguesa.getIngredientes()) {
            HamburguesaProductoRequestDTO dto = new HamburguesaProductoRequestDTO();
            dto.setIdProducto(hp.getProducto().getIdProducto());
            dto.setCantidad(hp.getCantidad());

            ingredientesDTO.add(dto);
        };

        return new HamburguesaResponseDTO(
                hamburguesa.getId(),
                hamburguesa.getCantCarnes(),
                hamburguesa.getPrecio(),
                hamburguesa.isEsFavorita(),
                ingredientesDTO
        );

        }
    public Hamburguesa toEntity(HamburguesaRequestDTO dto) {
        Hamburguesa hamburguesa = new Hamburguesa();
        hamburguesa.setCantCarnes(dto.getCantCarnes());
        hamburguesa.setEsFavorita(dto.isEsFavorita());

        List<HamburguesaProducto> ingredientes = new ArrayList<>();

        for (HamburguesaProductoRequestDTO hp: dto.getIngredientes()) {
            HamburguesaProducto hi = new HamburguesaProducto();

            // Buscar el producto en la BD
            Producto producto = productoRepository.findById(hp.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + hp));

            hi.setHamburguesa(hamburguesa);
            hi.setProducto(producto);
            hi.setCantidad(hi.getCantidad());

            ingredientes.add(hi);
        }

        hamburguesa.setIngredientes(ingredientes);
        return hamburguesa;
    }
}

