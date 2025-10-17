package uy.um.edu.pizzumandburgum.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.HamburguesaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;
import uy.um.edu.pizzumandburgum.entities.HamburguesaProducto;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.exceptions.CantidadDeCarnesException;
import uy.um.edu.pizzumandburgum.exceptions.HamburguesaNoEncontradaException;
import uy.um.edu.pizzumandburgum.exceptions.SinCarneException;
import uy.um.edu.pizzumandburgum.exceptions.SinPanException;
import uy.um.edu.pizzumandburgum.mapper.HamburguesaMapper;
import uy.um.edu.pizzumandburgum.repository.HamburguesaRepository;
import uy.um.edu.pizzumandburgum.service.HamburguesaProductoService;
import uy.um.edu.pizzumandburgum.service.HamburguesaService;

@Service
public class HamburguesaServiceImpl implements HamburguesaService {
    @Autowired
    private HamburguesaRepository hamburguesaRepository;

    @Autowired
    private HamburguesaMapper hamburguesaMapper;

    @Autowired
    private HamburguesaProductoService hamburguesaProductoService;

    @Override
    public HamburguesaResponseDTO crearHamburguesa(HamburguesaResponseDTO hamburguesa) {
        if (hamburguesa.getCantCarnes()>3){
            throw new CantidadDeCarnesException();
        } else if (hamburguesa.getCantCarnes()==0) {
           throw new SinCarneException();
        }
        boolean tienePan = false;

        for (HamburguesaProducto hp: hamburguesa.getIngredientes()){
            Producto p = hp.getProducto();
            if ("pan".equalsIgnoreCase(p.getTipo())){
                tienePan = true;
            }
        }
        if (!tienePan){
            throw new SinPanException();
        }
        Hamburguesa nuevo = hamburguesaMapper.toEntity(hamburguesa);

        Hamburguesa guardado = hamburguesaRepository.save(nuevo);

        // Delegamos la creación de ingredientes al service especializado
        for (HamburguesaProducto hpDTO : hamburguesa.getIngredientes()) {
            hamburguesaProductoService.agregarIngrediente(
                    guardado,
                    hpDTO.getProducto(),
                    hpDTO.getCantidad()
            );
        }

        return hamburguesaMapper.toResponseDTO(guardado);



    }

    @Override
    public void fijarPrecio(Long idHamburguesa) {
        Hamburguesa hamburguesa = hamburguesaRepository.findById(idHamburguesa)
                .orElseThrow(() -> new HamburguesaNoEncontradaException());

        HamburguesaResponseDTO hamburguesaDTO = hamburguesaMapper.toResponseDTO(hamburguesa);
        float precioTotal = hamburguesaProductoService.calcularPrecio(hamburguesaDTO);
        hamburguesa.setPrecio(precioTotal);
        hamburguesaRepository.save(hamburguesa);
    }


}
