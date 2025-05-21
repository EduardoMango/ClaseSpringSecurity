package org.eduardomango.clasespringsecurity.model.mappers.impl;

import org.eduardomango.clasespringsecurity.model.dto.UserBasicInfoDTO;
import org.eduardomango.clasespringsecurity.model.dto.UserDTO;
import org.eduardomango.clasespringsecurity.model.entities.UserEntity;
import org.eduardomango.clasespringsecurity.model.mappers.interfaces.IMapper;
import org.springframework.stereotype.Component;

@Component
public class UserBasicInfoMapper implements IMapper<UserEntity, UserBasicInfoDTO> {
    @Override
    public UserBasicInfoDTO mapTo(UserEntity userEntity) {
        return UserBasicInfoDTO.builder()
                .email(userEntity.getEmail())
                .nombre(userEntity.getNombre())
                .apellido(userEntity.getApellido())
                .build();
    }

    @Override
    public UserEntity mapFrom(UserBasicInfoDTO dto) {
        return UserEntity.builder()
                .email(dto.getEmail())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .build();
    }
}
