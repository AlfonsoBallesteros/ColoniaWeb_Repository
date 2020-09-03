package com.springbootapp.coloniaweb.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.springbootapp.coloniaweb.models.entity.Country;
import com.springbootapp.coloniaweb.models.services.ICountryService;

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
public class CountryController {

    @Autowired
    private ICountryService countryService;

    @GetMapping("/countries")
    public ResponseEntity<?> findAll() {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        String message = !countryService.findAll().isEmpty() ? "El listado de paises posee información"
                : "El listado de paises se encuentra vacío";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("countries", countryService.findAll());

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @GetMapping("/countries/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;
        Country country = countryService.findById(id);

        httpStatus = country != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        message = country != null ? "Ha sido encontrado exitosamente el país" : "No se ha encontrado un país";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("country", country);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @PostMapping("/countries")
    public ResponseEntity<?> save(@Valid @RequestBody Country country) {

        Map<String, Object> response = new HashMap<>();
        Country newCountry = countryService.save(country);
        HttpStatus httpStatus = HttpStatus.CREATED;
        String message = "Ha sido creado exitosamente el país";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("country", newCountry);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @PutMapping("/countries/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Country country, @PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        Country updateCountry = countryService.findById(id);
        HttpStatus httpStatus;
        String message;

        if (updateCountry != null) {

            updateCountry.setName(country.getName());
            updateCountry.setPrefix(country.getPrefix());
            updateCountry.setUpdateAt(new Date());

            countryService.save(updateCountry);

            httpStatus = HttpStatus.CREATED;
            message = "Ha sido actualizado con éxito el país";

            response.put("country", updateCountry);

        } else {

            httpStatus = HttpStatus.NOT_FOUND;
            message = "No se ha encontrado un país";

        }

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @DeleteMapping("/countries/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        String message = "Ha sido eliminado con éxito el país";

        countryService.deleteById(id);

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @GetMapping("/countries/pagination")
    public ResponseEntity<?> findAll(@PageableDefault(page = 0, size = 5) Pageable pageable) {

        Map<String, Object> response = new HashMap<>();
        Page<Country> paginator = countryService.findAll(pageable);
        HttpStatus httpStatus = HttpStatus.OK;
        String message = !paginator.isEmpty() ? "El paginador contiene el listado de países"
                : "El paginador contiene un listado de países vacío ";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("paginator", paginator);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

}