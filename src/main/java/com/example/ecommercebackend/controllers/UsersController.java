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

//    GET ALL USERS
    @GetMapping("allusers")
    public List<Users> getAllUsers() {
        return usersRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
//    GET USER BY ID
    @GetMapping("user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Users> getUsersById(@PathVariable int id) {
        Users users = usersRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseEntity.ok(users);
    }

//  SIGN UP
    @PostMapping("signup")
    public Users newUser(@RequestBody Users newUser) {
        return usersRepo.save(newUser);
    }

//  SIGN IN
//    @PostMapping("signin")
//    public Users signInUser(@RequestBody Users user) {
//        return usersRepo.signIn(user);
//    }

    // UPDATE USER
    @PutMapping("user/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable int id, @RequestBody Users newUserInfo) {
        Users foundUser = usersRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        foundUser.setUsername(newUserInfo.getUsername());
        foundUser.setFirstname(newUserInfo.getFirstname());
        foundUser.setLastname(newUserInfo.getLastname());
        foundUser.setEmail(newUserInfo.getEmail());
        foundUser.setPassword(newUserInfo.getPassword());

        Users updatedUser = usersRepo.save(foundUser);
        return new ResponseEntity<Users>(updatedUser, HttpStatus.CREATED);
    }

    //DELETE USER
    @DeleteMapping("user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        usersRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        usersRepo.deleteById(id);
        String message = "User has been deleted";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
