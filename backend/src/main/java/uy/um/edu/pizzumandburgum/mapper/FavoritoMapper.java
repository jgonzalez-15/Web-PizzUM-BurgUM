package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.FavoritoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.FavoritoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Favorito;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.CreacionNoEncontradaException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.repository.ClienteRepository;
import uy.um.edu.pizzumandburgum.repository.CreacionRepository;

@Component
public class FavoritoMapper {

    @Autowired
    private CreacionRepository creacionRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Favorito toEntity(FavoritoRequestDTO dto) {
        Favorito favorito = new Favorito();
        favorito.setCreacion(creacionRepository.findById(dto.getIdCreacion()).orElseThrow(()->new CreacionNoEncontradaException()));
        favorito.setCliente(clienteRepository.findByEmail(dto.getClienteId()).orElseThrow(()->new ClienteNoExisteException()));
        favorito.setNombre(dto.getNombre());
        return favorito;
    }

    public FavoritoResponseDTO toResponseDTO(Favorito favorito) {
        return new FavoritoResponseDTO(favorito.getId(), favorito.getNombre(),favorito.getCliente().getEmail(), favorito.getCreacion().getId(), favorito.getPrecio());
    }

}
