package com.ecommerce.backend.service;

import com.ecommerce.backend.model.User;
import com.ecommerce.backend.api.model.RegistrationBody;
import com.ecommerce.backend.exception.UserAlreadyExistsException;
import com.ecommerce.backend.model.dao.UserDAO;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private EncryptionService encryptionService;
    private UserDAO userDAO;

    public UserService(UserDAO userDAO,EncryptionService encryptionService){
        this.userDAO = userDAO;
        this.encryptionService = encryptionService;
    }

    public User registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException{
        if(userDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
            || userDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()){
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

}
