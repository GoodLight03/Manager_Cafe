package com.manager.cafe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.manager.cafe.POJO.Category;
import com.manager.cafe.wrapper.UserWrepper;

@Repository
public interface CategoryDao extends JpaRepository<Category,Integer>{
    @Query("select c from Category c where c.id in (select p.category.id from Product p where p.status='true')")
    List<Category> getAllCategory();
}
