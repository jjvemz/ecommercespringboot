package com.ecommerce.backend.model.dao;

import com.ecommerce.backend.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

 
public interface UserDAO  extends CrudRepository<User, Long>{

    static Optional<User> findByUsernameIgnoreCase(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUsernameIgnoreCase'");
    }

    Optional<User> findByEmailIgnoreCase(String email);

}
