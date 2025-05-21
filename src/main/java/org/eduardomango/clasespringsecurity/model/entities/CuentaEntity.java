package org.eduardomango.clasespringsecurity.model.entities;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.*;
import org.eduardomango.clasespringsecurity.model.enums.TipoCuenta;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CuentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private Double saldo;
    @Enumerated(EnumType.STRING)
    private TipoCuenta tipo;

    @ManyToOne
    private UserEntity usuario;

    @PrePersist
    public void init(){
        saldo = 0.0;
    }
}
