package com.ecommerce.backend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ecommerce.backend.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JWTService {
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String Issuer;

    @Value("${jwt.expiryInSeconds}")
    private int expiryInSeconds;

    private Algorithm algorithm;

    private static final String USERNAME_KEY = "USERNAME";

    @PostConstruct
    public void postConstruct(){
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    public String generateJWT(User user){
        return JWT.create()
        .withClaim(USERNAME_KEY, user.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + (1000*expiryInSeconds)))
        .withIssuer(Issuer)
        .sign(algorithm);
    }
}
