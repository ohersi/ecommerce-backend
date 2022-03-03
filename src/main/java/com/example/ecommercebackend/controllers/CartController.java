package com.example.ecommercebackend.controllers;

import com.example.ecommercebackend.dto.CartDTO;
import com.example.ecommercebackend.models.Cart;
import com.example.ecommercebackend.models.Products;
import com.example.ecommercebackend.models.Users;
import com.example.ecommercebackend.service.CartService;
import com.example.ecommercebackend.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1")
public class CartController {

    @Autowired
    CartService cartService;

//    GET CART ITEMS
    @GetMapping("cart")
    public List<Cart> getAllCartItems() {
        return cartService.getAllCartItems();
    }

//    ADD TO CART
    @PostMapping("addtocart")
    public ResponseEntity<String> addToCart(@RequestBody CartDTO cartDTO) {
        cartService.addToCart(cartDTO);
        String message = "Item has been added to cart";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

//    UPDATE CART
    @DeleteMapping("deleteitem/{id}")
    public ResponseEntity<String> deleteFromCart(@PathVariable int id, CartDTO cartDTO) {
        cartService.deleteFromCart(id, cartDTO);
        String message = "Item has been deleted";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
