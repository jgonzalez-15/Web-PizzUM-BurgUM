package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.MedioDePagoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.MedioDePagoDTO;
import uy.um.edu.pizzumandburgum.dto.update.MedioDePagoUpdateDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.MedioDePago;
import uy.um.edu.pizzumandburgum.exceptions.MedioDePago.MedioDePagoNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
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
    public MedioDePago obtenerMedioDePago(String email, Long id) {
        return medioDePagoRepository.findByClienteEmailAndId(email,id ).orElseThrow(MedioDePagoNoExisteException::new);
    }

    @Override
    public MedioDePagoDTO aniadirMedioDePago(MedioDePagoRequestDTO dto, String idCliente){
        MedioDePago medioDePago = new MedioDePago();
        medioDePago.setNumero(dto.getNumero());
        medioDePago.setVencimiento(dto.getVencimiento());
        medioDePago.setDireccion(dto.getDireccion());

        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(ClienteNoExisteException::new);
        cliente.getMediosDePago().add(medioDePago);
        medioDePago.setCliente(cliente);

        medioDePagoRepository.save(medioDePago);

        return medioDePagoMapper.toResponseDTO(medioDePago);
    }

    @Override
    public MedioDePagoDTO editarMDP(String email, MedioDePagoUpdateDTO dto) {
        MedioDePago mdp = new MedioDePago();
        if (dto.getDireccion() != null) {
            mdp.setDireccion(dto.getDireccion());
        }
        if (dto.getVencimiento() != null) {
            mdp.setVencimiento(dto.getVencimiento());
        }
        if (dto.getNumero() != null) {
            mdp.setNumero(dto.getNumero());
        }
        return medioDePagoMapper.toResponseDTO(mdp);
    }
}
