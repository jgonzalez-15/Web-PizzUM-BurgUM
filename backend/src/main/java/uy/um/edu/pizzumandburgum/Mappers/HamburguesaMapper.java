package uy.um.edu.pizzumandburgum.Mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.DTOs.Request.HamburguesaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Request.HamburguesaRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.HamburguesaProductoResponseDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.Entidades.Cliente;
import uy.um.edu.pizzumandburgum.Entidades.Hamburguesa;
import uy.um.edu.pizzumandburgum.Entidades.HamburguesaProducto;
import uy.um.edu.pizzumandburgum.Entidades.Producto;
import uy.um.edu.pizzumandburgum.Excepciones.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.Repositorios.ClienteRepository;
import uy.um.edu.pizzumandburgum.Repositorios.ProductoRepository;

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

