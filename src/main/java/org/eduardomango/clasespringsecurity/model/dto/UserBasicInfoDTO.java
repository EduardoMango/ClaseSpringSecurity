package org.eduardomango.clasespringsecurity.model.dto;

import lombok.Builder;
import lombok.Value;
import org.eduardomango.clasespringsecurity.model.entities.UserEntity;

import java.io.Serializable;

/**
 * DTO for {@link UserEntity}
 */
@Value
@Builder
public class UserBasicInfoDTO implements Serializable {
    String nombre;
    String apellido;
    String email;
}