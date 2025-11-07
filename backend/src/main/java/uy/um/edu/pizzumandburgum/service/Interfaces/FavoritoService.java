package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.FavoritoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.FavoritoResponseDTO;

import java.util.List;

public interface FavoritoService {
    List<FavoritoResponseDTO> mostrarCreacionesFavoritas(String email);
    FavoritoResponseDTO agregarFavorito(FavoritoRequestDTO dto);
    void eliminarFavorito(Long id);
    FavoritoResponseDTO obtenerFavoritoPorId(Long id);

}
