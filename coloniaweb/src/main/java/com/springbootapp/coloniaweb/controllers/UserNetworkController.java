package com.springbootapp.coloniaweb.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.springbootapp.coloniaweb.models.entity.Usuario;
import com.springbootapp.coloniaweb.models.entity.UserNetwork;
import com.springbootapp.coloniaweb.models.services.IUserNetworkService;
import com.springbootapp.coloniaweb.models.services.IUsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class UserNetworkController {

    @Autowired
    private IUserNetworkService userNetworkService;

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/usernetworks")
    public ResponseEntity<?> findAll() {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        String message = !userNetworkService.findAll().isEmpty()
                ? "El listado de redes sociales de usuario posee información"
                : "El listado de redes sociales de usuario se encuentra vacío";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("userNetworks", userNetworkService.findAll());

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    // Metodo de busqueda para llaves compuestas
    @GetMapping("/usernetworks/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;
        UserNetwork userNetworkShow = userNetworkService.findById(id);

        httpStatus = userNetworkShow != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        message = userNetworkShow != null
                ? "Ha sido encontrada exitosamente la red social"
                : "No se ha encontrado la red social";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("userNetwork", userNetworkShow);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @PostMapping("/usernetworks")
    public ResponseEntity<?> save(@Valid @RequestBody UserNetwork userNetwork) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;
        Usuario user = usuarioService.findById(userNetwork.getUsuario().getId());


        if (user != null) {

            boolean validation = true;

            for (UserNetwork user_network : user.getUserNetworks()) {

                if (user_network.getNetwork().getId() == userNetwork.getNetwork().getId()) {

                    validation = false;

                }

            }

            if (validation) {

                httpStatus = HttpStatus.CREATED;
                message = "Ha sido creada con éxito la red social";

                response.put("userNetwork", userNetworkService.save(userNetwork));

            } else {

                httpStatus = HttpStatus.CREATED;
                message = "Ya existe una red social con los datos recibidos";

            }

        } else {

            httpStatus = HttpStatus.NOT_FOUND;
            message = "No se ha encontrado el usuario";

        }

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @PutMapping("/usernetworks/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UserNetwork userNetwork, @PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;
        UserNetwork updateUserNetwork = userNetworkService.findById(id);

        if (updateUserNetwork != null) {

            updateUserNetwork.setUrl(userNetwork.getUrl());
            updateUserNetwork.setUpdateAt(new Date());

            response.put("userNetwork", userNetworkService.save(updateUserNetwork));

            httpStatus = HttpStatus.CREATED;
            message = "La red social ha sido actualizada con éxito";

        } else {

            httpStatus = HttpStatus.NOT_FOUND;
            message = "No existe la red social";

        }

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @DeleteMapping("/usernetworks/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        String message = "Se ha eliminado con éxito la red social";

        userNetworkService.deleteById(id);

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @GetMapping("/usernetworks/pagination")
    public ResponseEntity<?> findAll(@PageableDefault(page = 0, size = 5) Pageable pageable) {

        Map<String, Object> response = new HashMap<>();
        Page<UserNetwork> paginator = userNetworkService.findAll(pageable);
        HttpStatus httpStatus = HttpStatus.OK;
        String message = !paginator.isEmpty() ? "El paginador contiene el listado de redes sociales asociadas a los usuarios"
                : "El paginador contiene un listado de redes sociales asociadas a los usuarios vacío ";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("paginator", paginator);
        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

}