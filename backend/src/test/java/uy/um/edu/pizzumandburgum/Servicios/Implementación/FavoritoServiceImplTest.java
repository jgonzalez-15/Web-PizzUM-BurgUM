package uy.um.edu.pizzumandburgum.Servicios.Implementación;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uy.um.edu.pizzumandburgum.DTOs.Request.FavoritoRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.FavoritoResponseDTO;
import uy.um.edu.pizzumandburgum.Entidades.Cliente;
import uy.um.edu.pizzumandburgum.Entidades.Favorito;
import uy.um.edu.pizzumandburgum.Entidades.Pizza;
import uy.um.edu.pizzumandburgum.Excepciones.Creacion.CreacionNoEncontradaException;
import uy.um.edu.pizzumandburgum.Excepciones.Favorito.FavoritoYaExisteException;
import uy.um.edu.pizzumandburgum.Excepciones.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.Mappers.FavoritoMapper;
import uy.um.edu.pizzumandburgum.Repositorios.ClienteRepository;
import uy.um.edu.pizzumandburgum.Repositorios.CreacionRepository;
import uy.um.edu.pizzumandburgum.Repositorios.FavoritoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FavoritoServiceImplTest {

    @InjectMocks
    private FavoritoServiceImpl service;

    @Mock
    private FavoritoRepository favoritoRepo;
    @Mock
    private FavoritoMapper favoritoMapper;
    @Mock
    private ClienteRepository clienteRepo;
    @Mock
    private CreacionRepository creacionRepo;

    @Test
    void agregarFavorito_ok() {

        FavoritoRequestDTO dto = new FavoritoRequestDTO();
        dto.setClienteId("cliente@mail.com");
        dto.setIdCreacion(10L);
        dto.setNombre("Mi pizza favorita");

        Cliente cliente = new Cliente();
        cliente.setEmail("cliente@mail.com");

        Pizza pizza = new Pizza();
        pizza.setId(10L);
        pizza.setPrecio(250);

        Favorito favorito = new Favorito();
        favorito.setId(1L);
        favorito.setCliente(cliente);
        favorito.setCreacion(pizza);

        when(clienteRepo.findById("cliente@mail.com"))
                .thenReturn(Optional.of(cliente));

        when(creacionRepo.findById(10L))
                .thenReturn(Optional.of(pizza));

        when(favoritoRepo.existsByCliente_EmailAndCreacion_Id(
                "cliente@mail.com", 10L))
                .thenReturn(false);

        when(favoritoRepo.save(any()))
                .thenReturn(favorito);

        FavoritoResponseDTO resp = new FavoritoResponseDTO();
        when(favoritoMapper.toResponseDTO(favorito)).thenReturn(resp);

        FavoritoResponseDTO resultado = service.agregarFavorito(dto);

        assertNotNull(resultado);
    }


    @Test
    void agregarFavorito_yaExiste() {

        FavoritoRequestDTO dto = new FavoritoRequestDTO();
        dto.setClienteId("cliente@mail.com");
        dto.setIdCreacion(10L);

        Cliente cliente = new Cliente();
        Pizza pizza = new Pizza();

        when(clienteRepo.findById("cliente@mail.com"))
                .thenReturn(Optional.of(cliente));

        when(creacionRepo.findById(10L))
                .thenReturn(Optional.of(pizza));

        when(favoritoRepo.existsByCliente_EmailAndCreacion_Id(
                "cliente@mail.com", 10L))
                .thenReturn(true);

        assertThrows(FavoritoYaExisteException.class,
                () -> service.agregarFavorito(dto));
    }

    @Test
    void agregarFavorito_clienteNoExiste() {

        FavoritoRequestDTO dto = new FavoritoRequestDTO();
        dto.setClienteId("noExiste@mail.com");

        when(clienteRepo.findById("noExiste@mail.com"))
                .thenReturn(Optional.empty());

        assertThrows(ClienteNoExisteException.class,
                () -> service.agregarFavorito(dto));
    }


    @Test
    void agregarFavorito_creacionNoExiste() {

        FavoritoRequestDTO dto = new FavoritoRequestDTO();
        dto.setClienteId("cliente@mail.com");
        dto.setIdCreacion(50L);

        Cliente cliente = new Cliente();

        when(clienteRepo.findById("cliente@mail.com"))
                .thenReturn(Optional.of(cliente));

        when(creacionRepo.findById(50L))
                .thenReturn(Optional.empty());

        assertThrows(CreacionNoEncontradaException.class,
                () -> service.agregarFavorito(dto));
    }

    @Test
    void mostrarFavoritos_ok() {

        Cliente cliente = new Cliente();
        cliente.setEmail("cliente@mail.com");

        Favorito fav1 = new Favorito();
        Favorito fav2 = new Favorito();

        cliente.setFavoritos(List.of(fav1, fav2));

        when(clienteRepo.findByEmail("cliente@mail.com"))
                .thenReturn(Optional.of(cliente));

        when(favoritoMapper.toResponseDTO(fav1)).thenReturn(new FavoritoResponseDTO());
        when(favoritoMapper.toResponseDTO(fav2)).thenReturn(new FavoritoResponseDTO());

        List<FavoritoResponseDTO> lista = service.mostrarCreacionesFavoritas("cliente@mail.com");

        assertEquals(2, lista.size());
    }

    @Test
    void mostrarFavoritos_clienteNoExiste() {

        when(clienteRepo.findByEmail("x@mail.com"))
                .thenReturn(Optional.empty());

        assertThrows(ClienteNoExisteException.class,
                () -> service.mostrarCreacionesFavoritas("x@mail.com"));
    }


}
