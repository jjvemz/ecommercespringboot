package com.ecommerce.backend.service;

import com.ecommerce.backend.model.User;
import com.ecommerce.backend.api.model.LoginBody;
import com.ecommerce.backend.api.model.RegistrationBody;
import com.ecommerce.backend.exception.UserAlreadyExistsException;
import com.ecommerce.backend.model.dao.UserDAO;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private EncryptionService encryptionService;
    private UserDAO userDAO;
    private JWTService jwtService;

    public UserService(UserDAO userDAO,EncryptionService encryptionService, JWTService jwtService){
        this.userDAO = userDAO;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
        

    }

    public User registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {
        if (userDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
                || userDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()) {
            logger.warn("El usuario ya existe con el email {} o username {}", registrationBody.getEmail(), registrationBody.getUsername());
            throw new UserAlreadyExistsException();
        }
        
        User user = new User();
        user.setEmail(registrationBody.getEmail());
        user.setUsername(registrationBody.getUsername());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        
        // Encriptar y loguear la contraseña
        String encryptedPassword = encryptionService.encryptPassword(registrationBody.getPassword());
        logger.info("Contraseña encriptada: {}", encryptedPassword);
        user.setPassword(encryptedPassword);
        
        // Intentar guardar el usuario
        User savedUser = userDAO.save(user);
        logger.info("Usuario guardado: {}", savedUser);
    
        return savedUser;
    }

    public String loginUser(LoginBody loginBody){
        Optional<User> opUser = userDAO.findByUsernameIgnoreCase(loginBody.getUsername());
        if(opUser.isPresent())
        {
            User user = opUser.get();
            if(encryptionService.verifyPassword(loginBody.getPassword(),user.getPassword())){
                return jwtService.generateJWT(user);
            }
        }
        return null;
    }
}
