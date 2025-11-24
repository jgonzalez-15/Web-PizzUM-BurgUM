package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.response.CreacionResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Creacion;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.CreacionNoExisteException;
import uy.um.edu.pizzumandburgum.mapper.CreacionMapper;
import uy.um.edu.pizzumandburgum.repository.CreacionRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.CreacionService;

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
