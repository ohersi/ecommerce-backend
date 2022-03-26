package com.example.ecommercebackend.repositories;

import com.example.ecommercebackend.models.Cart;
import com.example.ecommercebackend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByUsersOrderByDateUpdatedDesc(Users user);
}
