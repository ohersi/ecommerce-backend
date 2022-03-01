package com.example.ecommercebackend.controllers;

import com.example.ecommercebackend.exceptions.ResourceNotFoundException;
import com.example.ecommercebackend.models.Category;
import com.example.ecommercebackend.models.Users;
import com.example.ecommercebackend.repositories.CategoryRepository;
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
    private CategoryRepository categoryRepo;

//    GET ALL CATEGORIES
    @GetMapping("categories")
    public List<Category> getAllCategories() {
        return categoryRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

//    GET USER BY NAME
    @GetMapping("category/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<Category> GetCategoryByName(@PathVariable String name) {
        List<Category> categories = categoryRepo.findByNameIgnoreCase(name);
        if(categories.isEmpty()) {
            System.out.println(new ResourceNotFoundException("Category " + name + " not found"));
        }
        return categoryRepo.findByNameIgnoreCase(name);
    }

//    CREATE CATEGORY
    @PostMapping("addcategory")
    public Category newCategory(@RequestBody Category category) {
        return categoryRepo.save(category);
    }

//    UPDATE CATEGORY
    @PutMapping("category/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable int id, @RequestBody Category newCategoryInfo) {
        Category foundCategory = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found."));
        foundCategory.setName(newCategoryInfo.getName());
        foundCategory.setDescription(newCategoryInfo.getDescription());

        Category updatedCategory = categoryRepo.save(foundCategory);
        return new ResponseEntity<Category>(updatedCategory, HttpStatus.CREATED);
    }

//    DELETE USER
    @DeleteMapping("category/{id}")
    public ResponseEntity<String> deleteCategory (@PathVariable int id) {
        categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryRepo.deleteById(id);
        String message = "Category has been deleted";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}

