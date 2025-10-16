package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.service.DomicilioService;

@Entity
@Getter
@Setter
public class ClienteDomicilio {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "domicilio")
    private Domicilio domicilio;

    public ClienteDomicilio() {
    }

    public ClienteDomicilio(Long id, Cliente cliente, Domicilio domicilio) {
        this.id = id;
        this.cliente = cliente;
        this.domicilio = domicilio;
    }
}
