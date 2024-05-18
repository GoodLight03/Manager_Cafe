package com.manager.cafe.JWT.service;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.manager.cafe.dao.UserDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

   @Autowired
    UserDao userDao;

    private com.manager.cafe.POJO.User userDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       log.info("Inside loadByUserName {}",username);
        userDetail=userDao.findByEmailId(username);
        if(!Objects.isNull(userDetail)){
            return new User(userDetail.getEmail(),userDetail.getPassword(),new ArrayList<>());

        }else{
            throw new UsernameNotFoundException("User not found");
        }
    }

    public com.manager.cafe.POJO.User getUserDetail(){
        // com.manager.cafe.POJO.User user=userDetail;
        // user.setPassword(null);
        return userDetail;
    }
    
    
}