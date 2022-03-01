package com.example.ecommercebackend.controllers;

import com.example.ecommercebackend.exceptions.ResourceNotFoundException;
import com.example.ecommercebackend.models.Category;
import com.example.ecommercebackend.models.Users;
import com.example.ecommercebackend.repositories.CategoryRepository;
import com.example.ecommercebackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1")
public class CategoryController {

    @Autowired
//    private CategoryRepository categoryRepo;
    CategoryService categoryService;

//    GET ALL CATEGORIES
    @GetMapping("categories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

//    GET CATEGORY BY NAME
    @GetMapping("category/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getCategoryByName(@PathVariable("name") String name) {
        return categoryService.getCategoryByName(name);
    }

//    CREATE CATEGORY
    @PostMapping("addcategory")
    public Category newCategory(@RequestBody Category category) {
        return categoryService.newCategory(category);
    }

//    UPDATE CATEGORY
    @PutMapping("category/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable int id, @RequestBody Category category) {
            categoryService.updateCategory(id, category);
        return new ResponseEntity<Category>(categoryService.updateCategory(id, category), HttpStatus.CREATED);
    }

//    DELETE CATEGORY
    @DeleteMapping("category/{id}")
    public ResponseEntity<String> deleteCategory (@PathVariable int id) {
            categoryService.deleteCategory(id);
        String message = "Category has been deleted";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}

