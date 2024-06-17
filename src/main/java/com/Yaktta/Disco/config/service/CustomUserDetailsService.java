package com.Yaktta.Disco.config.service;

import com.Yaktta.Disco.config.models.UserDetailsImpl;
import com.Yaktta.Disco.models.entities.User;
import com.Yaktta.Disco.models.response.UserResponse;
import com.Yaktta.Disco.repository.UserRepository;
import com.Yaktta.Disco.service.Implementation.UserServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserServiceImpl userServiceImpl;

    public CustomUserDetailsService(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userServiceImpl.getUserByUsername(email);
        if (user == null) {
            throw new UsernameNotFoundException("El usuario no se encuentra registrado");
        }
        return new UserDetailsImpl(user);
    }
}
