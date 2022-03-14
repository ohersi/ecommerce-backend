package com.example.ecommercebackend.controllers;

import com.example.ecommercebackend.dto.ProductsDTO;
import com.example.ecommercebackend.models.Products;
import com.example.ecommercebackend.service.ProductsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    //    GET ALL PRODUCTS
    @GetMapping("collection/all")
    public List<Products> getAllProducts() {
        return productsService.getAllProducts();
    }

//    GET PRODUCT BY ID
    @GetMapping("products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Products> getProductsById(@PathVariable int id) {
       productsService.getProductsById(id);
    return ResponseEntity.ok(productsService.getProductsById(id));
}

//    GET PRODUCT BY NAME
    @GetMapping("allproducts/{name}")
    public List<Products> getProductByName(@PathVariable("name") String name) {
        return productsService.getProductByName(name);
    }

//    CREATE PRODUCT
    @PostMapping("addproduct")
    public ResponseEntity<String> newProduct(@RequestBody ProductsDTO productsDTO) {
        productsService.newProduct(productsDTO);
        String message = "Product has been added";
        return new ResponseEntity<>( message, HttpStatus.CREATED);
    }

//    UPDATE PRODUCT
    @PutMapping("products/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestBody ProductsDTO productsDTO) {
        productsService.updateProducts(id, productsDTO);
        String message = "Product has been updated";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

//    DELETE PRODUCT
    @DeleteMapping("products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        productsService.deleteProduct(id);
        String message = "Product has been deleted";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
