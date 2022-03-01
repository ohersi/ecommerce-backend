package com.example.ecommercebackend.service;

import com.example.ecommercebackend.exceptions.ResourceNotFoundException;
import com.example.ecommercebackend.models.Users;
import com.example.ecommercebackend.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepo;

    public List<Users> getAllUsers() {
        return usersRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Users getUsersById(int id) {
        Users users = usersRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return users;
    }

    public Users newUser(Users user) {
        return usersRepo.save(user);
    }

    public Users updateUser(int id, Users newUserInfo) {
        Users foundUser = usersRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        foundUser.setUsername(newUserInfo.getUsername());
        foundUser.setFirstname(newUserInfo.getFirstname());
        foundUser.setLastname(newUserInfo.getLastname());
        foundUser.setEmail(newUserInfo.getEmail());
        foundUser.setPassword(newUserInfo.getPassword());

        Users updatedUser = usersRepo.save(foundUser);
        return updatedUser;
    }

    public Users deleteUser(int id) {
        Users users = usersRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        usersRepo.deleteById(id);
        return users;
    }
}
