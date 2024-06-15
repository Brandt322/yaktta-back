package com.Yaktta.Disco.config.service;

import com.Yaktta.Disco.config.models.UserDetailsImpl;
import com.Yaktta.Disco.models.entities.User;
import com.Yaktta.Disco.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserEntityByEmail(email);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("El usuario no se encuentra registrado");
        }
        return new UserDetailsImpl(user.get());
    }
}
