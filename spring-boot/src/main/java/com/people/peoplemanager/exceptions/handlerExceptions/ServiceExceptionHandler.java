package com.people.peoplemanager.exceptions.handlerExceptions;

import com.people.peoplemanager.exceptions.InvalidObjectException;
import com.people.peoplemanager.exceptions.PersonNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(InvalidObjectException.class)
    public ResponseEntity<StandardError> InvalidObjectException(InvalidObjectException e, HttpServletRequest request){
        StandardError error = new StandardError(
                Instant.now(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Requisição invalida por conta de objeto(s) não aceito(s)",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<StandardError> ObjectNotFoundException(PersonNotFoundException e, HttpServletRequest request){
        StandardError error = new StandardError(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "Objeto não encontrado na base de dados através dos parametros informados",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
