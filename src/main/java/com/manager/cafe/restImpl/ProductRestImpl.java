package com.manager.cafe.restImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.manager.cafe.constents.CafeConstants;
import com.manager.cafe.rest.ProductRest;
import com.manager.cafe.service.ProductService;
import com.manager.cafe.utils.CafeUtils;
import com.manager.cafe.wrapper.ProductWrapper;
@RestController
public class ProductRestImpl implements ProductRest{

    @Autowired
    ProductService productService;

    @Override
    public ResponseEntity<String> add(Map<String, String> requestMap) {
       try {
            return productService.add(requestMap);
        } catch (Exception e) {
            e.printStackTrace();// TODO: handle exception
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAll() {
        try {
            return productService.getAll();
        } catch (Exception e) {
            e.printStackTrace();// TODO: handle exception
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateStatus'");
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByCategory'");
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }
    
}
