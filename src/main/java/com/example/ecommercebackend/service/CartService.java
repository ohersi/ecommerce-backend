package com.example.ecommercebackend.service;

import com.example.ecommercebackend.dto.CartDTO;
import com.example.ecommercebackend.exceptions.ResourceNotFoundException;
import com.example.ecommercebackend.models.Cart;
import com.example.ecommercebackend.models.Category;
import com.example.ecommercebackend.models.Products;
import com.example.ecommercebackend.models.Users;
import com.example.ecommercebackend.repositories.CartRepository;
import com.example.ecommercebackend.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
//        System.out.println("Cart User from cart.getUsers_id: ->" + cart.getUsers_id().getId());
//        System.out.println("Cart User from user.getUsers_id: ->" + user.getId());
//        if(cart.getUsers_id().getId() != user.getId()){
//            throw new ResourceNotFoundException("Invalid user access; cannot edit cart.");
//        }
        cartRepo.delete(cart);
        return cart;
    }


}
