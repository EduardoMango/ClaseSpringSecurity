package org.eduardomango.clasespringsecurity.services;

import org.eduardomango.clasespringsecurity.model.dto.UserBasicInfoDTO;
import org.eduardomango.clasespringsecurity.model.dto.UserDTO;
import org.eduardomango.clasespringsecurity.model.entities.UserEntity;
import org.eduardomango.clasespringsecurity.model.mappers.impl.UserBasicInfoMapper;
import org.eduardomango.clasespringsecurity.model.mappers.impl.UserMapper;
import org.eduardomango.clasespringsecurity.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserBasicInfoMapper userBasicInfoMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper, UserBasicInfoMapper userBasicInfoMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userBasicInfoMapper = userBasicInfoMapper;
    }


    public UserDTO save(UserDTO dto) {
        UserEntity saved = userRepository.save(userMapper.mapFrom(dto));

        return userMapper.mapTo(saved);
    }

    public UserDTO findById(String dni) {
        return userRepository.findProjectedByDni(dni)
                .map(u -> UserDTO
                        .builder()
                        .dni(u.getDni())
                        .email(u.getEmail())
                        .nombre(u.getNombre())
                        .apellido(u.getApellido())
                        .edad(u.getEdad())
                        .estadoCivil(u.getEstadoCivil())
                        .build())
                .orElseThrow(NoSuchElementException::new);
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    public List<UserBasicInfoDTO> findAll() {
        return userRepository.findAllProjectedBy().stream()
                .map(projection -> UserBasicInfoDTO.builder()
                        .nombre(projection.getNombre())
                        .apellido(projection.getApellido())
                        .email(projection.getEmail())
                        .build())
                .collect(Collectors.toList());
    }

    public UserDTO update(UserDTO dto) {
        if (userRepository.existsById(dto.getDni())) {
            return save(dto);
        }
        throw new NoSuchElementException();
    }
}
