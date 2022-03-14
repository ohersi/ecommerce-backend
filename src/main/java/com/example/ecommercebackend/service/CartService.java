package com.example.ecommercebackend.service;

import com.example.ecommercebackend.dto.CartDTO;
import com.example.ecommercebackend.exceptions.ResourceNotFoundException;
import com.example.ecommercebackend.models.Cart;
import com.example.ecommercebackend.models.Products;
import com.example.ecommercebackend.models.Users;
import com.example.ecommercebackend.repositories.CartRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepo;

    private final ProductsService productsService;

    private final UsersService usersService;

    public CartService(CartRepository cartRepo, ProductsService productsService, UsersService usersService) {
        this.cartRepo = cartRepo;
        this.productsService = productsService;
        this.usersService = usersService;
    }

    public List<Cart> getAllCartItems() {
        return cartRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Cart addToCart(CartDTO cartDTO) {
        Cart cart = new Cart();
        Products foundProducts = productsService.getProductsById(cartDTO.getProducts_id());
        if (cartDTO.getUsers_id() != 0){
            Users foundUsers = usersService.getUsersById(cartDTO.getUsers_id());
            cart.setUsers_id(foundUsers);
        }
        cart.setProduct_id(foundProducts);
        cart.setQuantity(cartDTO.getQuantity());
        cart.setDate_added(cartDTO.getDate_added());
        cart.setDate_updated(cartDTO.getDate_updated());

        return cartRepo.save(cart);
    }

    public Cart updateCart(int id, CartDTO cartDTO) {
        Cart foundCart = cartRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found."));
        foundCart.setQuantity(cartDTO.getQuantity());
        foundCart.setDate_updated(cartDTO.getDate_updated());
        Cart updateCart = cartRepo.save(foundCart);
        return updateCart;
    }

    public Cart deleteFromCart(int id) {
        Cart cart = cartRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));
        cartRepo.delete(cart);
        return cart;
    }


}
