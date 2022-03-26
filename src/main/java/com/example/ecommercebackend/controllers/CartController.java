package com.example.ecommercebackend.controllers;

import com.example.ecommercebackend.dto.CartDTO;
import com.example.ecommercebackend.models.Cart;
import com.example.ecommercebackend.repositories.UsersRepository;
import com.example.ecommercebackend.service.CartService;
import com.example.ecommercebackend.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1")
public class CartController {

    private final CartService cartService;

    private final UsersService usersService;

    private final UsersRepository usersRepo;

    private String HEADER = "Authorization";
    private String TOKEN_PREFIX = "Bearer ";

    public CartController(CartService cartService, UsersService usersService, UsersRepository usersRepo) {
        this.cartService = cartService;
        this.usersService = usersService;
        this.usersRepo = usersRepo;
    }

    public String getTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader(HEADER);
        String JWT = null;
        if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
            JWT = authHeader.substring(7);
        }
        return JWT;
    }

//    GET ALL CART ITEMS
    @GetMapping("allcartitems")
    public List<Cart> getAllCartItems() {
        return cartService.getAllCartItems();
    }

//    GET CART ITEMS BY USER
    @GetMapping("cart")
    public List<Cart> getCartItemsByUser(HttpServletRequest request) {
        return cartService.getCartItemsByUser(getTokenFromHeader(request));
    }

//    ADD TO CART
    @PostMapping("addtocart")
    public ResponseEntity<String> addToCart(HttpServletRequest request, @RequestBody CartDTO cartDTO) {
        cartService.addToCart(getTokenFromHeader(request), cartDTO);
        String message = "Item has been added to cart";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

//    UPDATE ITEM IN CART
    @PutMapping("updatecart/{id}")
    public ResponseEntity<String> updateCart(HttpServletRequest request, @PathVariable int id, @RequestBody CartDTO cartDTO) {
        cartService.updateCart(getTokenFromHeader(request), id, cartDTO);
        String message = "Cart has been updated";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

//    DELETE ITEM FROM CART
    @DeleteMapping("deleteitem/{id}")
    public ResponseEntity<String> deleteFromCart(HttpServletRequest request, @PathVariable int id) {
        cartService.deleteFromCart(getTokenFromHeader(request), id);
        String message = "Item has been deleted";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
