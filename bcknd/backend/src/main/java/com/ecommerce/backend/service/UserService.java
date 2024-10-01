package com.ecommerce.backend.service;

import com.ecommerce.backend.model.User;
import com.ecommerce.backend.api.model.LoginBody;
import com.ecommerce.backend.api.model.RegistrationBody;
import com.ecommerce.backend.exception.UserAlreadyExistsException;
import com.ecommerce.backend.model.dao.UserDAO;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private EncryptionService encryptionService;
    private UserDAO userDAO;
    private JWTService jwtService;
    public UserService(UserDAO userDAO,EncryptionService encryptionService, JWTService jwtService){
        this.userDAO = userDAO;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;

    }

    public User registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException{
        if(userDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
            || UserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()){
                throw new UserAlreadyExistsException();
            }
            User user = new User();
            user.setEmail(registrationBody.getEmail());
            user.setUsername(registrationBody.getUsername());
            user.setFirstName(registrationBody.getFirstName());
            user.setLastName(registrationBody.getLastName());

            user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));

            return userDAO.save(user);
    }

    public String loginUser(LoginBody loginBody){
        Optional<User> opUser = UserDAO.findByUsernameIgnoreCase(loginBody.getUsername());
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
