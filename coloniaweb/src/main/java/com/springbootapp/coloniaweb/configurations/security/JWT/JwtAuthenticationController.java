package com.springbootapp.coloniaweb.configurations.security.JWT;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.springbootapp.coloniaweb.configurations.security.JpaUserDetailsService;
import com.springbootapp.coloniaweb.models.entity.Usuario;
import com.springbootapp.coloniaweb.models.services.IUsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost/4200")
@RequestMapping("/api")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;

    @Autowired
    private JwtUtil jwtUtilService;

    @Autowired
    private IUsuarioService userService;

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@Valid @RequestBody JwtRequest jwtRequest) throws Exception {
        
        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;
      
        Usuario usuario = userService.findByUsername(jwtRequest.getUsername());

        if(usuario != null){

            if(usuario.isVerified()){

                try{

                    authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
                     
                               
                   }catch (BadCredentialsException e) {
                          throw new Exception("Bad credentials!");
                   }
    
                final UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
    
                Map<String, Object> claims = new HashMap<String, Object>();
    
                claims.put("authorities", userDetails.getAuthorities());
    
                final String jwt = jwtUtilService.token(userDetails, claims);
    
                httpStatus = HttpStatus.OK;
                message = "You're welcome" ;
    
                JwtResponse jwtResponse = new JwtResponse(jwt);
    
                response.put("token", jwtResponse);

            }else{

                httpStatus = HttpStatus.UNAUTHORIZED;
                message = "User not verified" ;

            }

        }else{

            httpStatus = HttpStatus.UNAUTHORIZED;
            message = "Bad credentials" ;

        }
        	
        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);
       

    }

}