package com.manager.cafe.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manager.cafe.JWT.jwt.JwtFilter;
import com.manager.cafe.POJO.Category;
import com.manager.cafe.POJO.Product;
import com.manager.cafe.constents.CafeConstants;
import com.manager.cafe.dao.ProductDao;
import com.manager.cafe.service.ProductService;
import com.manager.cafe.utils.CafeUtils;
import com.manager.cafe.wrapper.ProductWrapper;
import com.manager.cafe.wrapper.UserWrepper;
@Service
public class ProductServiceImple implements ProductService{

    @Autowired
    ProductDao productDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> add(Map<String, String> requestMap) {
        try {
         if(jwtFilter.isAdmin()){
            
            if(candidateProductMap(requestMap,false)){
                productDao.save(getProductFromMap(requestMap, false));
                return CafeUtils.getResponseEntity("Product Add success", HttpStatus.OK);
            }
            return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
         }else{
            return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
         }
      } catch (Exception e) {
        e.printStackTrace();
      }

       return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean candidateProductMap(Map<String, String> requestMap, boolean validate) {
        if(requestMap.containsKey("name")){
            if(requestMap.containsKey("id")&& validate){
                return true;
            }else if(!validate){
                return true;
            }
        }
        return false;
    }
    
    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd){
        Category category=new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));
        Product product=new Product();
        if(isAdd){
            product.setId(Integer.parseInt(requestMap.get("id")));
        }else{
            product.setStatus("true");
        }
        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("decription"));
        product.setPrice(Integer.parseInt( requestMap.get("prince")));
        return product;
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAll() {
        try {
         if(jwtFilter.isAdmin()){
            return new ResponseEntity<List<ProductWrapper>>(productDao.getAllProduct(),HttpStatus.OK);
         }else{
            return new ResponseEntity<List<ProductWrapper>>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
         }
      } catch (Exception e) {
        e.printStackTrace();
      }

       return new ResponseEntity<List<ProductWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
