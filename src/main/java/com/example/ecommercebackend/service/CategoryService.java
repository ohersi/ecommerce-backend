package com.example.ecommercebackend.service;

import com.example.ecommercebackend.exceptions.ResourceNotFoundException;
import com.example.ecommercebackend.models.Category;
import com.example.ecommercebackend.repositories.CategoryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepo;

    public CategoryService(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    //    GET ALL CATEGORIES
    public List<Category> getAllCategories() {
        return categoryRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

//    GET CATEGORY BY NAME
    public List<Category> getCategoryByName(String name) {
        List<Category> categories = categoryRepo.findByNameIgnoreCase(name);
        if(categories== null) {
            throw new ResourceNotFoundException("Category " + name + " not found");
        }
        return categoryRepo.findByNameIgnoreCase(name);
    }

//    CREATE CATEGORY
    public Category newCategory(Category category) {
        return categoryRepo.save(category);
    }

//    UPDATE CATEGORY
    public Category updateCategory(int id, Category newCategoryInfo) {
        Category foundCategory = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found."));
        foundCategory.setName(newCategoryInfo.getName());
        foundCategory.setDescription(newCategoryInfo.getDescription());

        Category updatedCategory = categoryRepo.save(foundCategory);
        return updatedCategory;
    }

//    DELETE CATEGORY
    public Category deleteCategory(int id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryRepo.deleteById(id);
        return category;
    }
}
