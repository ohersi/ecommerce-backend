package com.example.ecommercebackend.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.ecommercebackend.dto.CartDTO;
import com.example.ecommercebackend.exceptions.ResourceNotFoundException;
import com.example.ecommercebackend.models.Cart;
import com.example.ecommercebackend.models.Products;
import com.example.ecommercebackend.models.Users;
import com.example.ecommercebackend.repositories.CartRepository;
import com.example.ecommercebackend.security.JWTUtility;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepo;

    private final ProductsService productsService;

    private final JWTUtility jwtUtility;

    private final UsersService usersService;

    public CartService(CartRepository cartRepo, ProductsService productsService, JWTUtility jwtUtility, UsersService usersService) {
        this.cartRepo = cartRepo;
        this.productsService = productsService;
        this.jwtUtility = jwtUtility;
        this.usersService = usersService;
    }

    public List<Cart> getAllCartItems() {
        return cartRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public List<Cart> getCartItemsByUser(String token) {
        String tokenUsername = jwtUtility.validateToken(token);
        Users user = (Users) usersService.loadUserByUsername(tokenUsername);
        List <Cart> userCartItems = cartRepo.findAllByUsersOrderByDateUpdatedDesc(user);
        return userCartItems;
    }

    public Cart addToCart(String token, CartDTO cartDTO) {
        Products foundProducts = productsService.getProductsById(cartDTO.getProducts_id());

        String tokenUsername = jwtUtility.validateToken(token);
        Users user = (Users) usersService.loadUserByUsername(tokenUsername);

        Cart cart = new Cart();
        cart.setUsers(user);
        cart.setProduct_id(foundProducts);
        cart.setQuantity(cartDTO.getQuantity());
        cart.setDateAdded(cartDTO.getDateAdded());
        cart.setDateUpdated(cartDTO.getDateUpdated());

        return cartRepo.save(cart);
    }

    public Cart updateCart(String token, int id, CartDTO cartDTO) {
        Cart foundCart = cartRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found."));
        String tokenUsername = jwtUtility.validateToken(token);
        Users user = (Users) usersService.loadUserByUsername(tokenUsername);
        if(!user.equals(foundCart.getUsers())){
            throw new ResourceNotFoundException("Token user does not match cart user");
        }
        foundCart.setQuantity(cartDTO.getQuantity());
        foundCart.setDateUpdated(cartDTO.getDateUpdated());
        Cart updateCart = cartRepo.save(foundCart);
        return updateCart;
    }

    public Cart deleteFromCart(String token, int id) {
        Cart cart = cartRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));
        String tokenUsername = jwtUtility.validateToken(token);
        String user = cart.getUsers().getUsername();
        if (!tokenUsername.equals(user)) {
            throw new ResourceNotFoundException("Invalid token");
        }
        cartRepo.delete(cart);
        return cart;
    }
}
