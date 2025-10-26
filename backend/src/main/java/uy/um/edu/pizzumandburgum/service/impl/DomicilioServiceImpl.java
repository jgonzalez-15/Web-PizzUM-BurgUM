package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.DomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.DomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Domicilio;
import uy.um.edu.pizzumandburgum.mapper.DomicilioMapper;
import uy.um.edu.pizzumandburgum.repository.DomicilioRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.DomicilioService;

import java.util.ArrayList;
import java.util.List;

@Service
public class DomicilioServiceImpl implements DomicilioService {
    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private DomicilioMapper domicilioMapper;

    @Override
    public DomicilioResponseDTO crearDomicilio(DomicilioRequestDTO dto) {
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle(dto.getCalle());
        domicilio.setNumero(dto.getNumero());
        domicilio.setCiudad(dto.getCiudad());
        domicilio.setDepartamento(dto.getDepartamento());
        domicilio.setCodigoPostal(dto.getCodigoPostal());
        Domicilio guardado = domicilioRepository.save(domicilio);

        return domicilioMapper.toResponseDTO(guardado);
    }

    @Override
    public List<DomicilioResponseDTO> listarDomicilios() {
        List<Domicilio> domiciliciosComunes = domicilioRepository.findAll();
        List<DomicilioResponseDTO> retornar = new ArrayList<>();
        for (Domicilio domicilio : domiciliciosComunes) {
            retornar.add(domicilioMapper.toResponseDTO(domicilio));
        }
        return retornar;
    }

}
