package com.example.ecommercebackend.service;

import com.example.ecommercebackend.exceptions.ResourceNotFoundException;
import com.example.ecommercebackend.models.Users;
import com.example.ecommercebackend.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
        Users newUser = new Users();
        Users foundUserName = usersRepo.findByUsername(user.getUsername());
        Users foundUserEmail = usersRepo.findByEmail(user.getEmail());
        if(foundUserName != null) {
            throw new ResourceNotFoundException("User already exist");
        }
        if(foundUserEmail !=null) {
            throw new ResourceNotFoundException("Email already in use");
        }
        newUser.setUsername(user.getUsername());
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());

        Users newUserFinal = usersRepo.save(newUser);
        return newUserFinal;
    }

    public Users signInUser(Users user) {
        Users signInUser = usersRepo.findByUsername(user.getUsername());
        if(signInUser.getUsername().isEmpty()) {
           throw new ResourceNotFoundException("Invalid information");
        }
        if(!signInUser.getPassword().equals(user.getPassword())){
            throw new ResourceNotFoundException("Invalid information");
        }
        return signInUser;
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
