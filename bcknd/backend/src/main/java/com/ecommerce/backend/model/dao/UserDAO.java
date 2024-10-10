package com.ecommerce.backend.model.dao;

import com.ecommerce.backend.model.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

 
public interface UserDAO  extends ListCrudRepository<User, Long>{

    Optional<User> findByUsernameIgnoreCase(String username);

    Optional<User> findByEmailIgnoreCase(String email);

}
