package com.springbootapp.coloniaweb.controllers;


import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springbootapp.coloniaweb.models.entity.ConfirmationToken;
import com.springbootapp.coloniaweb.models.entity.Usuario;
import com.springbootapp.coloniaweb.models.implementations.EmailSender;
import com.springbootapp.coloniaweb.models.services.IConfirmationTokenService;
import com.springbootapp.coloniaweb.models.services.IUsuarioService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")

public class ConfirmationTokenController {

    @Autowired
    private IUsuarioService usuarioService;


    @Autowired
    private IConfirmationTokenService confirmationTokenService;

    @Autowired
    private EmailSender emailSender;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/confirm-account-email")
    public ResponseEntity<?> confirmUserAccountEmail(@RequestBody Usuario user) {
        
        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;

        //Usuario existsUser = usuarioService.findById(user.getId());

        Usuario existsUser = usuarioService.findByEmailIgnoreCase(user.getEmail());

        if (existsUser != null) {

            ConfirmationToken confirmationToken = new ConfirmationToken(existsUser);
            confirmationTokenService.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(existsUser.getEmail());
            mailMessage.setSubject("Confirm your account");
            mailMessage.setFrom("coloniawebinvestigacion@gmail.com");
            mailMessage.setText("To complete the confirm account process, please click here: "  
      				+ "http://investigacionlacolonia.com/confirmed-mail?token=" + confirmationToken.getConfirmationToken());

            emailSender.sendEmail(mailMessage);
            httpStatus = HttpStatus.OK;
            message = "¡We have sent an email to confirm your email, please check your inbox!";

        } else {
            httpStatus = HttpStatus.NOT_FOUND;
            message = "error: This email address doesn't exist!";
        }
        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;
        ConfirmationToken token = confirmationTokenService.findByConfirmationToken(confirmationToken);
        String result = confirmationTokenService.validatePasswordResetToken(confirmationToken);
       
        if (result == null ) {

            Usuario user = usuarioService.findByEmailIgnoreCase(token.getUsuario().getEmail());
            
            user.setVerified(true);
            token.setEnabled(false);
            confirmationTokenService.save(token);
            usuarioService.save(user);
            httpStatus = HttpStatus.OK;
            message = "¡Successful validation!";

        } else {

            httpStatus = HttpStatus.NOT_FOUND;
            message = "error: token doesn't exist or malformed, or time expired";
        }

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }
    
    //Se manda el email con un token para restablecer la contraseña
    @PostMapping("/reset-password-email")
    public ResponseEntity<?> resetPasswordEmail(@RequestBody Usuario user) {
        
        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;

        //Usuario existsUser = usuarioService.findById(user.getId());

        Usuario existsUser = usuarioService.findByEmailIgnoreCase(user.getEmail());

        if (existsUser != null) {

            ConfirmationToken confirmationToken = new ConfirmationToken(existsUser);
            confirmationTokenService.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(existsUser.getEmail());
            mailMessage.setSubject("Reset your password!");
            mailMessage.setFrom("coloniawebinvestigacion@gmail.com");
            mailMessage.setText("To reset your password, please click here: "
                    + "http://investigacionlacolonia.com/change-password?token=" + confirmationToken.getConfirmationToken());

            emailSender.sendEmail(mailMessage);
            httpStatus = HttpStatus.OK;
            message = "¡We have sent an email to reset your password, please check your inbox!";

        } else {
            httpStatus = HttpStatus.NOT_FOUND;
            message = "error: This email address doesn't exist!";
        }
        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);
    }
    //Confirma el token, para mostrar formulario y establecer la nueva contraseña.
    @GetMapping("/confirm-token-reset")
    public ResponseEntity<?> confirmResset(@RequestParam("token")String confirmationToken) {
    	Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;
        ConfirmationToken token = confirmationTokenService.findByConfirmationToken(confirmationToken);
        String result = confirmationTokenService.validatePasswordResetToken(confirmationToken);
        
       
        if (result == null) {
           
           httpStatus = HttpStatus.OK;
           message = "Confirmed reset password token!";
           token.setEnabled(false);
           Usuario user = usuarioService.findById(token.getUsuario().getId());
           response.put("user", user);
           confirmationTokenService.save(token);
            
        } else {
        	httpStatus = HttpStatus.NOT_FOUND;
            message = "error: token doesn't exist or malformed, or time expired";
        }
        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);
    }
      
    //Se traen los datos y se guarda en la base de datos, el token pasa a status "true" que quiere decir
    //que ya fue usado.
    
    @PostMapping("/reset-password-confirm")
    public ResponseEntity<?> confirmReset(@Valid @RequestBody Usuario user) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;
        
        if (user.getEmail() != null) {
           
            Usuario tokenUser = usuarioService.findByEmailIgnoreCase(user.getEmail());
           
            tokenUser.setPassword(passwordEncoder.encode(user.getPassword()));
            
            //token.setEnableed(true);
            //confirmationTokenService.save(token);
            usuarioService.save(tokenUser);
            httpStatus = HttpStatus.OK;
            message = "correct password change!";
        } else {
        	 httpStatus = HttpStatus.NOT_FOUND;
             message = "error: token doesn't exist or malformed, or time expired";
        }

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }
}
