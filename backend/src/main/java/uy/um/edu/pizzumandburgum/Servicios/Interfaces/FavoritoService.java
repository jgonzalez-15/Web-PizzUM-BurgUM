package uy.um.edu.pizzumandburgum.Servicios.Interfaces;

import uy.um.edu.pizzumandburgum.DTOs.Request.FavoritoRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.FavoritoResponseDTO;

import java.util.List;

public interface FavoritoService {
    List<FavoritoResponseDTO> mostrarCreacionesFavoritas(String email);
    FavoritoResponseDTO agregarFavorito(FavoritoRequestDTO dto);
    void eliminarFavorito(Long id);
    FavoritoResponseDTO obtenerFavoritoPorId(Long id);

}
