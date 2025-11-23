package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.AdministradorLoginRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.AdministradorRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.AdministradorResponseDTO;
import uy.um.edu.pizzumandburgum.dto.update.AdministradorUpdateDTO;
import uy.um.edu.pizzumandburgum.entities.Administrador;
import uy.um.edu.pizzumandburgum.entities.Domicilio;
import uy.um.edu.pizzumandburgum.exceptions.Domicilio.DomicilioNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Administrador.AdministradorNoExiste;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Administrador.AdministradorYaExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.ContraseniaInvalidaException;
import uy.um.edu.pizzumandburgum.mapper.AdministradorMapper;
import uy.um.edu.pizzumandburgum.mapper.DomicilioMapper;
import uy.um.edu.pizzumandburgum.repository.AdministradorRepository;
import uy.um.edu.pizzumandburgum.repository.DomicilioRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.AdministradorService;
import uy.um.edu.pizzumandburgum.service.Interfaces.Historicos.HistoricoAdministradorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AdministradorServiceImpl implements AdministradorService {
    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private AdministradorMapper administradorMapper;

    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private HistoricoAdministradorService historicoAdministradorService;

    @Autowired
    private  DomicilioMapper domicilioMapper;


    @Override
    public AdministradorResponseDTO agregarAdmin(AdministradorRequestDTO dto) {
        Administrador admin = administradorMapper.toEntity(dto);
        if (administradorRepository.findByEmail(admin.getEmail()).isPresent()) {
            throw new AdministradorYaExisteException();
        }

        Domicilio domicilio = domicilioMapper.toEntity(dto.getDomicilio());
        Domicilio domicilioGuardado = domicilioRepository.saveAndFlush(domicilio);
        administradorRepository.save(admin);
        domicilioGuardado.setAdministrador(admin);
        asociarDomicilio(domicilioGuardado.getId(), admin.getEmail());
        historicoAdministradorService.RegistrarAgregar(admin);
        return administradorMapper.toResponseDTO(admin);
    }
    @Override
    public void asociarDomicilio(Long idDomicilio,String email){
        Administrador administrador = administradorRepository.findById(email).orElseThrow(AdministradorNoExiste::new);
        Domicilio domicilio = domicilioRepository.findById(idDomicilio).orElseThrow(DomicilioNoExisteException::new);
        administrador.setDomicilio(domicilio);
        domicilio.setAdministrador(administrador);
    }

    @Override
    public AdministradorResponseDTO login(AdministradorLoginRequestDTO dto) {
        Administrador administrador = administradorRepository.findById(dto.getEmail()).orElseThrow(AdministradorNoExiste::new);
        if (!Objects.equals(administrador.getContrasenia(), dto.getContrasenia())){
            throw new ContraseniaInvalidaException();
        }
        String domicilio = "";
        if (administrador.getDomicilio() != null) {
            domicilio = administrador.getDomicilio().getDireccion();
        }
        return new AdministradorResponseDTO(administrador.getEmail(), administrador.getNombre(), administrador.getApellido(), administrador.getTelefono(), administrador.getFechaNac(), administrador.getCedula(), domicilio);

    }

    @Override
    public AdministradorResponseDTO editarPerfil(String email, AdministradorUpdateDTO dto) {
        Administrador viejo = administradorRepository.findById(email).orElseThrow(AdministradorNoExiste::new);
        Administrador administrador = administradorRepository.findById(email).orElseThrow(AdministradorNoExiste::new);
        if (dto.getNombre() != null) {
            administrador.setNombre(dto.getNombre());
        }
        if (dto.getApellido() != null) {
            administrador.setApellido(dto.getApellido());
        }
        if (dto.getContrasenia() != null && !dto.getContrasenia().isBlank()) {
            administrador.setContrasenia(dto.getContrasenia());
        }
        if (dto.getTelefono() != 0) {
            administrador.setTelefono(dto.getTelefono());
        }
        if (dto.getFechaNac() != null) {
            administrador.setFechaNac(dto.getFechaNac());
        }
        if (dto.getCedula() != null) {
            administrador.setCedula(dto.getCedula());
        }
        if (dto.getDomicilio() != null) {
            Domicilio domicilio = domicilioMapper.toEntity(dto.getDomicilio());
            Domicilio domicilioGuardado = domicilioRepository.saveAndFlush(domicilio);
            asociarDomicilio(domicilioGuardado.getId(), administrador.getEmail());
        }
        administradorRepository.save(administrador);

        historicoAdministradorService.registrarActualizacion(viejo,administrador);

        return administradorMapper.toResponseDTO(administrador);
    }

    @Override
    public List<AdministradorResponseDTO> listarAdministradores() {
        List <Administrador> administradores = administradorRepository.findAll();
        List <AdministradorResponseDTO>retornar = new ArrayList<>();
        for (Administrador administrador: administradores) {
            if (administrador.isEstaActivo()){
            retornar.add(administradorMapper.toResponseDTO(administrador));
            }
        }
        return retornar;
    }

    @Override
    public AdministradorResponseDTO obtenerAdministrador(String email) {
        Administrador administrador = administradorRepository.findById(email).orElseThrow(AdministradorNoExiste::new);
        String domicilio = "";
        if (administrador.getDomicilio() != null) {
            domicilio = administrador.getDomicilio().getDireccion();
        }
        return new AdministradorResponseDTO(
                administrador.getEmail(),
                administrador.getNombre(),
                administrador.getApellido(),
                administrador.getTelefono(),
                administrador.getFechaNac(),
                administrador.getCedula(),
                domicilio
        );
    }

    @Override
    public void eliminarAdministrador(String email) {
        Administrador admin = administradorRepository.findById(email).orElseThrow(AdministradorNoExiste::new);
        admin.setEstaActivo(false);
        historicoAdministradorService.RegistrarEliminar(admin);
        administradorRepository.delete(admin);
    }

}
