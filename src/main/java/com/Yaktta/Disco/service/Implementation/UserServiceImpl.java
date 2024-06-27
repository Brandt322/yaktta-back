package com.Yaktta.Disco.service.Implementation;

import com.Yaktta.Disco.exceptions.InternalServerError;
import com.Yaktta.Disco.exceptions.NotFoundException;
import com.Yaktta.Disco.models.entities.User;
import com.Yaktta.Disco.models.mapper.UserMapper;
import com.Yaktta.Disco.models.response.UserResponse;
import com.Yaktta.Disco.repository.UserRepository;
import com.Yaktta.Disco.service.UserService;
import com.Yaktta.Disco.utils.Messages;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByUsername(String username) {
        try {
            Optional<User> optionalUser = userRepository.findUserEntityByEmail(username);
            User user = optionalUser.orElseThrow(() -> new NotFoundException(Messages.USER_CREDENTIALS.getMessage()));
            return user;
        } catch (NotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new InternalServerError(e.getMessage());
        }
    }

    @Override
    public User getUserById(Long id) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            User user = optionalUser.orElseThrow(() -> new NotFoundException(Messages.USER_NOT_FOUND.getMessage()));
            return user;
        } catch (NotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new InternalServerError(e.getMessage());
        }
    }


}
