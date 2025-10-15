package uy.um.edu.pizzumandburgum.impl;


import jakarta.validation.constraints.Null;
import org.hibernate.jdbc.Expectation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.ClienteRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.exceptions.EmailYaRegistradoException;
import uy.um.edu.pizzumandburgum.exceptions.UsuarioNoEncontradoException;
import uy.um.edu.pizzumandburgum.mapper.ClienteMapper;
import uy.um.edu.pizzumandburgum.repository.ClienteRepository;
import uy.um.edu.pizzumandburgum.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Override
    public ClienteResponseDTO registrarCliente(ClienteRequestDTO dto) {
        if (clienteRepository.existsByEmail(dto.getEmail())) {
            throw new EmailYaRegistradoException();
        }
        // Convertir DTO → Entity
        Cliente nuevo = clienteMapper.toEntity(dto);

        // Guardar en la base
        Cliente guardado = clienteRepository.save(nuevo);

        // Convertir Entity → DTO de respuesta
        return clienteMapper.toResponseDTO(guardado);
    }

    @Override
    public ClienteResponseDTO login(String email, String password) {
        if (clienteRepository.existsByEmail(email)) {
            Cliente cliente = clienteRepository.findByEmail(email);
            if ((cliente.getPassword() != password)) {
                throw new RuntimeException("Contraseña incorrecta");
            }
            return new ClienteResponseDTO(
                    cliente.getEmail(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getTelefono()
            );
        } else {
            throw new UsuarioNoEncontradoException();
        }
    }
}
