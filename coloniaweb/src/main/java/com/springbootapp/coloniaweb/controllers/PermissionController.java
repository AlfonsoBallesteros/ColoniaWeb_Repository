package com.springbootapp.coloniaweb.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.springbootapp.coloniaweb.models.entity.Permission;
import com.springbootapp.coloniaweb.models.services.IPermissionService;

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
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @GetMapping("/permissions")
    public ResponseEntity<?> findAll() {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        String message = !permissionService.findAll().isEmpty() ? "El listado de permisos posee información"
                : "El listado de permisos se encuentra vacío";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("permissions", permissionService.findAll());

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @GetMapping("/permissions/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;
        Permission permission = permissionService.findById(id);

        httpStatus = permission != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        message = permission != null ? "Ha sido encontrado exitosamente el permiso" : "No se ha encontrado el permiso";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("permission", permission);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @PostMapping("/permissions")
    public ResponseEntity<?> save(@Valid @RequestBody Permission permission) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.CREATED;
        Permission newpermission = permissionService.save(permission);
        String message = "Ha sido creado exitosamente el permiso";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("permission", newpermission);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @PutMapping("/permissions/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Permission permission, @PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        Permission updatePermission = permissionService.findById(id);
        HttpStatus httpStatus;
        String message;

        if (updatePermission != null) {

            updatePermission.setName(permission.getName());
            updatePermission.setDescription(permission.getDescription());
            updatePermission.setUpdateAt(new Date());

            permissionService.save(updatePermission);

            httpStatus = HttpStatus.CREATED;
            message = "Ha sido actualizado con éxito el permiso";

            response.put("permission", updatePermission);

        } else {

            httpStatus = HttpStatus.NOT_FOUND;
            message = "No se ha encontrado el permiso";

        }

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {

        HttpStatus httpStatus = HttpStatus.OK;
        String message = "Ha sido eliminado con éxito el permiso";
        Map<String, Object> response = new HashMap<>();

        permissionService.deleteById(id);

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @GetMapping("/permissions/pagination")
    public ResponseEntity<?> findAll(@PageableDefault(page = 0, size = 5) Pageable pageable) {

        Map<String, Object> response = new HashMap<>();
        Page<Permission> paginator = permissionService.findAll(pageable);
        HttpStatus httpStatus = HttpStatus.OK;
        String message = !paginator.isEmpty() ? "El paginador contiene el listado de permisos"
                : "El paginador contiene un listado de permisos vacío ";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("paginator", paginator);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

}