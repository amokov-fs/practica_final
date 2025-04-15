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
    @ExceptionHandler(EmployeeHasProjectsException.class) //manejo de excepción de que empleado tiene proyectos
    public ResponseEntity<Map<String, String>> handleEmployeeHasProjects(EmployeeHasProjectsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(ProjectHasEmployeesException.class)// manejo de la excepción de que un proyecto tiene empleados
    public ResponseEntity<Map<String, String>> handleProjectHasEmployeesException(ProjectHasEmployeesException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(StartDateAfterEndDateException.class) // manejo de excepcion de fecha de inicio/nacimiento posterior a fecha de fin/alta
    public ResponseEntity<Map<String, String>> handleStartDateAfterEndDateException(StartDateAfterEndDateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(HandlerMethodValidationException.class) // Manejo de la excepción de las validaciones del create
    public ResponseEntity<Map<String, String>> handleValidationException(HandlerMethodValidationException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST) // Manejo de la excepción de las valdiaciones del update
                .body(Map.of("error", ex.getAllErrors().get(0).getDefaultMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // Manejo de excepcion de validacion
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage()));
    }
}
