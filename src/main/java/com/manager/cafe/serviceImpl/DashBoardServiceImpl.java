package com.manager.cafe.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manager.cafe.dao.BillDao;
import com.manager.cafe.dao.CategoryDao;
import com.manager.cafe.dao.ProductDao;
import com.manager.cafe.service.DashBoardService;
@Service
public class DashBoardServiceImpl implements DashBoardService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ProductDao ProductDao;

    @Autowired
    BillDao billDao;

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
       Map<String, Object> map=new HashMap<>();
       map.put("category", categoryDao.count());
       map.put("product",ProductDao.count());
       map.put("bill", billDao.count());
       return new ResponseEntity<>(map,HttpStatus.OK);
    }
    
}
