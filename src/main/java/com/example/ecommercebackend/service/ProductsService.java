package com.example.ecommercebackend.service;

import com.example.ecommercebackend.exceptions.ResourceNotFoundException;
import com.example.ecommercebackend.models.Products;
import com.example.ecommercebackend.models.Users;
import com.example.ecommercebackend.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {

    @Autowired
    private ProductsRepository productsRepo;

    public List<Products> getAllProducts() {
        return productsRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Products getProductsById(int id) {
        Products products = productsRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return products;
    }

    public List<Products> getProductByName(String name) {
        List<Products> product = productsRepo.findByNameIgnoreCase(name);
        if(product.isEmpty()) {
            System.out.println(new ResourceNotFoundException("Product(s): " + name + " not found"));
        }
        return productsRepo.findByNameIgnoreCase(name);
    }

    public Products newProduct(Products product) {
        return productsRepo.save(product);
    }

    public Products updateProducts(int id, Products newProductInfo) {
        Products foundProduct = productsRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
        foundProduct.setName(newProductInfo.getName());
        foundProduct.setDescription(newProductInfo.getDescription());
        foundProduct.setPrice(newProductInfo.getPrice());
        foundProduct.setImageURL(newProductInfo.getImageURL());
        foundProduct.setStock(newProductInfo.getStock());

        Products updateProduct = productsRepo.save(foundProduct);
        return updateProduct;
    }

    public Products deleteProduct(int id) {
        Products products = productsRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        productsRepo.deleteById(id);
        return products;
    }
}
