package com.example.ecommercebackend.controllers;


import com.example.ecommercebackend.dto.CheckoutItemDTO;
import com.example.ecommercebackend.dto.StripeResponseDTO;
import com.example.ecommercebackend.service.OrdersService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.net.StripeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1")
public class OrdersController {

    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponseDTO> checkoutList(@RequestBody List<CheckoutItemDTO> checkoutItemDTOList) throws StripeException {
        Session session = ordersService.createSession(checkoutItemDTOList);
        StripeResponseDTO stripeResponse = new StripeResponseDTO(session.getUrl());
        return new ResponseEntity<>(stripeResponse,HttpStatus.OK);
    }
}
