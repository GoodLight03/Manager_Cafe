package com.manager.cafe.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.manager.cafe.POJO.Category;

public interface CategoryService {
    ResponseEntity<String> add(@RequestBody(required = true) Map<String, String> requestMap);

    ResponseEntity<List<Category>> getAll(String filtervalue);

    ResponseEntity<String> update(Map<String, String> requestMap);
}
