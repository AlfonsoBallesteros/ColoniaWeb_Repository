package com.springbootapp.coloniaweb.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.springbootapp.coloniaweb.models.entity.ExpAcademic;
import com.springbootapp.coloniaweb.models.entity.Usuario;
import com.springbootapp.coloniaweb.models.services.IExpAcademicService;
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
public class ExpAcademicController {

    @Autowired
    private IExpAcademicService expAcademicService;

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/expacademics")
    public ResponseEntity<?> findAll() {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        String message = !expAcademicService.findAll().isEmpty()
                ? "El listado de experiencia académica posee información"
                : "El listado de experiencia académica se encuentra vacío";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("expAcademics", expAcademicService.findAll());

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @GetMapping("/expacademics/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;
        ExpAcademic expAcademicShow = expAcademicService.findById(id);

        httpStatus = expAcademicShow != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        message = expAcademicShow != null ? "Ha sido encontrada exitosamente la experiencia"
                : "No se ha encontrado la experiencia académica";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("expAcademic", expAcademicShow);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @PostMapping("/expacademics")
    public ResponseEntity<?> save(@Valid @RequestBody ExpAcademic expAcademic) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;
        Calendar entryValidator;
        Calendar exitValidator;

        Usuario user = usuarioService.findById(expAcademic.getUsuario().getId());

        if (user != null) {

            boolean validation = true;

            for (ExpAcademic experience : user.getExpAcademics()) {

                if (experience.getTitle().equals(expAcademic.getTitle())
                        && experience.getInstitute().equals(expAcademic.getInstitute())) {

                    validation = false;

                }

            }

            if (validation) {

                entryValidator = Calendar.getInstance();
                entryValidator.setTime(expAcademic.getEntry());

                if (expAcademic.getExit() != null) {

                    exitValidator = Calendar.getInstance();
                    exitValidator.setTime(expAcademic.getExit());

                    if (exitValidator.get(Calendar.YEAR) - entryValidator.get(Calendar.YEAR) >= 1) {

                        httpStatus = HttpStatus.CREATED;
                        message = "La experiencia académica ha sido creada con éxito";

                        response.put("expAcademic", expAcademicService.save(expAcademic));

                    } else {

                        httpStatus = HttpStatus.BAD_REQUEST;
                        message = "La fecha mínima de experiencia académica válida es de un año";

                    }

                } else {

                    httpStatus = HttpStatus.CREATED;

                    message = "La experiencia académica ha sido creada con éxito";

                    response.put("expAcademic", expAcademicService.save(expAcademic));

                }

            } else {

                httpStatus = HttpStatus.CONFLICT;

                message = "Ya existe una experiencia académica con la información suministrada";

            }

        } else {

            httpStatus = HttpStatus.NOT_FOUND;

            message = "No se encontró el usuario";

        }

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @PutMapping("/expacademics/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody ExpAcademic expAcademic, @PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;
        Calendar entryValidator;
        Calendar exitValidator;
        ExpAcademic updateExpAcademic = expAcademicService.findById(id);

        if (updateExpAcademic != null) {

            entryValidator = Calendar.getInstance();
            entryValidator.setTime(expAcademic.getEntry());

            if (expAcademic.getExit() != null) {

                exitValidator = Calendar.getInstance();
                exitValidator.setTime(expAcademic.getExit());

                if (exitValidator.get(Calendar.YEAR) - entryValidator.get(Calendar.YEAR) >= 1) {

                    httpStatus = HttpStatus.CREATED;
                    message = "La experiencia académica ha sido actualizada con éxito";

                    updateExpAcademic.setExit(expAcademic.getExit());
                    updateExpAcademic.setDescription(expAcademic.getDescription());
                    updateExpAcademic.setUpdateAt(new Date());

                    response.put("expAcademic", expAcademicService.save(updateExpAcademic));

                } else {

                    httpStatus = HttpStatus.BAD_REQUEST;
                    message = "La fecha mínima de experiencia académica válida es de un año";

                }

            } else {

                httpStatus = HttpStatus.OK;
                message = "La experiencia académica ha sido actualizada con éxito";

                updateExpAcademic.setDescription(expAcademic.getDescription());
                updateExpAcademic.setUpdateAt(new Date());

                response.put("expAcademic", expAcademicService.save(updateExpAcademic));

            }

        } else {

            httpStatus = HttpStatus.NOT_FOUND;
            message = "No existe la experiencia académica";

        }

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @DeleteMapping("/expacademics/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        String message = "Se ha eliminado con éxito la experiencia académica";

        expAcademicService.deleteById(id);

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);
    }

    @GetMapping("/expacademics/pagination")
    public ResponseEntity<?> findAll(@PageableDefault(page = 0, size = 5) Pageable pageable) {

        Map<String, Object> response = new HashMap<>();
        Page<ExpAcademic> paginator = expAcademicService.findAll(pageable);
        HttpStatus httpStatus = HttpStatus.OK;
        String message = !paginator.isEmpty() ? "El paginador contiene el listado de experiencias academicas"
                : "El paginador contiene un listado de experiencias académicas vacío ";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("paginator", paginator);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

}