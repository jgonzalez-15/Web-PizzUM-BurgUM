package uy.um.edu.pizzumandburgum.Servicios.Implementación;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uy.um.edu.pizzumandburgum.DTOs.Request.AdministradorLoginRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Request.AdministradorRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Request.DomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.AdministradorResponseDTO;
import uy.um.edu.pizzumandburgum.DTOs.Update.AdministradorUpdateDTO;
import uy.um.edu.pizzumandburgum.Entidades.Administrador;
import uy.um.edu.pizzumandburgum.Entidades.Domicilio;
import uy.um.edu.pizzumandburgum.Excepciones.Usuario.Administrador.AdministradorNoExiste;
import uy.um.edu.pizzumandburgum.Excepciones.Usuario.Administrador.AdministradorYaExisteException;
import uy.um.edu.pizzumandburgum.Excepciones.Usuario.ContraseniaInvalidaException;
import uy.um.edu.pizzumandburgum.Mappers.AdministradorMapper;
import uy.um.edu.pizzumandburgum.Mappers.DomicilioMapper;
import uy.um.edu.pizzumandburgum.Repositorios.AdministradorRepository;
import uy.um.edu.pizzumandburgum.Repositorios.DomicilioRepository;
import uy.um.edu.pizzumandburgum.Servicios.Interfaces.Historicos.HistoricoAdministradorService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AdministradorServiceImplTest {

    @InjectMocks
    private AdministradorServiceImpl servicio;

    @Mock private AdministradorRepository adminRepo;
    @Mock private DomicilioRepository domicilioRepo;
    @Mock private AdministradorMapper administradorMapper;
    @Mock private DomicilioMapper domicilioMapper;
    @Mock private HistoricoAdministradorService historico;

    @Test
    void agregarAdmin_OK() {
        AdministradorRequestDTO dto = new AdministradorRequestDTO(
                "admin@mail.com", "Juan", "Perez", "1234",
                123L, LocalDate.now(), 555L,
                new DomicilioRequestDTO("Calle 123")
        );

        Administrador admin = new Administrador();
        admin.setEmail("admin@mail.com");

        Domicilio dom = new Domicilio();
        dom.setId(1L);

        when(adminRepo.findByEmail("admin@mail.com")).thenReturn(Optional.empty());
        when(administradorMapper.toEntity(dto)).thenReturn(admin);
        when(domicilioMapper.toEntity(dto.getDomicilio())).thenReturn(dom);
        when(domicilioRepo.saveAndFlush(dom)).thenReturn(dom);
        when(adminRepo.save(admin)).thenReturn(admin);

        when(adminRepo.findById("admin@mail.com")).thenReturn(Optional.of(admin));
        when(domicilioRepo.findById(1L)).thenReturn(Optional.of(dom));
        when(administradorMapper.toResponseDTO(admin)).thenReturn(
                new AdministradorResponseDTO(
                        "admin@mail.com", "Juan", "Perez",
                        123L, LocalDate.now(), 555L, "Calle 123"));


        AdministradorResponseDTO respuesta = servicio.agregarAdmin(dto);

        assertNotNull(respuesta);
        assertEquals("admin@mail.com", respuesta.getEmail());
    }

    @Test
    void agregarAdmin_emailYaExiste() {
        AdministradorRequestDTO dto = new AdministradorRequestDTO();
        dto.setEmail("repetido@mail.com");

        when(adminRepo.findByEmail("repetido@mail.com"))
                .thenReturn(Optional.of(new Administrador()));

        assertThrows(AdministradorYaExisteException.class,
                () -> servicio.agregarAdmin(dto));

        verify(adminRepo).findByEmail("repetido@mail.com");
    }

    @Test
    void login_ok() {
        AdministradorLoginRequestDTO dto = new AdministradorLoginRequestDTO("admin@mail.com", "1234");

        Administrador admin = new Administrador();
        admin.setEmail("admin@mail.com");
        admin.setContrasenia("1234");
        admin.setNombre("Juan");

        when(adminRepo.findById("admin@mail.com")).thenReturn(Optional.of(admin));

        AdministradorResponseDTO administradorResponseDTO = servicio.login(dto);

        assertNotNull(administradorResponseDTO);
        assertEquals("admin@mail.com", administradorResponseDTO.getEmail());
    }

    @Test
    void login_contraseniaIncorrecta() {
        AdministradorLoginRequestDTO dto = new AdministradorLoginRequestDTO("admin@mail.com", "9999");

        Administrador admin = new Administrador();
        admin.setEmail("admin@mail.com");
        admin.setContrasenia("1234");

        when(adminRepo.findById("admin@mail.com")).thenReturn(Optional.of(admin));

        assertThrows(ContraseniaInvalidaException.class, () -> servicio.login(dto));
    }

    @Test
    void login_adminNoExiste() {
        AdministradorLoginRequestDTO dto = new AdministradorLoginRequestDTO("x@mail.com", "1234");

        when(adminRepo.findById("x@mail.com")).thenReturn(Optional.empty());

        assertThrows(AdministradorNoExiste.class, () -> servicio.login(dto));
    }


    @Test
    void editarPerfil_adminNoExiste() {
        when(adminRepo.findById("x@mail.com")).thenReturn(Optional.empty());

        AdministradorUpdateDTO dto = new AdministradorUpdateDTO();
        dto.setNombre("Nuevo");

        assertThrows(AdministradorNoExiste.class, () -> servicio.editarPerfil("x@mail.com", dto));
    }


    @Test
    void listarAdministradores_soloActivos() {
        Administrador a1 = new Administrador();
        a1.setEmail("admin1@mail.com");
        a1.setEstaActivo(true);

        Administrador a2 = new Administrador();
        a2.setEmail("admin2@mail.com");
        a2.setEstaActivo(false);

        when(adminRepo.findAll()).thenReturn(List.of(a1, a2));
        when(administradorMapper.toResponseDTO(a1))
                .thenReturn(new AdministradorResponseDTO("admin1@mail.com", "", "", null, null, null, ""));

        List<AdministradorResponseDTO> lista = servicio.listarAdministradores();

        assertEquals(1, lista.size());
        assertEquals("admin1@mail.com", lista.get(0).getEmail());
    }

    @Test
    void obtenerAdministrador_noExiste() {
        when(adminRepo.findById("x@mail.com")).thenReturn(Optional.empty());

        assertThrows(AdministradorNoExiste.class, () -> servicio.obtenerAdministrador("x@mail.com"));
    }


    @Test
    void eliminarAdministrador_OK() {
        Administrador admin = new Administrador();
        admin.setEmail("admin@mail.com");

        when(adminRepo.findById("admin@mail.com")).thenReturn(Optional.of(admin));

        servicio.eliminarAdministrador("admin@mail.com");

        assertFalse(admin.isEstaActivo());
        verify(adminRepo).delete(admin);
    }

}