package com.example.ecommercebackend.service;

import com.example.ecommercebackend.exceptions.ResourceNotFoundException;
import com.example.ecommercebackend.models.AuthToken;
import com.example.ecommercebackend.models.Users;
import com.example.ecommercebackend.repositories.UsersRepository;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepo;
    private final AuthTokenService authTokenService;
    private final static String USER_NOT_FOUND = "User '%s' was not found";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersService(UsersRepository usersRepo, AuthTokenService authTokenService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepo = usersRepo;
        this.authTokenService = authTokenService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<Users> getAllUsers() {
        return usersRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepo.findByUsername(username);
                if (users == null) {
                    throw new UsernameNotFoundException(String.format(USER_NOT_FOUND, username));
                }
        return users;
    }

    public Users getUsersById(int id) {
        Users users = usersRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return users;
    }
    @Transactional
    public Users newUser(Users user) {
        Users newUser = new Users();
        Users foundUserName = usersRepo.findByUsername(user.getUsername());
        Users foundUserEmail = usersRepo.findByEmail(user.getEmail());
        String bCryptPassword = bCryptPasswordEncoder.encode(user.getPassword());
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
        newUser.setPassword(bCryptPassword);

        Users newUserFinal = usersRepo.save(newUser);
        String token = UUID.randomUUID().toString();
        AuthToken authToken = new AuthToken(token,
                LocalDateTime.now(),
                newUser
                );
        authTokenService.saveAuthToken(authToken);

        return newUserFinal;
    }

    public Users signInUser(Users user) {
        UserDetails signInUser = loadUserByUsername(user.getUsername());
        if(!bCryptPasswordEncoder.matches(user.getPassword(), signInUser.getPassword())) {
            throw new ResourceNotFoundException("Invalid information");
        }
        return (Users) signInUser;
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
