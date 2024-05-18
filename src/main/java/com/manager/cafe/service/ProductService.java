package com.manager.cafe.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.manager.cafe.wrapper.ProductWrapper;

public interface ProductService {
    ResponseEntity<String> add(@RequestBody(required = true) Map<String, String> requestMap);

    ResponseEntity<List<ProductWrapper>> getAll();
}
