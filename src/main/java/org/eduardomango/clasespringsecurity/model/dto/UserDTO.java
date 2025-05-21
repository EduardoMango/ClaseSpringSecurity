package org.eduardomango.clasespringsecurity.model.dto;

import lombok.Builder;
import lombok.Value;
import org.eduardomango.clasespringsecurity.model.entities.UserEntity;
import org.eduardomango.clasespringsecurity.model.enums.EstadoCivil;

import java.io.Serializable;

/**
 * DTO for {@link UserEntity}
 */
@Value
@Builder
public class UserDTO implements Serializable {
    String dni;
    String nombre;
    String apellido;
    String email;
    String cuit;
    int edad;
    EstadoCivil estadoCivil;
}