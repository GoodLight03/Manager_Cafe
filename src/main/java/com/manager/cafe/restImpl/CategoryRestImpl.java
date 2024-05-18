package com.manager.cafe.restImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.manager.cafe.JWT.jwt.JwtFilter;
import com.manager.cafe.POJO.Category;
import com.manager.cafe.constents.CafeConstants;
import com.manager.cafe.rest.CategoryRest;
import com.manager.cafe.service.CategoryService;
import com.manager.cafe.utils.CafeUtils;

@RestController
public class CategoryRestImpl implements CategoryRest{

    @Autowired
    CategoryService categoryService;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> add(Map<String, String> requestMap) {
        try {
            return categoryService.add(requestMap);
        } catch (Exception e) {
            e.printStackTrace();// TODO: handle exception
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAll(String filtervalue) {
        try {
            return categoryService.getAll(filtervalue);
        } catch (Exception e) {
            e.printStackTrace();// TODO: handle exception
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            return categoryService.update(requestMap);
        } catch (Exception e) {
            e.printStackTrace();// TODO: handle exception
        }
        return new ResponseEntity<>(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> delete(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    
    
}
