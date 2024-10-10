package com.ecommerce.backend.api.security;

import com.auth0.jwt.exceptions.JWTDecodeException;

import com.ecommerce.backend.model.User;
import com.ecommerce.backend.model.dao.UserDAO;
import com.ecommerce.backend.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
 
    private JWTService M__jwtService;

    private UserDAO M__UserDao;


    public JWTRequestFilter(JWTService jwtService, UserDAO UserDao){
        jwtService= M__jwtService;
        UserDao = M__UserDao;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(7);
            try{
                String UserName = M__jwtService.getUsername(token);
                Optional<User> optUser = M__UserDao.findByUsernameIgnoreCase(UserName);
                if(optUser.isPresent()){
                    User currUser = optUser.get();
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(currUser, null, new ArrayList<>());
                    auth.setDetails( new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }

            }catch (JWTDecodeException res ){
                System.out.println(res);
            }
        }
    }
}
