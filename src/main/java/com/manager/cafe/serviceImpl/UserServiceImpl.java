package com.manager.cafe.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.manager.cafe.JWT.jwt.AuthTokenFilter;
import com.manager.cafe.JWT.jwt.JwtFilter;
import com.manager.cafe.JWT.jwt.JwtUtil;
import com.manager.cafe.JWT.jwt.JwtUtils;
import com.manager.cafe.JWT.service.UserDetailsServiceImpl;
import com.manager.cafe.POJO.User;
import com.manager.cafe.constents.CafeConstants;
import com.manager.cafe.dao.UserDao;
import com.manager.cafe.service.UserService;
import com.manager.cafe.utils.CafeUtils;
import com.manager.cafe.utils.EmailUtil;
import com.manager.cafe.wrapper.UserWrepper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    EmailUtil emailUtil;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside sigup {}", requestMap);
        try {
            if (validateSigUpMap(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
                    return CafeUtils.getResponseEntity("Successfully Registered", HttpStatus.OK);
                } else {
                    return CafeUtils.getResponseEntity("Email already exits", HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private boolean validateSigUpMap(Map<String, String> reMap) {
        if (reMap.containsKey("name") && reMap.containsKey("contactNumber") && reMap.containsKey("email")
                && reMap.containsKey("password")) {
            return true;
        }
        return false;
    }

    private User getUserFromMap(Map<String, String> reMap) {
        User user = new User();
        user.setName(reMap.get("name"));
        user.setContactNumber(reMap.get("contactNumber"));
        user.setEmail(reMap.get("email"));
        user.setPassword(reMap.get("password"));
        user.setStatus(reMap.get("status"));
        user.setRole(reMap.get("role"));
        return user;

    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");
        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );
            if(auth.isAuthenticated()){
                if(userDetailsServiceImpl.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"tokem\":\""+ jwtUtil.genarateToken(userDetailsServiceImpl.getUserDetail().getEmail(),
                    userDetailsServiceImpl.getUserDetail().getRole())+"\"}",HttpStatus.OK);
                }
            }else{
                return new ResponseEntity<String>("{\"mesage\":\""+"Wait for admn approval"+"\"}",HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
           log.error("{}", e);
        }
        return new ResponseEntity<String>("{\"mesage\":\""+"Bad Credentials"+"\"}",HttpStatus.BAD_REQUEST);

    }

    @Override
    public ResponseEntity<List<UserWrepper>> getAllUser() {
      try {
         if(jwtFilter.isAdmin()){
            return new ResponseEntity<List<UserWrepper>>(userDao.getAllUser(),HttpStatus.OK);
         }else{
            return new ResponseEntity<List<UserWrepper>>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
         }
      } catch (Exception e) {
        e.printStackTrace();
      }

       return new ResponseEntity<List<UserWrepper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
           if(jwtFilter.isAdmin()){
               Optional<User> optional= userDao.findById(Integer.parseInt(requestMap.get("id")));
               log.info(optional.get()+"");
                if(!optional.isEmpty()){
                    userDao.updateUser(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                    //sendMailtoAdmin(requestMap.get("status"),optional.get().getEmail(),userDao.getAllAdmin());
                    return CafeUtils.getResponseEntity("User Status Update Success", HttpStatus.OK);
                }else{
                    return CafeUtils.getResponseEntity("UserId is not exxit", HttpStatus.OK);
                }

           }else{
            return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED,HttpStatus.UNAUTHORIZED);
           }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return new ResponseEntity<>(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void sendMailtoAdmin(String status, String user, List<String> allAdmin) {
       allAdmin.remove(jwtFilter.getCurrentUser());
       if(status !=null && status.equalsIgnoreCase("true")){
            emailUtil.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Approced", "User:-"+user+"\n is approved by \n Admin:-"+jwtFilter.getCurrentUser(), allAdmin);
       }else{
        emailUtil.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Disabled", "User:-"+user+"\n is disables by \n Admin:-"+jwtFilter.getCurrentUser(), allAdmin);
       }
    }

    @Override
    public ResponseEntity<String> checkToken() {
        // TODO Auto-generated method stub
        return CafeUtils.getResponseEntity("true",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            User user=userDao.findByEmailId(jwtFilter.getCurrentUser());
            if(!user.equals(null)){
                log.info(user+"");
                if(user.getPassword().equals(requestMap.get("oldPassword"))){
                    user.setPassword(requestMap.get("newPassword"));
                    userDao.save(user);
                    return CafeUtils.getResponseEntity("Password Update Successfully", HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity("Incorrest Old Password", HttpStatus.BAD_REQUEST); 
            }
         } catch (Exception e) {
           e.printStackTrace();
         }
   
          return new ResponseEntity<String>(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> fogotPassword(Map<String, String> requestMap) {
        try {
            User user=userDao.findByEmailId(requestMap.get("email"));
            if(!Objects.isNull(user)&&!Strings.isNotEmpty(user.getEmail())){
                //emailUtil.forgotMail(user.getEmail(),"Credentials by Cafe Managerment System", user.getPassword());
                return CafeUtils.getResponseEntity("Check your mail for Credentials", HttpStatus.BAD_REQUEST); 
            }
         } catch (Exception e) {
           e.printStackTrace();
         }
   
          return new ResponseEntity<String>(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    
    
}
