package it.pinoelefante.restaurant.service;

import org.springframework.stereotype.Component;

@Component
public class SimpleAuthenticationService {

    private String getUser(String token) {
        throw new IllegalArgumentException("Token not found");
    }
}
