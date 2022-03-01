package com.example.ecommercebackend.controllers;

import com.example.ecommercebackend.models.Products;
import com.example.ecommercebackend.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1")
public class ProductsController {

    @Autowired
    ProductsService productsService;

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
    public Products newProduct(@RequestBody Products product) {
        return productsService.newProduct(product);
    }

//    UPDATE PRODUCT
    @PutMapping("products/{id}")
    public ResponseEntity<Products> updateProduct(@PathVariable int id, @RequestBody Products product) {
        productsService.updateProducts(id, product);
        return new ResponseEntity<Products>(productsService.updateProducts(id, product), HttpStatus.CREATED);
    }

//    DELETE PRODUCT
    @DeleteMapping("products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        productsService.deleteProduct(id);
        String message = "User has been deleted";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
