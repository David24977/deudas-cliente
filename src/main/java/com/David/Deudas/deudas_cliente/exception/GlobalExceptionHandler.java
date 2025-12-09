package com.David.Deudas.deudas_cliente.exception;

import com.David.Deudas.deudas_cliente.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorDto> manejarResponseStatus(ResponseStatusException ex) {

        ErrorDto error = new ErrorDto(
                ex.getStatusCode().value(),
                ex.getReason()
        );

        return ResponseEntity
                .status(ex.getStatusCode())
                .body(error);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> manejarValidaciones(MethodArgumentNotValidException ex) {

        String mensaje = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .findFirst()
                .map(err -> err.getDefaultMessage())
                .orElse("Datos inválidos");

        ErrorDto error = new ErrorDto(
                HttpStatus.BAD_REQUEST.value(),
                mensaje
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> manejarGenerales(Exception ex) {

        ErrorDto error = new ErrorDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor"
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}
