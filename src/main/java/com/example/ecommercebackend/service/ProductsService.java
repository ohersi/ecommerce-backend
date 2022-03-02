package com.example.ecommercebackend.service;

import com.example.ecommercebackend.dto.ProductsDTO;
import com.example.ecommercebackend.exceptions.ResourceNotFoundException;
import com.example.ecommercebackend.models.Category;
import com.example.ecommercebackend.models.Products;
import com.example.ecommercebackend.models.Users;
import com.example.ecommercebackend.repositories.CategoryRepository;
import com.example.ecommercebackend.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {

    @Autowired
    private ProductsRepository productsRepo;
    @Autowired
    private CategoryRepository categoryRepo;

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

    public Products newProduct(ProductsDTO productsDTO) {
        Products newProduct = new Products();
        Category foundCategory = categoryRepo.findById(productsDTO.getCategory_id())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found."));
        newProduct.setName(productsDTO.getName());
        newProduct.setDescription(productsDTO.getDescription());
        newProduct.setPrice(productsDTO.getPrice());
        newProduct.setImageURL(productsDTO.getImageURL());
        newProduct.setStock(productsDTO.getStock());
        newProduct.setCategory_id(foundCategory);

        return productsRepo.save(newProduct);
    }

    public Products updateProducts(int id, ProductsDTO productsDTO) {
        Products foundProduct = productsRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
        Category foundCategory = categoryRepo.findById(productsDTO.getCategory_id())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found."));
        foundProduct.setName(productsDTO.getName());
        foundProduct.setDescription(productsDTO.getDescription());
        foundProduct.setPrice(productsDTO.getPrice());
        foundProduct.setImageURL(productsDTO.getImageURL());
        foundProduct.setStock(productsDTO.getStock());
        foundProduct.setCategory_id(foundCategory);

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
