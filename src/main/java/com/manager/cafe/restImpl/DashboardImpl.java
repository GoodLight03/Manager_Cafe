package com.manager.cafe.restImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.manager.cafe.rest.Dashboard;
import com.manager.cafe.service.DashBoardService;
@RestController
public class DashboardImpl implements Dashboard{

    @Autowired
    DashBoardService dashBoardService;
    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
       return dashBoardService.getCount();
    }
    
}
