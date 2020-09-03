package com.springbootapp.coloniaweb.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.springbootapp.coloniaweb.models.entity.Network;
import com.springbootapp.coloniaweb.models.services.INetworkService;

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
public class NetworkController {

    @Autowired
    private INetworkService networkService;

    @GetMapping("/networks")
    public ResponseEntity<?> findAll() {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        String message = !networkService.findAll().isEmpty() ? "El listado de redes sociales posee información"
                : "El listado de redes sociales se encuentra vacío";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("networks", networkService.findAll());

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @GetMapping("/networks/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        String message;
        Network network = networkService.findById(id);

        httpStatus = network != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        message = network != null ? "Ha sido encontrado exitosamente la red social"
                : "No se ha encontrado la red social";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("network", network);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @PostMapping("/networks")
    public ResponseEntity<?> save(@Valid @RequestBody Network network) {

        Map<String, Object> response = new HashMap<>();
        Network newNetwork = networkService.save(network);
        HttpStatus httpStatus = HttpStatus.CREATED;
        String message = "Ha sido creado exitosamente la red social";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("network", newNetwork);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @PutMapping("/networks/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Network network, @PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        Network updateNetwork = networkService.findById(id);
        HttpStatus httpStatus;
        String message;

        if (updateNetwork != null) {

            updateNetwork.setName(network.getName());
            updateNetwork.setUpdateAt(new Date());

            networkService.save(updateNetwork);

            httpStatus = HttpStatus.CREATED;
            message = "Ha sido actualizada con éxito La red social";

            response.put("network", updateNetwork);

        } else {

            httpStatus = HttpStatus.NOT_FOUND;
            message = "No se ha encontrado la red social";

        }

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @DeleteMapping("/networks/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        String message = "Ha sido eliminada con éxito la red social";

        networkService.deleteById(id);

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

    @GetMapping("/networks/pagination")
    public ResponseEntity<?> findAll(@PageableDefault(page = 0, size = 5) Pageable pageable) {

        Map<String, Object> response = new HashMap<>();
        Page<Network> paginator = networkService.findAll(pageable);
        HttpStatus httpStatus = HttpStatus.OK;
        String message = !paginator.isEmpty() ? "El paginador contiene el listado de redes sociales"
                : "El paginador contiene un listado de redes sociales vacío ";

        response.put("status", httpStatus);
        response.put("code", httpStatus.value());
        response.put("message", message);
        response.put("paginator", paginator);

        return new ResponseEntity<Map<String, Object>>(response, httpStatus);

    }

}