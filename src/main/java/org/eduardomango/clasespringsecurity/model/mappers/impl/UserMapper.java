package org.eduardomango.clasespringsecurity.model.mappers.impl;

import org.eduardomango.clasespringsecurity.model.dto.UserDTO;
import org.eduardomango.clasespringsecurity.model.entities.UserEntity;
import org.eduardomango.clasespringsecurity.model.mappers.interfaces.IMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements IMapper<UserEntity, UserDTO> {

    @Override
    public UserDTO mapTo(UserEntity userEntity) {
        return UserDTO.builder()
                .cuit(userEntity.getCuit())
                .email(userEntity.getEmail())
                .edad(userEntity.getEdad())
                .dni(userEntity.getDni())
                .nombre(userEntity.getNombre())
                .apellido(userEntity.getApellido())
                .build();
    }

    @Override
    public UserEntity mapFrom(UserDTO dto) {
        return UserEntity.builder()
                .cuit(dto.getCuit())
                .email(dto.getEmail())
                .edad(dto.getEdad())
                .dni(dto.getDni())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .build();
    }
}
