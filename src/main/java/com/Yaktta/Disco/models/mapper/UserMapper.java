package com.Yaktta.Disco.models.mapper;

import com.Yaktta.Disco.models.entities.Role;
import com.Yaktta.Disco.models.entities.User;
import com.Yaktta.Disco.models.response.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserResponse mapUserEntityToDTO(User user) {
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        // Mapea los roles a una lista de strings
        userResponse.setRoles(user.getRoles().stream()
                .map(Role::getRol)
                .collect(Collectors.toList()));
        return userResponse;
    }
}
