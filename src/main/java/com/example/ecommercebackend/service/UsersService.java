package com.example.ecommercebackend.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.ecommercebackend.dto.UsersDTO;
import com.example.ecommercebackend.exceptions.ResourceNotFoundException;
import com.example.ecommercebackend.models.Users;
import com.example.ecommercebackend.repositories.UsersRepository;
import com.example.ecommercebackend.security.JWTUtility;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepo;
    private final AuthenticationManager authenticationManager;
    private final JWTUtility jwtUtility;
    private final static String USER_NOT_FOUND = "User '%s' was not found";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersService(UsersRepository usersRepo, @Lazy AuthenticationManager authenticationManager, JWTUtility jwtUtility, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepo = usersRepo;
        this.authenticationManager = authenticationManager;
        this.jwtUtility = jwtUtility;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Boolean tokenUserMatchesUser(String token, Users user) {
        String tokenUsername = jwtUtility.validateToken(token);
        if (!tokenUsername.equals(user.getUsername())) {
            throw new JWTVerificationException("JWT Token does not contain same user");
        }
        return true;
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

    public UsersDTO newUser(Users user) {
        Users foundUserName = usersRepo.findByUsername(user.getUsername());
        Users foundUserEmail = usersRepo.findByEmail(user.getEmail());
        String bCryptPassword = bCryptPasswordEncoder.encode(user.getPassword());
        if(foundUserName != null) {
            throw new ResourceNotFoundException("User already exist");
        }
        if(foundUserEmail !=null) {
            throw new ResourceNotFoundException("Email already in use");
        }
        Users newUser = new Users();
        newUser.setUsername(user.getUsername());
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bCryptPassword);

        Users newUserFinal = usersRepo.save(newUser);
        String token = jwtUtility.generateToken(loadUserByUsername(newUser.getUsername()));
        UsersDTO usersResponse = new UsersDTO();
        usersResponse.setUsername(newUserFinal.getUsername());
        usersResponse.setEmail(newUserFinal.getEmail());
        usersResponse.setToken(token);

        return usersResponse;
    }

    public UsersDTO signInUser(UsersDTO usersDTO) throws BadCredentialsException {
        UserDetails signInUser = loadUserByUsername(usersDTO.getUsername());
        Users user = usersRepo.findByUsername(signInUser.getUsername());
        if(!bCryptPasswordEncoder.matches(usersDTO.getPassword(), signInUser.getPassword())) {
            throw new ResourceNotFoundException("Invalid information");
        }
        try{
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(usersDTO.getUsername(), usersDTO.getPassword());
            authenticationManager.authenticate(authToken);
        }
        catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid credentials", e);
        }
        String token = jwtUtility.generateToken(signInUser);
        UsersDTO usersResponse = new UsersDTO();
        usersResponse.setUsername(signInUser.getUsername());
        usersResponse.setEmail(user.getEmail());
        usersResponse.setToken(token);
        return usersResponse;
    }

    public Users updateUser(String token, Users newUserInfo) {
        String tokenUsername = jwtUtility.validateToken(token);
        Users foundUser = usersRepo.findByUsername(tokenUsername);
        if (foundUser == null) {
            throw new ResourceNotFoundException("User not found.");
        }
        if (!tokenUserMatchesUser(token, newUserInfo)) {
            throw new ResourceNotFoundException("Invalid token");
        }
        foundUser.setUsername(newUserInfo.getUsername());
        foundUser.setFirstname(newUserInfo.getFirstname());
        foundUser.setLastname(newUserInfo.getLastname());
        foundUser.setEmail(newUserInfo.getEmail());
        foundUser.setPassword(newUserInfo.getPassword());

        Users updatedUser = usersRepo.save(foundUser);
        return updatedUser;
    }

    public Users deleteUser(String token, UsersDTO usersDTO) {
        String tokenUsername = jwtUtility.validateToken(token);
        Users foundUser = usersRepo.findByUsername(tokenUsername);
        if (foundUser == null) {
            throw new ResourceNotFoundException("User not found.");
        }
        if (!foundUser.getUsername().equals(usersDTO.getUsername())) {
            throw new ResourceNotFoundException("Invalid user deletion attempt");
        }
        usersRepo.deleteById(foundUser.getId());
        return foundUser;
    }
}
