package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.DomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.DomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.dto.update.DomicilioUpdateDTO;
import uy.um.edu.pizzumandburgum.entities.Domicilio;
import uy.um.edu.pizzumandburgum.exceptions.Domicilio.DomicilioNoExisteException;
import uy.um.edu.pizzumandburgum.mapper.DomicilioMapper;
import uy.um.edu.pizzumandburgum.repository.DomicilioRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.DomicilioService;

@Service
public class DomicilioServiceImpl implements DomicilioService {
    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private DomicilioMapper domicilioMapper;

    @Override
    public DomicilioResponseDTO crearDomicilio(DomicilioRequestDTO dto) {
        Domicilio domicilio = new Domicilio();
        domicilio.setDireccion(dto.getDireccion());
        Domicilio guardado = domicilioRepository.save(domicilio);

        return domicilioMapper.toResponseDTO(guardado);
    }

    @Override
    public DomicilioResponseDTO editarPerfil(Long idDomicilio, DomicilioUpdateDTO dto) {
        Domicilio domicilio = domicilioRepository.findById(idDomicilio).orElseThrow(DomicilioNoExisteException::new);
        if (dto.getDireccion() != null){
            domicilio.setDireccion(dto.getDireccion());
        }
        return domicilioMapper.toResponseDTO(domicilio);
    }
}
