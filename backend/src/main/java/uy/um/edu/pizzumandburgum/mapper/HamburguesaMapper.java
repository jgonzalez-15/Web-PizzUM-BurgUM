package uy.um.edu.pizzumandburgum.mapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.HamburguesaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.HamburguesaRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaProductoResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;
import uy.um.edu.pizzumandburgum.entities.HamburguesaProducto;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.repository.ClienteRepository;
import uy.um.edu.pizzumandburgum.repository.ProductoRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class HamburguesaMapper {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private HamburguesaProductoMapper hamburguesaProductoMapper;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    public HamburguesaResponseDTO toResponseDTO(Hamburguesa hamburguesa) {
        HamburguesaResponseDTO dto = new HamburguesaResponseDTO();

        dto.setIdCreacion(hamburguesa.getId());
        dto.setCantCarnes(hamburguesa.getCantCarnes());
        dto.setPrecio(hamburguesa.getPrecio());
        if (hamburguesa.getCliente() != null) {
            dto.setCliente(clienteMapper.toResponseDTO(hamburguesa.getCliente()));
        }
        List<HamburguesaProductoResponseDTO> ingredientesDTO = new ArrayList<>();

        if (hamburguesa.getIngredientes() != null && !hamburguesa.getIngredientes().isEmpty()) {
            for (HamburguesaProducto hp : hamburguesa.getIngredientes()) {
                ingredientesDTO.add(hamburguesaProductoMapper.toResponseDTO(hp));
            }
        }

        dto.setIngredientes(ingredientesDTO);

        return dto;
    }

    public Hamburguesa toEntity(HamburguesaRequestDTO dto) {
        Hamburguesa hamburguesa = new Hamburguesa();
        Cliente cliente = clienteRepository.findByEmail(dto.getClienteId()).orElseThrow(ClienteNoExisteException::new);
        hamburguesa.setCliente(cliente);
        List<HamburguesaProducto> ingredientes = new ArrayList<>();

        for (HamburguesaProductoRequestDTO hp: dto.getIngredientes()) {
            HamburguesaProducto hi = new HamburguesaProducto();

            Producto producto = productoRepository.findById(hp.getIdProducto()).orElseThrow(() -> new RuntimeException("Producto no encontrado: " + hp));

            hi.setHamburguesa(hamburguesa);
            hi.setProducto(producto);
            hi.setCantidad(hp.getCantidad());

            ingredientes.add(hi);
        }

        hamburguesa.setIngredientes(ingredientes);
        return hamburguesa;
    }
}

