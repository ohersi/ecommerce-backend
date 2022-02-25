package com.example.ecommercebackend.controllers;

import com.example.ecommercebackend.exceptions.ResourceNotFoundException;
import com.example.ecommercebackend.models.Users;
import com.example.ecommercebackend.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class UsersController {

    @Autowired
    private UsersRepository usersRepo;

    @GetMapping("allusers")
    public List<Users> getAllUsers() {
        return usersRepo.findAll();
    }

    @GetMapping("users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Users> getUsersById(@PathVariable int id) {
        Users users = usersRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseEntity.ok(users);
    }

}
