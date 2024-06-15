package com.Yaktta.Disco.config.controller;

import com.Yaktta.Disco.config.jwt.JWTProvider;
import com.Yaktta.Disco.config.models.JwtDto;
import com.Yaktta.Disco.config.models.UserDetailsImpl;
import com.Yaktta.Disco.models.entities.Role;
import com.Yaktta.Disco.models.entities.User;
import com.Yaktta.Disco.models.mapper.UserMapper;
import com.Yaktta.Disco.models.request.LoginRequest;
import com.Yaktta.Disco.models.request.RegisterRequest;
import com.Yaktta.Disco.models.response.UserResponse;
import com.Yaktta.Disco.repository.RoleRepository;
import com.Yaktta.Disco.repository.UserRepository;
import com.Yaktta.Disco.utils.Response;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public AuthController(UserRepository userRepository, RoleRepository roleRepository,AuthenticationManager authenticationManager, JWTProvider jwtProvider, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserDetailsImpl) {
            User user = ((UserDetailsImpl) auth.getPrincipal()).getUser();
            return userMapper.mapUserEntityToDTO(user);
        }
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Response(400, "campos mal puestos"), HttpStatus.BAD_REQUEST);
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(getUser(), jwt, userDetails.getAuthorities());
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsUserEntityByEmail(registerRequest.getUsername())) {
            return new ResponseEntity<>(new Response(400, "El email ya está en uso"), HttpStatus.BAD_REQUEST);
        }

        // Crear nuevo objeto User
        User newUser = new User();
        newUser.setEmail(registerRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setFirstName(registerRequest.getFirstname());
        newUser.setLastName(registerRequest.getLastname());
        newUser.setCity(registerRequest.getCity());
        newUser.setAccountNonExpired(true);
        newUser.setAccountNonLocked(true);
        newUser.setCredentialsNonExpired(true);
        newUser.setEnabled(true);

        // Asignar rol USER
        Role userRole = roleRepository.findByRol("USER").orElseThrow(() -> new RuntimeException("Error: Rol no encontrado"));
        newUser.setRoles(new ArrayList<>(Collections.singletonList(userRole)));
        // Guardar User en la base de datos
        userRepository.save(newUser);

        return new ResponseEntity<>(new Response(200, "Usuario registrado con éxito"), HttpStatus.OK);
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsUserEntityByEmail(registerRequest.getUsername())) {
            return new ResponseEntity<>(new Response(400, "El email ya está en uso"), HttpStatus.BAD_REQUEST);
        }

        // Crear nuevo objeto User
        User newUser = new User();
        newUser.setEmail(registerRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setFirstName(registerRequest.getFirstname());
        newUser.setLastName(registerRequest.getLastname());
        newUser.setCity(registerRequest.getCity());
        newUser.setAccountNonExpired(true);
        newUser.setAccountNonLocked(true);
        newUser.setCredentialsNonExpired(true);
        newUser.setEnabled(true);

        // Asignar rol ADMIN
        Role adminRole = roleRepository.findByRol("ADMIN").orElseThrow(() -> new RuntimeException("Error: Rol no encontrado"));
        newUser.setRoles(new ArrayList<>(Collections.singletonList(adminRole)));
        // Guardar User en la base de datos
        userRepository.save(newUser);

        return new ResponseEntity<>(new Response(200, "Administrador registrado con éxito"), HttpStatus.OK);
    }

}
