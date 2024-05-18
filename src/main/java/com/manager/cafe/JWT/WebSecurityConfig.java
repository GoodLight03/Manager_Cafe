package com.manager.cafe.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//import com.manager.cafe.JWT.jwt.AuthEntryPointJwt;
import com.manager.cafe.JWT.jwt.AuthTokenFilter;
import com.manager.cafe.JWT.jwt.JwtFilter;
import com.manager.cafe.JWT.service.UserDetailsServiceImpl;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
  
    // @Autowired
    // private AuthEntryPointJwt unauthorizedHandler;
  
    @Bean
    public JwtFilter authenticationJwtTokenFilter() {
      // return new AuthTokenFilter();
      return new JwtFilter();
    }
  
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
         
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
     
        return authProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
      return authConfig.getAuthenticationManager();
    }
  
    @Bean
    public PasswordEncoder passwordEncoder() {
      // return new BCryptPasswordEncoder();
      return NoOpPasswordEncoder.getInstance();
    }    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.cors().and().csrf().disable()
          //.exceptionHandling()
          //.authenticationEntryPoint(unauthorizedHandler).and()
          //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
          .authorizeRequests().requestMatchers("/user/login/**","/user/fogotPassword").permitAll()
          .anyRequest().authenticated()
          .and().exceptionHandling()
          .and()
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
      
      http.authenticationProvider(authenticationProvider());
  
      http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
      
      return http.build();
    }


}
