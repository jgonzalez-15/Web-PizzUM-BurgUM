package uy.um.edu.pizzumandburgum.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.exceptions.CantidadDeCarnesException;
import uy.um.edu.pizzumandburgum.exceptions.SinCarneException;
import uy.um.edu.pizzumandburgum.exceptions.SinPanException;
import uy.um.edu.pizzumandburgum.mapper.HamburguesaMapper;
import uy.um.edu.pizzumandburgum.repository.HamburguesaRepository;
import uy.um.edu.pizzumandburgum.service.HamburguesaService;

@Service
public class HamburguesaServiceImpl implements HamburguesaService {
    @Autowired
    private HamburguesaRepository hamburguesaRepository;

    @Autowired
    private HamburguesaMapper hamburguesaMapper;

    @Override
    public HamburguesaResponseDTO crearHamburguesa(HamburguesaResponseDTO hamburguesa) {
        if (hamburguesa.getCantCarnes()>3){
            throw new CantidadDeCarnesException();
        } else if (hamburguesa.getCantCarnes()==0) {
           throw new SinCarneException();
        }
        boolean tienePan = false;

        for (Producto p: hamburguesa.getProductos()){
            if (p.getTipo()=="pan"){
                tienePan = true;
            }
        }
        if (!tienePan){
            throw new SinPanException();
        }
        Hamburguesa nuevo = hamburguesaMapper.toEntity(hamburguesa);

        Hamburguesa guardado = hamburguesaRepository.save(nuevo);

        return hamburguesaMapper.toResponseDTO(guardado);



    }
}
