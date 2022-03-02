package com.example.ecommercebackend.controllers;

import com.example.ecommercebackend.models.Cart;
import com.example.ecommercebackend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1")
public class CartController {

    @Autowired
    CartService cartService;

    // GET CART ITEMS
    @GetMapping("cart")
    public <List>Cart getAllCartItems() {
        return null;
    }

    // ADD TO CART
    @PostMapping("addtocart")
    public ResponseEntity<Cart> addToCart() {
        return null;
    }

}
