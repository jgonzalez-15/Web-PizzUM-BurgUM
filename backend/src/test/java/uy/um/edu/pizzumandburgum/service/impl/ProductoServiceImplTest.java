package uy.um.edu.pizzumandburgum.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uy.um.edu.pizzumandburgum.dto.request.ProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ProductoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.exceptions.Producto.CampoObligatorioException;
import uy.um.edu.pizzumandburgum.exceptions.Producto.ProductoYaExisteException;
import uy.um.edu.pizzumandburgum.mapper.ProductoMapper;
import uy.um.edu.pizzumandburgum.repository.ProductoRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.Historicos.HistoricoProductoModificacionService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @InjectMocks
    private ProductoServiceImpl service;

    @Mock
    private ProductoRepository productoRepo;
    @Mock private ProductoMapper mapper;
    @Mock private ProductoCreacionUpdaterService updater;
    @Mock private HistoricoProductoModificacionService historicoService;

    @Test
    void agregarProducto_ok() {

        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("Coca Cola");
        dto.setTipo("Bebida");
        dto.setPrecio(120f);
        dto.setVisible(true);

        Producto prod = new Producto();
        prod.setNombre("Coca Cola");
        prod.setTipo("Bebida");
        prod.setPrecio(120f);

        when(mapper.toEntity(dto)).thenReturn(prod);
        when(productoRepo.findByNombre("Coca Cola"))
                .thenReturn(Optional.empty());

        when(productoRepo.save(prod)).thenReturn(prod);

        ProductoResponseDTO resp = new ProductoResponseDTO();
        when(mapper.toResponseDTO(prod)).thenReturn(resp);

        ProductoResponseDTO resultado = service.agregarProducto(dto);

        assertNotNull(resultado);
    }

    @Test
    void agregarProducto_sinNombre() {
        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre(null);
        dto.setTipo("Bebida");
        dto.setPrecio(100f);
        dto.setVisible(true);

        Producto prod = new Producto();
        prod.setNombre(null);
        prod.setTipo("Bebida");
        prod.setPrecio(100f);

        when(mapper.toEntity(dto)).thenReturn(prod);

        assertThrows(CampoObligatorioException.class,
                () -> service.agregarProducto(dto));
    }

    @Test
    void agregarProducto_sinTipo() {
        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("Pepsi");
        dto.setTipo(null);
        dto.setPrecio(100f);
        dto.setVisible(true);

        Producto prod = new Producto();
        prod.setNombre("Pepsi");
        prod.setTipo(null);
        prod.setPrecio(100f);

        when(mapper.toEntity(dto)).thenReturn(prod);

        assertThrows(CampoObligatorioException.class,
                () -> service.agregarProducto(dto));
    }

    @Test
    void agregarProducto_precioObligatorioVisible() {

        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("Sprite");
        dto.setTipo("Bebida");
        dto.setVisible(true);
        dto.setPrecio(null);

        Producto prod = new Producto();
        prod.setNombre("Sprite");
        prod.setTipo("Bebida");
        prod.setVisible(true);
        prod.setPrecio(null);

        when(mapper.toEntity(dto)).thenReturn(prod);

        assertThrows(CampoObligatorioException.class,
                () -> service.agregarProducto(dto));
    }

    @Test
    void agregarProducto_yaExiste() {

        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("Fanta");
        dto.setTipo("Bebida");
        dto.setPrecio(100f);
        dto.setVisible(true);

        Producto prod = new Producto();
        prod.setNombre("Fanta");
        prod.setTipo("Bebida");
        prod.setPrecio(100f);
        prod.setEstaActivo(true);

        when(mapper.toEntity(dto)).thenReturn(prod);

        when(productoRepo.findByNombre("Fanta"))
                .thenReturn(Optional.of(prod));

        assertThrows(ProductoYaExisteException.class,
                () -> service.agregarProducto(dto));
    }
}
