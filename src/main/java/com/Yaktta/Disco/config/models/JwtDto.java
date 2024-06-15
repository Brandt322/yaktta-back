package com.Yaktta.Disco.config.models;

import com.Yaktta.Disco.models.entities.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class JwtDto {
    private User userPrincipal;
    private String bearer = "Bearer";
    private String token;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtDto(User userPrincipal, String token, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.userPrincipal = userPrincipal;
        this.authorities = authorities;
    }
}
