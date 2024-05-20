package com.manager.cafe.restImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.manager.cafe.JWT.jwt.JwtFilter;
import com.manager.cafe.POJO.Category;
import com.manager.cafe.constents.CafeConstants;
import com.manager.cafe.dto.MesageDto;
import com.manager.cafe.dto.StatisticDto;
import com.manager.cafe.rest.CategoryRest;
import com.manager.cafe.service.CategoryService;
import com.manager.cafe.utils.CafeUtils;

@RestController
public class CategoryRestImpl implements CategoryRest{

    @Autowired
    CategoryService categoryService;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate; 

    @Override
    public ResponseEntity<String> add(Map<String, String> requestMap) {
        try {
            StatisticDto statisticDto=new StatisticDto("Catogory "+ requestMap.get("name")+ "is created",new Date());
            
            MesageDto mesageDto=new MesageDto();
            mesageDto.setTo(requestMap.get("name"));
            mesageDto.setToName(requestMap.get("name"));
            mesageDto.setSubject("Welcome to Cang");
            mesageDto.setContent("OK mangnager Cafe");
            //for(int i=0;i<100;i++)
                kafkaTemplate.send("notification",mesageDto);
                // ListenableFuture<SendResult<String,Object>> future=(ListenableFuture<SendResult<String, Object>>) kafkaTemplate.send("notification",mesageDto);
                // future.addCallback(new ListenableFutureCallback<SendResult<String,Object>>() {
                //     @Override
                //     public void onSuccess(SendResult<String, Object> result) {
                //          // Xử lý khi gửi thành công
                //          System.out.println("Notification sent successfully");
                //          System.out.println(result.getRecordMetadata().partition());
                //     }

                //     @Override
                //     public void onFailure(Throwable ex) {
                //          // Xử lý khi gửi thành công
                //          System.out.println("Notification sent successfully");
                        
                //     }
                // });

            kafkaTemplate.send("static",statisticDto);

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
