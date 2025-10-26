package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.MedioDePagoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.MedioDePagoDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.MedioDePago;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.mapper.ClienteMapper;
import uy.um.edu.pizzumandburgum.mapper.MedioDePagoMapper;
import uy.um.edu.pizzumandburgum.repository.ClienteRepository;
import uy.um.edu.pizzumandburgum.repository.MedioDePagoRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.MedioDePagoService;

@Service
public class MedioDePagoServiceImpl implements MedioDePagoService {
    @Autowired
    private MedioDePagoRepository medioDePagoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MedioDePagoMapper medioDePagoMapper;


    @Override
    public MedioDePago obtenerMedioDePago(String email,Long numero) {
        Cliente cliente = clienteRepository.findById(email).orElseThrow(() -> new ClienteNoExisteException());

        for (MedioDePago medioDePago : cliente.getMediosDePago()){
            if (medioDePago.getNumero().equals(numero)){
                return medioDePago;
            }
        }
        return null;
    }

    @Override
    public MedioDePagoDTO aniadirMedioDePago(MedioDePagoRequestDTO dto){
        MedioDePago medioDePago = new MedioDePago();
        medioDePago.setNumero(dto.getNumero());
        medioDePago.setVencimiento(dto.getVencimiento());
        medioDePago.setDireccion(dto.getDireccion());

        Cliente cliente = clienteRepository.findById(dto.getCliente().getEmail()).orElseThrow(()-> new ClienteNoExisteException());
        cliente.getMediosDePago().add(medioDePago);
        medioDePago.setCliente(cliente);

        medioDePagoRepository.save(medioDePago);

        return medioDePagoMapper.toResponseDTO(medioDePago);
    }
}
