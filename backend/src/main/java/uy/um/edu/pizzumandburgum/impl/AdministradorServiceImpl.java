package uy.um.edu.pizzumandburgum.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.AdministradorDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Administrador;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.exceptions.AdministradorYaExisteException;
import uy.um.edu.pizzumandburgum.exceptions.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.ContraseniaInvalidaException;
import uy.um.edu.pizzumandburgum.mapper.AdministradorMapper;
import uy.um.edu.pizzumandburgum.repository.AdministradorRepository;
import uy.um.edu.pizzumandburgum.service.AdministradorService;

import java.util.Objects;

@Service
public class AdministradorServiceImpl implements AdministradorService {
    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private AdministradorMapper administradorMapper;

    @Override
    public AdministradorDTO agregarAdmin(AdministradorDTO dto) {
        Administrador admin = administradorMapper.toEntity(dto);
        if (administradorRepository.findByEmail(admin.getEmail()).isPresent()) {
            throw new AdministradorYaExisteException();
        }
        administradorRepository.save(admin);
        return administradorMapper.toResponseDTO(admin);
    }

    @Override
    public AdministradorDTO login(String email, String contrasenia) {
        Administrador administrador = administradorRepository.findById(email).orElseThrow(()->new ClienteNoExisteException());

        if (!Objects.equals(administrador.getContrasenia(), contrasenia)){
            throw new ContraseniaInvalidaException();
        }
        return new AdministradorDTO(
                administrador.getEmail(),
                administrador.getNombre(),
                administrador.getApellido(),
                administrador.getContrasenia(),
                administrador.getTelefono(),
                administrador.getFechaNac()
        );

    }

}
