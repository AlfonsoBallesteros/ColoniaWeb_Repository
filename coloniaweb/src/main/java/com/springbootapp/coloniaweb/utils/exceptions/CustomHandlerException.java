package com.springbootapp.coloniaweb.utils.exceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomHandlerException extends ResponseEntityExceptionHandler {

        // 400

        // Validación de parámetros por URL
        @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
        public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex,
                        final WebRequest request) {

                final Map<String, Object> response = new HashMap<>();

                response.put("exception", ex.getClass().getSimpleName());
                response.put("cause", "The parameter ".concat(ex.getName().concat(" should be of type "))
                                .concat(ex.getRequiredType().getSimpleName()));
                response.put("description", ex.getMostSpecificCause().getMessage());

                final ResponseHandlerException responseHandlerException = new ResponseHandlerException(
                                HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(),
                                response);

                return new ResponseEntity<Object>(responseHandlerException, new HttpHeaders(),
                                responseHandlerException.getStatus());
        }

        // Validación de request recibidos (JSON mal formados)
        @Override
        protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
                        final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

                final Map<String, Object> response = new HashMap<>();

                response.put("exception", ex.getClass().getSimpleName());

                final ResponseHandlerException responseHandlerException = new ResponseHandlerException(
                                HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                                ex.getMostSpecificCause().getMessage(), response);

                return handleExceptionInternal(ex, responseHandlerException, new HttpHeaders(), HttpStatus.BAD_REQUEST,
                                request);
        }

        // Validacion de request recibidos (@RequestBody validación de campos)
        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                        final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

                final Map<String, Object> response = new HashMap<>();

                response.put("exception", ex.getClass().getSimpleName());

                Map<String, Object> errors = new HashMap<>();

                int countErrors = 0;

                for (FieldError error : ex.getBindingResult().getFieldErrors()) {

                        errors.put(error.getField(), error.getDefaultMessage());
                        countErrors++;
                }

                response.put("errors", errors);

                final ResponseHandlerException responseHandlerException = new ResponseHandlerException(
                                HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                                "Number of errors found : ".concat(Integer.toString(countErrors)), response);

                return handleExceptionInternal(ex, responseHandlerException, headers,
                                responseHandlerException.getStatus(), request);
        }

        // Validación de @ModelAtribute de tipo constraint
        @ExceptionHandler({ ConstraintViolationException.class })
        public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex,
                        final WebRequest request) {

                final Map<String, Object> response = new HashMap<>();

                final Map<String, Object> errors = new HashMap<>();

                ex.getConstraintViolations()
                                .forEach(error -> errors.put(error.getPropertyPath().toString(), error.getMessage()));

                response.put("errors", errors);

                final ResponseHandlerException responseHandlerException = new ResponseHandlerException(
                                HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), ex.getClass().getSimpleName(),
                                response);

                return new ResponseEntity<Object>(responseHandlerException, new HttpHeaders(),
                                responseHandlerException.getStatus());
        }

        // Validación global usada por @Valid
        @Override
        protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
                        final HttpStatus status, final WebRequest request) {

                final Map<String, Object> response = new HashMap<>();

                final Map<String, Object> errors = new HashMap<>();

                ex.getBindingResult().getFieldErrors()
                                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

                response.put("errors", errors);

                final ResponseHandlerException responseHandlerException = new ResponseHandlerException(
                                HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                                "Number of errors found : ".concat(Integer.toString(ex.getErrorCount())), response);

                return handleExceptionInternal(ex, responseHandlerException, headers,
                                responseHandlerException.getStatus(), request);
        }

        @Override
        protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex,
                        final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

                final Map<String, Object> response = new HashMap<>();

                response.put("requestPartName", ex.getRequestPartName());

                final ResponseHandlerException responseHandlerException = new ResponseHandlerException(
                                HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(),
                                response);

                return new ResponseEntity<Object>(responseHandlerException, new HttpHeaders(),
                                responseHandlerException.getStatus());
        }

        // 405 // Metodos no soportados para el request realizado
        @Override
        protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
                        final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers,
                        final HttpStatus status, final WebRequest request) {

                final Map<String, Object> response = new HashMap<>();

                response.put("exception", ex.getClass().getSimpleName());
                response.put("methodsSupported", new ArrayList<>(ex.getSupportedHttpMethods()));
                response.put("cause", ex.getCause() != null ? ex.getCause().getMessage() : "No message available");

                final ResponseHandlerException responseHandlerException = new ResponseHandlerException(
                                HttpStatus.METHOD_NOT_ALLOWED, HttpStatus.METHOD_NOT_ALLOWED.value(),
                                ex.getLocalizedMessage(), response);

                return new ResponseEntity<Object>(responseHandlerException, new HttpHeaders(),
                                responseHandlerException.getStatus());
        }

        // 409
        // Validación interna de acceso a datos
        @ExceptionHandler({ DataAccessException.class, InvalidDataAccessApiUsageException.class })
        protected ResponseEntity<Object> handleConflict(final DataAccessException ex, final WebRequest request) {

                final Map<String, Object> response = new HashMap<>();

                response.put("exception", ex.getClass().getSimpleName());
                response.put("description", ex.getMostSpecificCause().getMessage());

                final ResponseHandlerException responseHandlerException = new ResponseHandlerException(
                                HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(), ex.getLocalizedMessage(), response);

                return handleExceptionInternal(ex, responseHandlerException, new HttpHeaders(),
                                responseHandlerException.getStatus(), request);
        }

        // 415

        // Validación de tipo de medios soportados
        @Override
        protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
                        final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

                final Map<String, Object> response = new HashMap<>();

                response.put("class", ex.getClass().getSimpleName());

                response.put("ejemplo", ex.getSupportedMediaTypes());

                final ResponseHandlerException responseHandlerException = new ResponseHandlerException(
                                HttpStatus.UNSUPPORTED_MEDIA_TYPE, HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                                ex.getLocalizedMessage(), response);

                return new ResponseEntity<Object>(responseHandlerException, new HttpHeaders(),
                                responseHandlerException.getStatus());
        }

}
