package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.PagoDummyRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.MedioDePagoDTO;
import uy.um.edu.pizzumandburgum.dto.response.PagoDummyResponseDTO;
import uy.um.edu.pizzumandburgum.entities.MedioDePago;
import uy.um.edu.pizzumandburgum.entities.PagoDummy;

@Component
public class PagoDummyMapper {

    public PagoDummyResponseDTO toResponseDTO(PagoDummy pagoDummy) {
        return new PagoDummyResponseDTO(pagoDummy.getCodigoTransaccion(),pagoDummy.getMonto(),pagoDummy.getFechaPago(),pagoDummy.getEstadoPago(),pagoDummy.getPedido().getIdPedido());
    }
}
