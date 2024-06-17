package com.Yaktta.Disco.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponse {
    private Long id_user;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String city;
    private String postalCode;
    private List<String> roles;
}
