package com.example.ecommercebackend.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.ecommercebackend.security.JWTUtility;
import com.example.ecommercebackend.service.UsersService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtility jwtUtility;
    private final UsersService usersService;

    private String HEADER = "Authorization";
    private String TOKEN_PREFIX = "Bearer ";

    public JWTFilter(JWTUtility jwtUtility, UsersService usersService) {
        this.jwtUtility = jwtUtility;
        this.usersService = usersService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HEADER);
        if(authHeader != null && authHeader.startsWith(TOKEN_PREFIX)){
            String JWT = authHeader.substring(TOKEN_PREFIX.length());
            if(JWT == null){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
            }
            else {
                try{
                    String username = jwtUtility.validateToken(JWT);
                    if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                        UserDetails userDetails = usersService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        username,
                                        userDetails.getPassword(),
                                        userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }catch(JWTVerificationException exception){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
