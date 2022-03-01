package com.example.ecommercebackend.repositories;

import com.example.ecommercebackend.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findByNameIgnoreCase(String name);
}
