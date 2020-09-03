package com.springbootapp.coloniaweb.utils.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

public class ResponseHandlerException {

    private Date timestamp;
    private HttpStatus status;
    private int code;
    private String message;
    private Map<String, Object> response;


    public ResponseHandlerException(HttpStatus status, int code, String message, Map<String,Object> response) {
        this.timestamp = new Date();
        this.status = status;
        this.code = code;
        this.message = message;
        this.response = response;
    }

    public ResponseHandlerException(HttpStatus status, int code, String message) {
        this.timestamp = new Date();
        this.status = status;
        this.code = code;
        this.message = message;
        this.response = new HashMap<>();
    }


    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String,Object> getResponse() {
        return this.response;
    }

    public void setResponse(Map<String,Object> response) {
        this.response = response;
    }

}