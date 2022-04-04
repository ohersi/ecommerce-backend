package com.example.ecommercebackend.controllers;

import com.example.ecommercebackend.dto.ProfileDTO;
import com.example.ecommercebackend.dto.UsersDTO;
import com.example.ecommercebackend.models.Users;
import com.example.ecommercebackend.security.JWTUtility;
import com.example.ecommercebackend.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class UsersController {

    private final UsersService usersService;
    private final JWTUtility jwtUtility;

    private String HEADER = "Authorization";
    private String TOKEN_PREFIX = "Bearer ";

    public UsersController(UsersService usersService, JWTUtility jwtUtility) {
        this.usersService = usersService;
        this.jwtUtility = jwtUtility;
    }

    public String getTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader(HEADER);
        String JWT = null;
        if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
            JWT = authHeader.substring(7);
        }
        return JWT;
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
        Users response = usersService.getUsersById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    GET USER BY TOKEN
    @GetMapping("profile")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProfileDTO> getUsersById(HttpServletRequest request) {
        ProfileDTO response = usersService.getUserInfoByToken(getTokenFromHeader(request));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    REGISTER
    @PostMapping("register")
    public UsersDTO newUser(@RequestBody Users users) {
        UsersDTO response = usersService.newUser(users);
        return response;
    }

//    LOG IN
    @PostMapping("login")
    public UsersDTO signInUser(@RequestBody UsersDTO usersDTO) throws BadCredentialsException {
        UsersDTO response = usersService.signInUser(usersDTO);
        return response;
    }

//    UPDATE USER
    @PutMapping("updateuser")
    public ResponseEntity<String> updateUser(HttpServletRequest request, @RequestBody Users user) {
        usersService.updateUser(getTokenFromHeader(request), user);
        String message = "User has been updated";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

//    DELETE USER
    @DeleteMapping("deleteuser")
    public ResponseEntity<String> deleteUser(HttpServletRequest request, @RequestBody UsersDTO usersDTO) {
        usersService.deleteUser(getTokenFromHeader(request), usersDTO);
        String message = "User has been deleted";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
