package com.manager.cafe.JWT.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.manager.cafe.JWT.service.UserDetailsServiceImpl;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl service;

    Claims claims=null;

    private String userName=null;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
      if(request.getServletPath().matches("/user/login")){
        filterChain.doFilter(request,response);

      }else{
        String authozationHeader=request.getHeader("Authorization");
        String token=null;

        if(authozationHeader!=null && authozationHeader.startsWith("Bearer ")){
            token=authozationHeader.substring(7);
            userName=jwtUtil.extractUsername(token);
            claims=jwtUtil.extractAllClaims(token);
        }

        if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetail=service.loadUserByUsername(userName);
            if(jwtUtil.validateToken(token,userDetail)){
                UsernamePasswordAuthenticationToken us=
                    new UsernamePasswordAuthenticationToken(userDetail,null,userDetail.getAuthorities());
                us.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
           
                SecurityContextHolder.getContext().setAuthentication(us);
            }
        }
        filterChain.doFilter(request,response);
      }
      
    }

    public boolean isAdmin(){
        return "admin".equalsIgnoreCase((String) claims.get("role"));
    }

    public boolean isUser(){
        return "user".equalsIgnoreCase((String) claims.get("role"));
    }

    public String getCurrentUser(){
        return userName;
    }
    
}
