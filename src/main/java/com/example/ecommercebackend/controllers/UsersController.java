package com.example.ecommercebackend.controllers;

import com.example.ecommercebackend.exceptions.ResourceNotFoundException;
import com.example.ecommercebackend.models.Users;
import com.example.ecommercebackend.repositories.UsersRepository;
import com.example.ecommercebackend.service.UsersService;
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
//    private UsersRepository usersRepo;
    UsersService usersService;

//    GET ALL USERS
    @GetMapping("allusers")
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }

//    GET USER BY ID
    @GetMapping("user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Users> getUsersById(@PathVariable int id) {
        usersService.getUsersById(id);
        return ResponseEntity.ok(usersService.getUsersById(id));
    }

//  SIGN UP
    @PostMapping("signup")
    public Users newUser(@RequestBody Users user) {
        return usersService.newUser(user);
    }

//  SIGN IN
//    @PostMapping("signin")
//    public Users signInUser(@RequestBody Users user) {
//        return usersRepo.signIn(user);
//    }

    // UPDATE USER
    @PutMapping("user/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable int id, @RequestBody Users user) {
        usersService.updateUser(id, user);
        return new ResponseEntity<Users>(usersService.updateUser(id, user), HttpStatus.CREATED);
    }

    //DELETE USER
    @DeleteMapping("user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        usersService.deleteUser(id);
        String message = "User has been deleted";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
