package com.ecommerce.backend.service;

import com.ecommerce.backend.model.User;
import com.ecommerce.backend.model.WebOrder;
import com.ecommerce.backend.model.dao.WebOrderDAO;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {


    private WebOrderDAO webOrderDAO;

    public OrderService(WebOrderDAO webOrderDAO) {
        this.webOrderDAO = webOrderDAO;
      }


      public List<WebOrder> getOrders(User user) {
        return webOrderDAO.findByUser(user);
      }
}
