package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.HamburguesaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.HamburguesaRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;
import uy.um.edu.pizzumandburgum.entities.HamburguesaProducto;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.Hamburguesa.CantidadDeCarnesException;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.Hamburguesa.HamburguesaNoEncontradaException;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.Hamburguesa.SinCarneException;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.Hamburguesa.SinPanException;
import uy.um.edu.pizzumandburgum.exceptions.Producto.ProductoNoExisteException;
import uy.um.edu.pizzumandburgum.mapper.HamburguesaMapper;
import uy.um.edu.pizzumandburgum.mapper.HamburguesaProductoMapper;
import uy.um.edu.pizzumandburgum.repository.HamburguesaProductoRepository;
import uy.um.edu.pizzumandburgum.repository.HamburguesaRepository;
import uy.um.edu.pizzumandburgum.repository.ProductoRepository;
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
    private ProductoRepository productoRepository;

    @Autowired
    private HamburguesaProductoMapper hamburguesaProductoMapper;

    @Autowired
    private HamburguesaProductoRepository hamburguesaProductoRepository;


    @Override
    public HamburguesaResponseDTO crearHamburguesa(HamburguesaRequestDTO dto) {
        // 1. Validaciones de negocio
        if (dto.getCantCarnes() > 3) {
            throw new CantidadDeCarnesException();
        }
        if (dto.getCantCarnes() == 0) {
            throw new SinCarneException();
        }

        // 2. Crear hamburguesa base
        Hamburguesa hamburguesa = new Hamburguesa();
        hamburguesa.setCantCarnes(dto.getCantCarnes());
        hamburguesa.setEsFavorita(dto.isEsFavorita());

        // 3. Validar ingredientes, pan y calcular precio en UN solo loop
        boolean tienePan = false;
        float precioTotal = 0;

        for (HamburguesaProductoRequestDTO ingredienteDto : dto.getIngredientes()) {
            // Buscar producto UNA sola vez
            Producto producto = productoRepository.findById(ingredienteDto.getIdProducto())
                    .orElseThrow(() -> new ProductoNoExisteException());

            // Validar si tiene pan
            if ("Pan".equalsIgnoreCase(producto.getTipo())) {
                tienePan = true;
            }

            // Calcular precio en memoria
            precioTotal += producto.getPrecio() * ingredienteDto.getCantidad();
        }

        // Validar que tenga pan
        if (!tienePan) {
            throw new SinPanException();
        }

        // 4. Setear precio y GUARDAR hamburguesa (para obtener el ID)
        hamburguesa.setPrecio(precioTotal);
        Hamburguesa guardado = hamburguesaRepository.save(hamburguesa);

        // 5. AHORA agregar ingredientes (ya tenemos el ID de la hamburguesa)
        for (HamburguesaProductoRequestDTO ingredienteDto : dto.getIngredientes()) {
            hamburguesaProductoService.agregarIngrediente(
                    guardado.getId(),
                    ingredienteDto.getIdProducto(),
                    ingredienteDto.getCantidad()
            );
        }

        return hamburguesaMapper.toResponseDTO(guardado);
    }

    @Override
    public float fijarPrecio(Long idHamburguesa) {
        Hamburguesa hamburguesa = hamburguesaRepository.findById(idHamburguesa)
                .orElseThrow(() -> new HamburguesaNoEncontradaException());

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


}
