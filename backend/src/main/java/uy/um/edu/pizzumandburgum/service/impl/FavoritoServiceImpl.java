package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.FavoritoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.FavoritoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.Creacion;
import uy.um.edu.pizzumandburgum.entities.Favorito;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.CreacionNoEncontradaException;
import uy.um.edu.pizzumandburgum.exceptions.Favorito.FavoritoYaExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.mapper.FavoritoMapper;
import uy.um.edu.pizzumandburgum.repository.ClienteRepository;
import uy.um.edu.pizzumandburgum.repository.CreacionRepository;
import uy.um.edu.pizzumandburgum.repository.FavoritoRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.FavoritoService;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoritoServiceImpl implements FavoritoService {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private FavoritoMapper favoritoMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CreacionRepository creacionRepository;

    @Override
    public List<FavoritoResponseDTO> mostrarCreacionesFavoritas(String email) {
        Cliente cliente = clienteRepository.findByEmail(email).orElseThrow(ClienteNoExisteException::new);
        List<Favorito> favoritos = cliente.getFavoritos();
        List<FavoritoResponseDTO> retornarlista = new ArrayList<>();

        for (Favorito favorito: favoritos){
            FavoritoResponseDTO retornar = favoritoMapper.toResponseDTO(favorito);
            retornarlista.add(retornar);
        }
        return retornarlista;
    }

    @Override
    public FavoritoResponseDTO agregarFavorito(FavoritoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId()).orElseThrow(ClienteNoExisteException::new);

        Creacion creacion = creacionRepository.findById(dto.getIdCreacion()).orElseThrow(CreacionNoEncontradaException::new);

        boolean yaExiste = favoritoRepository.existsByCliente_EmailAndCreacion_Id(dto.getClienteId(), dto.getIdCreacion());
        if (yaExiste) {
            throw new FavoritoYaExisteException();
        }

        Favorito favorito = new Favorito();
        favorito.setNombre(dto.getNombre());
        favorito.setCliente(cliente);
        favorito.setCreacion(creacion);
        favorito.setPrecio(creacion.getPrecio());

        Favorito guardado = favoritoRepository.save(favorito);

        return favoritoMapper.toResponseDTO(guardado);
    }

    @Override
    public void eliminarFavorito(Long id) {
        favoritoRepository.deleteById(id);
    }
}
