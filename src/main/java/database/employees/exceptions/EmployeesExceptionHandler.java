package database.employees.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Map;

@RestControllerAdvice
public class EmployeesExceptionHandler { // Devuelven un json con clave "error" y el mensaje correspondiente como valor
    /*@ExceptionHandler(AlreadyExistsException.class) // Manejo de la excepción ya existe
    public ResponseEntity<Map<String, String>> handleAlreadyExists(AlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(DoesNotExistException.class) // Manejo de la excepción no existe
    public ResponseEntity<Map<String, String>> handleDoesNotExist(DoesNotExistException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }*/

    @ExceptionHandler(HandlerMethodValidationException.class) // Manejo de la excepción de las validaciones del create
    public ResponseEntity<Map<String, String>> handleValidationException(HandlerMethodValidationException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST) // Manejo de la excepción de las valdiaciones del update
                .body(Map.of("error", ex.getAllErrors().get(0).getDefaultMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage()));
    }
}
