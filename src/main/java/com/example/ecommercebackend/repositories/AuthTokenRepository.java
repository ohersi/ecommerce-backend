package com.example.ecommercebackend.repositories;

import com.example.ecommercebackend.models.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Integer> {
        AuthToken findByToken(String token);
}
