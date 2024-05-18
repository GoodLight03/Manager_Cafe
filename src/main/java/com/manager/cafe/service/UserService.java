package com.manager.cafe.service;

import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.manager.cafe.wrapper.UserWrepper;

public interface UserService {
    ResponseEntity<String> signUp(Map<String, String> requestMap);
    ResponseEntity<String> login(Map<String, String> requestMap);
    ResponseEntity<List<UserWrepper>> getAllUser();
    ResponseEntity<String> update(Map<String, String> requestMap);
    ResponseEntity<String> checkToken();
    ResponseEntity<String> changePassword(@RequestBody(required = true) Map<String, String> requestMap);
    ResponseEntity<String> fogotPassword(@RequestBody(required = true) Map<String, String> requestMap);
}
