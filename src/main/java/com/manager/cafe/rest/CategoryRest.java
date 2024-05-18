package com.manager.cafe.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.manager.cafe.POJO.Category;
import com.manager.cafe.wrapper.UserWrepper;

@RequestMapping(path = "/category")
public interface CategoryRest {
    @PostMapping(path = "/add")
    public ResponseEntity<String> add(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/get")
    public ResponseEntity<List<Category>> getAll(@RequestBody(required = false) String filtervalue);

    @PostMapping(path = "/update")
    public ResponseEntity<String> update(@RequestBody(required = true) Map<String, String> requestMap);

    @PostMapping(path = "/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id);
}
