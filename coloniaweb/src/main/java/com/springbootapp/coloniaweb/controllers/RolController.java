package com.springbootapp.coloniaweb.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.springbootapp.coloniaweb.models.entity.Rol;
import com.springbootapp.coloniaweb.models.services.IRolService;

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
public class RolController {

    @Autowired
    private IRolService rolService;

    @GetMapping("/roles")
    public ResponseEntity<?> findAll() {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        String message = !rolService.findAll().isEmpty() ? "El listado de roles posee información"
                : "El listado de roles se encuentra vacío";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("roles", rolService.findAll());

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;
        Rol rol = rolService.findById(id);

        httpStatus = rol != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        message = rol != null ? "Ha sido encontrado exitosamente el rol" : "No se ha encontrado el rol";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("rol", rol);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @PostMapping("/roles")
    public ResponseEntity<?> save(@Valid @RequestBody Rol rol) {

        Map<String, Object> response = new HashMap<>();
        Rol newRol = rolService.save(rol);
        HttpStatus httpStatus = HttpStatus.CREATED;
        String message = "Ha sido creado exitosamente el rol";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("rol", newRol);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Rol rol, @PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        Rol updateRol = rolService.findById(id);
        HttpStatus httpStatus;
        String message;

        if (updateRol != null) {

            updateRol.setName(rol.getName());
            updateRol.setDescription(rol.getDescription());
            updateRol.setPermissions(rol.getPermissions());
            updateRol.setUpdateAt(new Date());

            rolService.save(updateRol);

            httpStatus = HttpStatus.CREATED;
            message = "Ha sido actualizado con éxito el rol";

            response.put("rol", updateRol);

        } else {

            httpStatus = HttpStatus.NOT_FOUND;
            message = "No se ha encontrado el rol";

        }

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        String message = "Ha sido eliminado con éxito el rol";

        rolService.deleteById(id);

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @GetMapping("/roles/pagination")
    public ResponseEntity<?> findAll(@PageableDefault(page = 0, size = 5) Pageable pageable) {

        Map<String, Object> response = new HashMap<>();
        Page<Rol> paginator = rolService.findAll(pageable);
        HttpStatus httpStatus = HttpStatus.OK;
        String message = !paginator.isEmpty() ? "El paginador contiene el listado de roles"
                : "El paginador contiene un listado de roles vacío ";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("paginator", paginator);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

}