package com.Yaktta.Disco.service;

import com.Yaktta.Disco.models.entities.User;
import com.Yaktta.Disco.models.response.UserResponse;

public interface UserService {
    User getUserByUsername(String username);
}
