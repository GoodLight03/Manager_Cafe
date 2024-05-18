package com.manager.cafe.restImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.manager.cafe.POJO.Bill;
import com.manager.cafe.constents.CafeConstants;
import com.manager.cafe.rest.BillRest;
import com.manager.cafe.service.BillService;
import com.manager.cafe.utils.CafeUtils;
@RestController
public class BillRestImpl implements BillRest{

    @Autowired
    BillService billService;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
         try {
            return billService.generateReport(requestMap);
        } catch (Exception e) {
            e.printStackTrace();// TODO: handle exception
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {
        try {
            return billService.getBills();
        } catch (Exception e) {
            e.printStackTrace();// TODO: handle exception
        }
        return null;
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        try {
            return billService.getPdf(requestMap);
        } catch (Exception e) {
            e.printStackTrace();// TODO: handle exception
        }
        return null;
    }

    @Override
    public ResponseEntity<String> delete(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
