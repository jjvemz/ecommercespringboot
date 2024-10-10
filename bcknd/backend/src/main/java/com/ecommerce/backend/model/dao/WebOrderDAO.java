package com.ecommerce.backend.model.dao;

import com.ecommerce.backend.model.User;
import com.ecommerce.backend.model.WebOrder;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface WebOrderDAO  extends ListCrudRepository<WebOrder, Long>{
    
    List<WebOrder> findByUser(User currUser);
}
