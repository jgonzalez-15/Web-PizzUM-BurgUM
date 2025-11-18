package uy.um.edu.pizzumandburgum.dto.response.Historicos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoAdministradorDTO {
    private Long id;

    private String emailAdministrador;

    private LocalDate fechaModificacion;

    private String tipoModificiacion;

    private String nombreViejo;
    private String apellidoViejo;
    private String contraseniaVieja;
    private Long telefonoViejo;
    private LocalDate fechaNacVieja;
    private String domicilioViejo;

    private String nombreNuevo;
    private String apellidoNuevo;
    private String contraseniaNueva;
    private Long telefonoNuevo;
    private LocalDate fechaNacNueva;
    private String domicilioNuevo;

}
