package com.example.ecommercebackend.controllers;

import com.example.ecommercebackend.exceptions.ResourceNotFoundException;
import com.example.ecommercebackend.models.Products;
import com.example.ecommercebackend.models.Users;
import com.example.ecommercebackend.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1")
public class ProductsController {

    @Autowired
    private ProductsRepository productsRepo;

//    GET ALL PRODUCTS
    @GetMapping("collection/all")
    public List<Products> getAllProducts() {
        return productsRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

//    GET PRODUCT BY ID
    @GetMapping("products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Products> getProductsById(@PathVariable int id) {
        Products products = productsRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    return ResponseEntity.ok(products);
}

//    GET PRODUCT BY NAME
    @GetMapping("allproducts/{name}")
    public List<Products> getProductByName(@PathVariable("name") String name) {
        List<Products> product = productsRepo.findByNameIgnoreCase(name);
        if(product.isEmpty()) {
            System.out.println(new ResourceNotFoundException("Product(s): " + name + " not found"));
        }
        return productsRepo.findByNameIgnoreCase(name);
    }

//    CREATE PRODUCT
    @PostMapping("addproduct")
    public Products newProduct(@RequestBody Products product) {
        return productsRepo.save(product);
    }

//    UPDATE PRODUCT
    @PutMapping("products/{id}")
    public ResponseEntity<Products> updateProduct(@PathVariable int id, @RequestBody Products newProductInfo) {
        Products foundProduct = productsRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
        foundProduct.setName(newProductInfo.getName());
        foundProduct.setDescription(newProductInfo.getDescription());
        foundProduct.setPrice(newProductInfo.getPrice());
        foundProduct.setImage_URL(newProductInfo.getImageURL());
        foundProduct.setStock(newProductInfo.getStock());

        Products updateProduct = productsRepo.save(foundProduct);
        return new ResponseEntity<Products>(updateProduct, HttpStatus.CREATED);
    }
}
