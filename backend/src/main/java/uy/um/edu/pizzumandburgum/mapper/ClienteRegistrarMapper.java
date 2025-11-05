package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.ClienteRegistrarRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.DomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.MedioDePagoRequestDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.ClienteDomicilio;
import uy.um.edu.pizzumandburgum.entities.Domicilio;
import uy.um.edu.pizzumandburgum.entities.MedioDePago;

@Component
public class ClienteRegistrarMapper {

    @Autowired
    private DomicilioMapper domicilioMapper;

    @Autowired
    private MedioDePagoMapper medioDePagoMapper;

    public Cliente toEntity(ClienteRegistrarRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setEmail(dto.getEmail());
        cliente.setContrasenia(dto.getContrasenia());
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setTelefono(dto.getTelefono());
        cliente.setFechaNac(dto.getFechaNac());


        for (DomicilioRequestDTO domicilio : dto.getDomicilios()) {
            Domicilio d = domicilioMapper.toEntity(domicilio);
            ClienteDomicilio clienteDomicilio = new ClienteDomicilio();
            clienteDomicilio.setDomicilio(d);
            cliente.getDomicilios().add(clienteDomicilio);
        }

        for (MedioDePagoRequestDTO medioDePago : dto.getMediosDePago()) {
            MedioDePago m = medioDePagoMapper.toEntity(medioDePago);
            cliente.getMediosDePago().add(m);
        }

        return cliente;

    }
}
