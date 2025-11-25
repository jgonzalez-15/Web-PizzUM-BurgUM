package uy.um.edu.pizzumandburgum.Servicios.Implementación;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.DTOs.Response.CreacionResponseDTO;
import uy.um.edu.pizzumandburgum.Entidades.Creacion;
import uy.um.edu.pizzumandburgum.Excepciones.Creacion.CreacionNoExisteException;
import uy.um.edu.pizzumandburgum.Mappers.CreacionMapper;
import uy.um.edu.pizzumandburgum.Repositorios.CreacionRepository;
import uy.um.edu.pizzumandburgum.Servicios.Interfaces.CreacionService;

@Service
public class CreacionServiceImpl implements CreacionService {

    @Autowired
    private CreacionRepository creacionRepository;

    @Autowired
    private CreacionMapper creacionMapper;

    @Override
    public CreacionResponseDTO obtenerCreacionPorId(Long id) {
        Creacion creacion = creacionRepository.findById(id).orElseThrow(CreacionNoExisteException::new);

        return creacionMapper.toResponseDTO(creacion);
    }
}
