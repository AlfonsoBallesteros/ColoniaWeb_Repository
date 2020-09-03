package com.springbootapp.coloniaweb.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.springbootapp.coloniaweb.models.entity.ExpJob;
import com.springbootapp.coloniaweb.models.entity.Usuario;
import com.springbootapp.coloniaweb.models.services.IExpJobService;
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
public class ExpJobController {

    @Autowired
    private IExpJobService expJobService;

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/expjobs")
    public ResponseEntity<?> findAll() {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        String message = !expJobService.findAll().isEmpty() ? "El listado de experiencia laboral posee información"
                : "El listado de experiencia laboral se encuentra vacío";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("expJobs", expJobService.findAll());

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    // Metodo de busqueda para llaves compuestas
    @GetMapping("/expjobs/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;
        ExpJob expJobShow = expJobService.findById(id);

        httpStatus = expJobShow != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        message = expJobShow != null ? "Ha sido encontrada exitosamente la experiencia laboral"
                : "No se ha encontrado la experiencia laboral";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("expJob", expJobShow);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    // Método de guardado para llaves compuestas
    @PostMapping("/expjobs")
    public ResponseEntity<?> save(@Valid @RequestBody ExpJob expJob) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;
        Calendar entryValidator;
        Calendar exitValidator;

        Usuario user = usuarioService.findById(expJob.getUsuario().getId());

        if (user != null) {

            boolean validation = true;

            for (ExpJob experience : user.getExpJobs()) {

                if (experience.getEmployment().equals(expJob.getEmployment())
                        && experience.getCompany().equals(expJob.getCompany())) {

                    validation = false;

                }

            }

            if (validation) {

                entryValidator = Calendar.getInstance();
                entryValidator.setTime(expJob.getEntry());

                if (expJob.getExit() != null) {

                    exitValidator = Calendar.getInstance();
                    exitValidator.setTime(expJob.getExit());

                    if (exitValidator.get(Calendar.YEAR) - entryValidator.get(Calendar.YEAR) >= 1) {

                        httpStatus = HttpStatus.CREATED;
                        message = "La experiencia laboral ha sido creada con éxito";

                        response.put("expJob", expJobService.save(expJob));

                    } else {

                        httpStatus = HttpStatus.BAD_REQUEST;
                        message = "La fecha mínima de experiencia laboral válida es de un año";

                    }

                } else {

                    httpStatus = HttpStatus.CREATED;
                    message = "La experiencia laboral ha sido creada con éxito";

                    response.put("expJob", expJobService.save(expJob));

                }

            } else {

                httpStatus = HttpStatus.CONFLICT;

                message = "Ya existe la experiencia laboral";

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

    @PutMapping("/expjobs/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody ExpJob expJob, @PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;
        Calendar entryValidator;
        Calendar exitValidator;
        ExpJob updateExpJob = expJobService.findById(id);

        if (updateExpJob != null) {

            entryValidator = Calendar.getInstance();
            entryValidator.setTime(expJob.getEntry());

            if (expJob.getExit() != null) {

                exitValidator = Calendar.getInstance();
                exitValidator.setTime(expJob.getExit());

                if (exitValidator.get(Calendar.YEAR) - entryValidator.get(Calendar.YEAR) >= 1) {

                    httpStatus = HttpStatus.CREATED;
                    message = "La experiencia laboral ha sido actualizada con éxito";

                    updateExpJob.setExit(expJob.getExit());
                    updateExpJob.setDescription(expJob.getDescription());
                    updateExpJob.setUpdateAt(new Date());

                    response.put("expJob", expJobService.save(updateExpJob));

                } else {

                    httpStatus = HttpStatus.BAD_REQUEST;
                    message = "La fecha mínima de experiencia válida es de un año";

                }

            } else {

                httpStatus = HttpStatus.CREATED;
                message = "La experiencia laboral ha sido actualizada con éxito";

                updateExpJob.setDescription(expJob.getDescription());
                updateExpJob.setUpdateAt(new Date());

                response.put("expJob", expJobService.save(updateExpJob));
            }

        } else {

            httpStatus = HttpStatus.NOT_FOUND;
            message = "No existe la experiencia laboral";

        }

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @DeleteMapping("/expjobs/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        String message = "Se ha eliminado con éxito la experiencia laboral";

        expJobService.deleteById(id);

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @GetMapping("/expjobs/pagination")
    public ResponseEntity<?> findAll(@PageableDefault(page = 0, size = 5) Pageable pageable) {

        Map<String, Object> response = new HashMap<>();
        Page<ExpJob> paginator = expJobService.findAll(pageable);
        HttpStatus httpStatus = HttpStatus.OK;
        String message = !paginator.isEmpty() ? "El paginador contiene el listado de experiencias laborales"
                : "El paginador contiene un listado de experiencias laborales vacío ";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("paginator", paginator);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

}