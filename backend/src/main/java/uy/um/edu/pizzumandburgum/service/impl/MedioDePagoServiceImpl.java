package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.MedioDePagoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.MedioDePagoDTO;
import uy.um.edu.pizzumandburgum.dto.update.MedioDePagoUpdateDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.Historicos.HistoricoMDPModificaciones;
import uy.um.edu.pizzumandburgum.entities.MedioDePago;
import uy.um.edu.pizzumandburgum.exceptions.MedioDePago.MedioDePagoNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.MedioDePago.PorLoMenosUnMedioDePagoException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.mapper.MedioDePagoMapper;
import uy.um.edu.pizzumandburgum.repository.ClienteRepository;
import uy.um.edu.pizzumandburgum.repository.MedioDePagoRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.Historicos.HistoricoMDPModificacionesService;
import uy.um.edu.pizzumandburgum.service.Interfaces.MedioDePagoService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedioDePagoServiceImpl implements MedioDePagoService {
    @Autowired
    private MedioDePagoRepository medioDePagoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MedioDePagoMapper medioDePagoMapper;

    @Autowired
    private HistoricoMDPModificacionesService historicoService;


    @Override
    public MedioDePago obtenerMedioDePago(String email, Long id) {


        return medioDePagoRepository.findByClienteEmailAndId(email,id ).orElseThrow(MedioDePagoNoExisteException::new);
    }

    @Override
    public MedioDePagoDTO aniadirMedioDePago(MedioDePagoRequestDTO dto, String idCliente){
        MedioDePago medioDePago = new MedioDePago();
        medioDePago.setNumeroTarjeta(dto.getNumeroTarjeta());
        medioDePago.setFechaVencimiento(dto.getFechaVencimiento());
        medioDePago.setNombreTitular(dto.getNombreTitular());

        Cliente cliente = clienteRepository.findByEmail(idCliente).orElseThrow(ClienteNoExisteException::new);
        cliente.getMediosDePago().add(medioDePago);
        medioDePago.setCliente(cliente);

        medioDePago.setEstaActivo(true);
        medioDePagoRepository.save(medioDePago);

        return medioDePagoMapper.toResponseDTO(medioDePago);
    }

    @Override
    public MedioDePagoDTO editarMDP(Long id, MedioDePagoUpdateDTO dto) {
        MedioDePago viejo = medioDePagoRepository.findById(id).orElseThrow(MedioDePagoNoExisteException::new);
        MedioDePago mdp = new MedioDePago();

        mdp.setCliente(viejo.getCliente());

        if (dto.getNombreTitular() != null) {
            mdp.setNombreTitular(dto.getNombreTitular());
        }
        if (dto.getFechaVencimiento() != null) {
            mdp.setFechaVencimiento(dto.getFechaVencimiento());
        }
        if (dto.getNumeroTarjeta() != null) {
            mdp.setNumeroTarjeta(dto.getNumeroTarjeta());
        }
        mdp.setHistorico(new ArrayList<>(viejo.getHistorico()));
        medioDePagoRepository.save(mdp);
        historicoService.registrarActualizacion(viejo,mdp);

        return medioDePagoMapper.toResponseDTO(mdp);
    }

    @Override
    public List<MedioDePagoDTO> listarPorCliente(String email) {
        List<MedioDePago> medios = medioDePagoRepository.findByClienteEmailAndEstaActivoTrue(email);
        List<MedioDePagoDTO> listaDTO = new ArrayList<>();

        for (MedioDePago medio : medios) {
            MedioDePagoDTO dto = medioDePagoMapper.toResponseDTO(medio);
            listaDTO.add(dto);
        }

        return listaDTO;
    }


    @Override
    public void eliminarMedioDePago(String email, Long id) {
        MedioDePago medio = medioDePagoRepository.findByClienteEmailAndId(email, id).orElseThrow(MedioDePagoNoExisteException::new);

        long mediosActivos = medioDePagoRepository.findByClienteEmailAndEstaActivoTrue(email).size();
        if (mediosActivos <= 1) {
            throw new PorLoMenosUnMedioDePagoException();
        }

        medio.setEstaActivo(false);
        medioDePagoRepository.save(medio);
        historicoService.RegistrarEliminar(medio);
    }

}
