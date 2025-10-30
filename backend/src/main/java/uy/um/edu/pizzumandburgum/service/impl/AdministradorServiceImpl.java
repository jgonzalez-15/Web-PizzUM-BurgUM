package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.AdministradorRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.AdministradorResponseDTO;
import uy.um.edu.pizzumandburgum.dto.update.AdministradorUpdateDTO;
import uy.um.edu.pizzumandburgum.entities.Administrador;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Administrador.AdministradorNoExiste;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Administrador.AdministradorYaExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.ContraseniaInvalidaException;
import uy.um.edu.pizzumandburgum.mapper.AdministradorMapper;
import uy.um.edu.pizzumandburgum.repository.AdministradorRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.AdministradorService;

import java.util.Objects;

@Service
public class AdministradorServiceImpl implements AdministradorService {
    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private AdministradorMapper administradorMapper;

    @Override
    public AdministradorResponseDTO agregarAdmin(AdministradorResponseDTO dto) {
        Administrador admin = administradorMapper.toEntity(dto);
        if (administradorRepository.findByEmail(admin.getEmail()).isPresent()) {
            throw new AdministradorYaExisteException();
        }
        administradorRepository.save(admin);
        return administradorMapper.toResponseDTO(admin);
    }

    @Override
    public AdministradorResponseDTO login(AdministradorRequestDTO dto) {
        Administrador administrador = administradorRepository.findById(dto.getEmail()).orElseThrow(()->new ClienteNoExisteException());

        if (!Objects.equals(administrador.getContrasenia(), dto.getContrasenia())){
            throw new ContraseniaInvalidaException();
        }
        return new AdministradorResponseDTO(
                administrador.getEmail(),
                administrador.getNombre(),
                administrador.getApellido(),
                administrador.getContrasenia(),
                administrador.getTelefono(),
                administrador.getFechaNac()
        );

    }

    @Override
    public AdministradorResponseDTO editarPerfil(String email, AdministradorUpdateDTO dto) {
        Administrador administrador = administradorRepository.findById(email).orElseThrow(() -> new AdministradorNoExiste());
        if (dto.getNombre() != null) {
            administrador.setNombre(dto.getNombre());
        }
        if (dto.getApellido() != null) {
            administrador.setApellido(dto.getApellido());
        }
        if (dto.getContrasenia() != null) {
            administrador.setContrasenia(dto.getContrasenia());
        }
        if (dto.getTelefono() != 0) {
            administrador.setTelefono(dto.getTelefono());
        }
        if (dto.getFechaNac() != null) {
            administrador.setFechaNac(dto.getFechaNac());
        }
        return administradorMapper.toResponseDTO(administrador);
    }

}
