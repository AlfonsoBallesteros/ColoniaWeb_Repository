package com.springbootapp.coloniaweb.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.springbootapp.coloniaweb.models.entity.DocType;
import com.springbootapp.coloniaweb.models.services.IDocTypeService;

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
public class DocTypeController {

    @Autowired
    private IDocTypeService docTypeService;

    @GetMapping("/doctypes")
    public ResponseEntity<?> findAll() {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        String message = !docTypeService.findAll().isEmpty() ? "El listado de tipos de documento posee información"
                : "El listado de tipos de documento se encuentra vacío";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("doctypes", docTypeService.findAll());

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @GetMapping("/doctypes/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;
        DocType docType = docTypeService.findById(id);

        httpStatus = docType != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        message = docType != null ? "Ha sido encontrado exitosamente el tipo de documento"
                : "No se ha encontrado el tipo de documento";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("docType", docType);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @PostMapping("/doctypes")
    public ResponseEntity<?> save(@Valid @RequestBody DocType docType) {

        Map<String, Object> response = new HashMap<>();
        DocType newDocType = docTypeService.save(docType);
        HttpStatus httpStatus = HttpStatus.CREATED;
        String message = "Ha sido creado exitosamente el tipo de documento";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("docType", newDocType);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @PutMapping("/doctypes/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody DocType docType, @PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        DocType updateDocType = docTypeService.findById(id);
        HttpStatus httpStatus;
        String message;

        if (updateDocType != null) {

            updateDocType.setName(docType.getName());
            updateDocType.setUpdateAt(new Date());

            docTypeService.save(updateDocType);

            httpStatus = HttpStatus.CREATED;
            message = "Ha sido actualizado con éxito el tipo de documento";

            response.put("docType", updateDocType);

        } else {

            httpStatus = HttpStatus.NOT_FOUND;
            message = "No se ha encontrado el tipo de documento";

        }

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @DeleteMapping("/doctypes/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        String message = "Ha sido eliminado con éxito el tipo de documento";

        docTypeService.deleteById(id);

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @GetMapping("/doctypes/pagination")
    public ResponseEntity<?> findAll(@PageableDefault(page = 0, size = 5) Pageable pageable) {

        Map<String, Object> response = new HashMap<>();
        Page<DocType> paginator = docTypeService.findAll(pageable);
        HttpStatus httpStatus = HttpStatus.OK;
        String message = !paginator.isEmpty() ? "El paginador contiene el listado de tipos de documento"
                : "El paginador contiene un listado de tipos de documento vacío ";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("paginator", paginator);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

}