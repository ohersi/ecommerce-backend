package com.example.ecommercebackend.controllers;

import com.example.ecommercebackend.models.Users;
import com.example.ecommercebackend.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

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
    public ResponseEntity<String> newUser(@RequestBody Users user) {
        usersService.newUser(user);
        String message = "User has been created";
        return new ResponseEntity<>( message, HttpStatus.CREATED);

    }

//  SIGN IN
    @PostMapping("signin")
    public Users signInUser(@RequestBody Users user) {
        return usersService.signInUser(user);
    }

    // UPDATE USER
    @PutMapping("user/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody Users user) {
        usersService.updateUser(id, user);
        String message = "User has been updated";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    //DELETE USER
    @DeleteMapping("user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        usersService.deleteUser(id);
        String message = "User has been deleted";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
