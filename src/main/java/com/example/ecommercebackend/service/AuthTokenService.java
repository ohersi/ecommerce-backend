package com.example.ecommercebackend.service;

import com.example.ecommercebackend.models.AuthToken;
import com.example.ecommercebackend.repositories.AuthTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthTokenService {

    private final AuthTokenRepository authTokenRepo;

    public AuthTokenService(AuthTokenRepository authTokenRepo) {
        this.authTokenRepo = authTokenRepo;
    }

    public void saveAuthToken(AuthToken token) {
        authTokenRepo.save(token);
    }
}
