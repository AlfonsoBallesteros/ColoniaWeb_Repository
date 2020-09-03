package com.springbootapp.coloniaweb.controllers;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;


import com.springbootapp.coloniaweb.models.entity.Usuario;
import com.springbootapp.coloniaweb.models.services.IUsuarioService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("http://investigacionlacolonia.com")
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;  

    @GetMapping("/usuarios")
    public ResponseEntity<?> findAll() {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        String message = !usuarioService.findAll().isEmpty() ? "El listado de usuarios posee información"
                : "El listado de usuarios se encuentra vacío";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("users", usuarioService.findAll());

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;
        Usuario user = usuarioService.findById(id);

        httpStatus = user != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        message = user != null ? "Ha sido encontrado exitosamente el usuario" : "No se ha encontrado el usuario";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("user", user);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @GetMapping("/usuarios/username/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {

        Usuario user = usuarioService.findByUsername(username);
        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = user != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        String message = user != null ? "Ha sido encontrado exitosamente el usuario" : "No se ha encontrado el usuario";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("user", user);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @PostMapping("/usuarios")
    public ResponseEntity<?> save(@Valid @RequestBody Usuario user) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;
        Usuario newUsuario;
        Calendar birthdate = Calendar.getInstance();
        birthdate.setTime(user.getBirthdate());
        Calendar timeNow = Calendar.getInstance();
        timeNow.setTime(new Date());
        if (timeNow.get(Calendar.YEAR) - birthdate.get(Calendar.YEAR) >= 13
                && timeNow.get(Calendar.YEAR) - birthdate.get(Calendar.YEAR) < 90) {

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            newUsuario = usuarioService.save(user);
            
            httpStatus = HttpStatus.CREATED;
            message = "Ha sido creado exitosamente el usuario";
            
            response.put("user", newUsuario);

        } else {

            httpStatus = HttpStatus.BAD_REQUEST;
            message = "El usuario debe tener más de 13 años y menos de 90 años";
        }

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        
        return new ResponseEntity<Map<String, Object>>(response, httpStatus);
        

    }

    
    

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Usuario user, @PathVariable Long id) {

        Usuario updateUsuario = usuarioService.findById(id);
        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;

        if (updateUsuario != null) {

            updateUsuario.setUsername(user.getUsername());
            updateUsuario.setPassword(passwordEncoder.encode(user.getPassword()));
            updateUsuario.setEmail(user.getEmail());
            updateUsuario.setName(user.getName());
            updateUsuario.setLastname(user.getLastname());
            // updateUsuario.setDocid(user.getDocid());
            // updateUsuario.setBirthdate(user.getBirthdate());
            updateUsuario.setCellphone(user.getCellphone());
            updateUsuario.setOcupation(user.getOcupation());
            updateUsuario.setAvatar(user.getAvatar());
            // updateUsuario.setRoles(user.getRoles());
            updateUsuario.setCountry(user.getCountry());
            // updateUsuario.setDocType(user.getDocType());
            updateUsuario.setGender(user.getGender());
            // updateUsuario.setEnable(user.getEnable());
            //updateUsuario.setVerified(user.isVerified());
            updateUsuario.setUpdateAt(new Date());

            usuarioService.save(updateUsuario);

            httpStatus = HttpStatus.CREATED;
            message = "Ha sido actualizado con éxito el usuario";

            response.put("user", updateUsuario);

        } else {

            httpStatus = HttpStatus.NOT_FOUND;
            message = "No se ha encontrado el usuario";

        }

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @DeleteMapping( value= "/usuarios/{id}/{validation}")
    public ResponseEntity<?> deleteById(@PathVariable Long id, @PathVariable boolean validation) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;
        Usuario user = usuarioService.findById(id);

        System.out.println("VALIDATION : " + validation);

        if(user != null){

            if (validation) {

                usuarioService.deleteById(id);
    
                httpStatus = HttpStatus.OK;
                message = "Ha sido eliminado con éxito el usuario";
    
            } else {
    
                user.setEnable(false);
                usuarioService.save(user);
    
                httpStatus = HttpStatus.OK;
                message = "Ha sido desactivado con éxito el usuario";
    
            }

        }else{

            httpStatus = HttpStatus.NOT_FOUND;
            message = "Usuario no encontrado";

        }

        response.put("message", message);
        response.put("status", httpStatus);
        response.put("code", httpStatus.value());

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @GetMapping("/usuarios/pagination")
    public ResponseEntity<?> findAll(@PageableDefault(page = 0, size = 5) Pageable pageable) {

        Map<String, Object> response = new HashMap<>();
        Page<Usuario> paginator = usuarioService.findAll(pageable);
        HttpStatus httpStatus = HttpStatus.OK;
        String message = !paginator.isEmpty() ? "El paginador contiene el listado de usuarios"
                : "El paginador contiene un listado de usuarios vacío ";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("paginator", paginator);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

}