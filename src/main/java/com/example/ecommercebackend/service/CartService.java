package com.example.ecommercebackend.service;

import com.example.ecommercebackend.dto.CartDTO;
import com.example.ecommercebackend.exceptions.ResourceNotFoundException;
import com.example.ecommercebackend.models.Cart;
import com.example.ecommercebackend.models.Category;
import com.example.ecommercebackend.models.Products;
import com.example.ecommercebackend.models.Users;
import com.example.ecommercebackend.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepo;
    @Autowired
    ProductsService productsService;
    @Autowired
    UsersService usersService;

    public List<Cart> getAllCartItems() {
        return cartRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Cart addToCart(CartDTO cartDTO) {
        Cart cart = new Cart();
        Users foundUsers = usersService.getUsersById(cartDTO.getUsers_id());
        Products foundProducts = productsService.getProductsById(cartDTO.getProducts_id());
        cart.setUsers_id(foundUsers);
        cart.setProduct_id(foundProducts);
        cart.setQuantity(cartDTO.getQuantity());
        cart.setDate_added(cartDTO.getDate_added());
        cart.setDate_updated(cartDTO.getDate_updated());

        return cartRepo.save(cart);
    }

    public Cart deleteFromCart(int id, CartDTO cartDTO) {
        Cart cart = cartRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));
        //TODO: Prevent unauthorized user access for deleting other carts
        cartRepo.delete(cart);
        return cart;
    }
}
