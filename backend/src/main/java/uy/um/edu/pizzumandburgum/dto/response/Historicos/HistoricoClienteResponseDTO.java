package uy.um.edu.pizzumandburgum.dto.response.Historicos;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.entities.Cliente;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoClienteResponseDTO {
    private Long id;

    private String emailCliente;

    private LocalDate fechaModificacion;

    private String tipoModificiacion;

    private String nombreViejo;
    private String apellidoViejo;
    private String contraseniaVieja;
    private Long telefonoViejo;
    private LocalDate fechaNacVieja;

    private String nombreNuevo;
    private String apellidoNuevo;
    private String contraseniaNueva;
    private Long telefonoNuevo;
    private LocalDate fechaNacNueva;
}
