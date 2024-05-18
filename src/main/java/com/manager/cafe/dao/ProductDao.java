package com.manager.cafe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.manager.cafe.POJO.Product;
import com.manager.cafe.wrapper.ProductWrapper;
@Repository
public interface ProductDao extends JpaRepository<Product,Integer>{
    @Query("select new com.manager.cafe.wrapper.ProductWrapper(u.id,u.name,u.category.id,u.description,u.price,u.status,u.category.name) from Product u")
    List<ProductWrapper> getAllProduct();
    
}
