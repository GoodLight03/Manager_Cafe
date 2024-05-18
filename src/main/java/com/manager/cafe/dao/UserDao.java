package com.manager.cafe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.manager.cafe.POJO.User;
import com.manager.cafe.wrapper.*;

import jakarta.transaction.Transactional;

import java.util.List;
@Repository
public interface UserDao extends JpaRepository<User,Integer>{
    @Query("select u from User u where u.email=:email")
    User findByEmailId(@Param("email") String eamil );

    @Query("select new com.manager.cafe.wrapper.UserWrepper(u.id,u.name,u.contactNumber,u.email,u.password,u.status) from User u")
    List<UserWrepper> getAllUser();

    @Transactional
    @Modifying
    @Query("update User u set u.status =:status where u.id =:id")
    Integer updateUser(@Param("status") String status,@Param("id") Integer id);

    @Query("select u.email from User u where u.role='admin'")
    List<String> getAllAdmin();

    //User findByEmail(String email);
}
