package org.eduardomango.clasespringsecurity.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.eduardomango.clasespringsecurity.model.enums.EstadoCivil;
import org.eduardomango.clasespringsecurity.security.model.entities.CredentialsEntity;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserEntity {

    @Id
    private String dni;

    private String nombre;
    private String apellido;
    private String email;
    private String cuit;
    private Integer edad;

    @Enumerated(EnumType.STRING)
    private EstadoCivil estadoCivil;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
    private CredentialsEntity credentials;

    @OneToMany(mappedBy = "usuario")
    @ToString.Exclude
    private Set<CuentaEntity> cuentas;
}
