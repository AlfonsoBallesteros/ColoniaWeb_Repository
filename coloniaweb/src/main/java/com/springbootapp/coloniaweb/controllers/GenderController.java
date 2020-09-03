package com.springbootapp.coloniaweb.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.springbootapp.coloniaweb.models.entity.Gender;
import com.springbootapp.coloniaweb.models.services.IGenderService;

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
public class GenderController {

    @Autowired
    private IGenderService genderService;

    @GetMapping("/genders")
    public ResponseEntity<?> findAll() {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        String message = !genderService.findAll().isEmpty() ? "El listado de géneros posee información"
                : "El listado de géneros se encuentra vacío";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("genders", genderService.findAll());

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @GetMapping("/genders/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;
        Gender gender = genderService.findById(id);

        httpStatus = gender != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        message = gender != null ? "Ha sido encontrado exitosamente el género" : "No se ha encontrado el género";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("gender", gender);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @PostMapping("/genders")
    public ResponseEntity<?> save(@Valid @RequestBody Gender gender) {

        Map<String, Object> response = new HashMap<>();
        Gender newGender = genderService.save(gender);
        HttpStatus httpStatus = HttpStatus.CREATED;
        String message = "Ha sido creado exitosamente el género";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("gender", newGender);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @PutMapping("/genders/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Gender gender, @PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        Gender updateGender = genderService.findById(id);
        HttpStatus httpStatus;
        String message;

        if (updateGender != null) {

            updateGender.setName(gender.getName());
            updateGender.setUpdateAt(new Date());

            genderService.save(updateGender);

            httpStatus = HttpStatus.CREATED;
            message = "Ha sido actualizado con éxito el género";

            response.put("gender", updateGender);

        } else {

            httpStatus = HttpStatus.NOT_FOUND;
            message = "No se ha encontrado el género";

        }

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @DeleteMapping("/genders/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        String message = "Ha sido eliminado con éxito el género";

        genderService.deleteById(id);

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @GetMapping("/genders/pagination")
    public ResponseEntity<?> findAll(@PageableDefault(page = 0, size = 5) Pageable pageable) {

        Map<String, Object> response = new HashMap<>();
        Page<Gender> paginator = genderService.findAll(pageable);
        HttpStatus httpStatus = HttpStatus.OK;
        String message = !paginator.isEmpty() ? "El paginador contiene el listado de géneros"
                : "El paginador contiene un listado de géneros vacío ";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("paginator", paginator);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

}