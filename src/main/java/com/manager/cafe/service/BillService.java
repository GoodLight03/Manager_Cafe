package com.manager.cafe.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.manager.cafe.POJO.Bill;

public interface BillService {

    ResponseEntity<String> generateReport(Map<String, Object> requestMap);

    ResponseEntity<List<Bill>> getBills();

    ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap);
    
}
