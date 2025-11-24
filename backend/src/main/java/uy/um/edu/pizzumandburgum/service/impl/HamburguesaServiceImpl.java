package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.HamburguesaRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.ProductoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;
import uy.um.edu.pizzumandburgum.entities.HamburguesaProducto;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.Hamburguesa.CantidadDeCarnesException;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.Hamburguesa.HamburguesaNoEncontradaException;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.Hamburguesa.SinCarneException;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.Hamburguesa.SinPanException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.mapper.HamburguesaMapper;
import uy.um.edu.pizzumandburgum.mapper.ProductoMapper;
import uy.um.edu.pizzumandburgum.repository.ClienteRepository;
import uy.um.edu.pizzumandburgum.repository.HamburguesaRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.HamburguesaProductoService;
import uy.um.edu.pizzumandburgum.service.Interfaces.HamburguesaService;

import java.util.ArrayList;
import java.util.List;

@Service
public class HamburguesaServiceImpl implements HamburguesaService {

    @Autowired
    private HamburguesaRepository hamburguesaRepository;

    @Autowired
    private HamburguesaMapper hamburguesaMapper;

    @Autowired
    private HamburguesaProductoService hamburguesaProductoService;

    @Autowired
    private ProductoMapper productoMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public HamburguesaResponseDTO crearHamburguesa(HamburguesaRequestDTO dto) {
        Cliente cliente = clienteRepository.findByEmail(dto.getClienteId()).orElseThrow(ClienteNoExisteException::new);
        Hamburguesa hamburguesa = hamburguesaMapper.toEntity(dto);

        boolean tienePan = false;
        int totalCarnes = 0;
        float precioTotal = 0;

        for (HamburguesaProducto hp : hamburguesa.getIngredientes()) {
            Producto producto = hp.getProducto();

            if ("Pan".equalsIgnoreCase(producto.getTipo())) {
                tienePan = true;
            } else if ("Hamburguesa".equalsIgnoreCase(producto.getTipo())) {
                totalCarnes += hp.getCantidad();
            }

            precioTotal += producto.getPrecio() * hp.getCantidad();
        }

        if (totalCarnes == 0) {
            throw new SinCarneException();
        }
        if (totalCarnes > 3) {
            throw new CantidadDeCarnesException();
        }
        if (!tienePan) {
            throw new SinPanException();
        }

        hamburguesa.setCantCarnes(totalCarnes);
        hamburguesa.setPrecio(precioTotal);
        hamburguesa.setCliente(cliente);
        Hamburguesa guardado = hamburguesaRepository.save(hamburguesa);
        return hamburguesaMapper.toResponseDTO(guardado);
    }

    @Override
    public float fijarPrecio(Long idHamburguesa) {
        Hamburguesa hamburguesa = hamburguesaRepository.findById(idHamburguesa).orElseThrow(HamburguesaNoEncontradaException::new);

        float precioTotal = hamburguesaProductoService.calcularPrecio(idHamburguesa);
        hamburguesa.setPrecio(precioTotal);
        hamburguesaRepository.save(hamburguesa);

        return precioTotal;
    }

    @Override
    public List<HamburguesaResponseDTO> listarHamburguesas() {
        List<Hamburguesa> hamburguesas = hamburguesaRepository.findAll();
        List<HamburguesaResponseDTO> retornar = new ArrayList<>();

        for(Hamburguesa hamburguesa: hamburguesas){
            retornar.add(hamburguesaMapper.toResponseDTO(hamburguesa));
        }
        return retornar;
    }

    @Override
    public List<ProductoResponseDTO> obtenerIngredientesHamburguesa(Long idCreacion) {
        Hamburguesa hamburguesa = hamburguesaRepository.findById(idCreacion).orElseThrow(HamburguesaNoEncontradaException::new);
        List<HamburguesaProducto>ingredienteshp = hamburguesa.getIngredientes();
        List<Producto>ingredientes = new ArrayList<>();
        List<ProductoResponseDTO> listaRetornar = new ArrayList<>();
        for (HamburguesaProducto hp: ingredienteshp){
            ingredientes.add(hp.getProducto());
        }
        for (Producto producto: ingredientes){
            ProductoResponseDTO retornar = productoMapper.toResponseDTO(producto);
            listaRetornar.add(retornar);
        }
        return listaRetornar;
    }


}
