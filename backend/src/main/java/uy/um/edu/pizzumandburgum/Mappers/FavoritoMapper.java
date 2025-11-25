package uy.um.edu.pizzumandburgum.Mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.DTOs.Request.FavoritoRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.FavoritoResponseDTO;
import uy.um.edu.pizzumandburgum.Entidades.Favorito;
import uy.um.edu.pizzumandburgum.Excepciones.Creacion.CreacionNoEncontradaException;
import uy.um.edu.pizzumandburgum.Excepciones.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.Repositorios.ClienteRepository;
import uy.um.edu.pizzumandburgum.Repositorios.CreacionRepository;

@Component
public class FavoritoMapper {

    @Autowired
    private CreacionRepository creacionRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Favorito toEntity(FavoritoRequestDTO dto) {
        Favorito favorito = new Favorito();
        favorito.setCreacion(creacionRepository.findById(dto.getIdCreacion()).orElseThrow(CreacionNoEncontradaException::new));
        favorito.setCliente(clienteRepository.findByEmail(dto.getClienteId()).orElseThrow(ClienteNoExisteException::new));
        favorito.setNombre(dto.getNombre());
        return favorito;
    }

    public FavoritoResponseDTO toResponseDTO(Favorito favorito) {
        FavoritoResponseDTO dto = new FavoritoResponseDTO();
        dto.setId(favorito.getId());
        dto.setNombre(favorito.getNombre());
        dto.setIdCliente(favorito.getCliente().getEmail());
        dto.setIdCreacion(favorito.getCreacion().getId());
        dto.setPrecio(favorito.getPrecio());
        dto.setTipo(favorito.getCreacion().getClass().getSimpleName());
        return dto;
    }
}