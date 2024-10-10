package com.ecommerce.backend.model.dao;

import com.ecommerce.backend.model.Product;
import org.springframework.data.repository.ListCrudRepository;


public interface ProductDAO extends ListCrudRepository<Product, Long>{
    
}
