package com.manager.cafe.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manager.cafe.JWT.jwt.JwtFilter;
import com.manager.cafe.POJO.Category;
import com.manager.cafe.constents.CafeConstants;
import com.manager.cafe.dao.CategoryDao;
import com.manager.cafe.service.CategoryService;
import com.manager.cafe.utils.CafeUtils;
import com.manager.cafe.wrapper.UserWrepper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> add(Map<String, String> requestMap) {
       try {
         if(jwtFilter.isAdmin()){
            log.info(candidateCategoryMap(requestMap,false)+"");
            if(candidateCategoryMap(requestMap,false)){
                categoryDao.save(getCategoryFromMap(requestMap, false));
                return CafeUtils.getResponseEntity("Category Add success", HttpStatus.OK);
            }
         }else{
            return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
         }
      } catch (Exception e) {
        e.printStackTrace();
      }

       return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean candidateCategoryMap(Map<String, String> requestMap, boolean validate) {
        if(requestMap.containsKey("name")){
            if(requestMap.containsKey("id")&& validate){
                return true;
            }else if(!validate){
                return true;
            }
        }
        return false;
    }
    
    private Category getCategoryFromMap(Map<String, String> requestMap, boolean isAdd){
        Category category=new Category();
        if(isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        return category;
    }

    @Override
    public ResponseEntity<List<Category>> getAll(String filtervalue) {
        log.info(filtervalue+"Iff");
        try {
            if(!Strings.isEmpty(filtervalue) && filtervalue.equalsIgnoreCase("true")){
               
               return new ResponseEntity<List<Category>>(categoryDao.getAllCategory(),HttpStatus.OK);
            }else{
               return new ResponseEntity<List<Category>>(categoryDao.findAll(),HttpStatus.OK);
            }
         } catch (Exception e) {
           e.printStackTrace();
         }
   
          return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
               
               if(candidateCategoryMap(requestMap,true)){
                Optional optional=categoryDao.findById(Integer.parseInt(requestMap.get("id")));
                if(!optional.isEmpty()){
                    categoryDao.save(getCategoryFromMap(requestMap, true));
                   return CafeUtils.getResponseEntity("Category Update success", HttpStatus.OK);
                }else{
                    return CafeUtils.getResponseEntity("Category Not Exit", HttpStatus.OK);
                }
                   
               }
            }else{
               return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
            }
         } catch (Exception e) {
           e.printStackTrace();
         }
   
          return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
