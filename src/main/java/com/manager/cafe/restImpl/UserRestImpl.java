package com.manager.cafe.restImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.manager.cafe.constents.CafeConstants;
import com.manager.cafe.rest.UserRest;
import com.manager.cafe.service.UserService;
import com.manager.cafe.utils.CafeUtils;
import com.manager.cafe.wrapper.UserWrepper;

@RestController
public class UserRestImpl implements UserRest {

    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<String> checkToken() {
        try{
            return userService.checkToken();
        }catch(Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            return userService.signUp(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // return new ResponseEntity<String>("{\"mesag\":\"something
        // wrong\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            return userService.login(requestMap);
        } catch (Exception e) {
            e.printStackTrace();// TODO: handle exception
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<List<UserWrepper>> getAllUser() {
        try {
           return userService.getAllUser();
        } catch (Exception e) {
           e.printStackTrace();
        }
        return new ResponseEntity<List<UserWrepper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            return userService.update(requestMap);
        } catch (Exception e) {
           e.printStackTrace();
        }

        return new ResponseEntity<>(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            return userService.changePassword(requestMap);
        } catch (Exception e) {
           e.printStackTrace();
        }

        return new ResponseEntity<>(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> fogotPassword(Map<String, String> requestMap) {
        try {
            return userService.fogotPassword(requestMap);
        } catch (Exception e) {
           e.printStackTrace();
        }

        return new ResponseEntity<>(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    

}
