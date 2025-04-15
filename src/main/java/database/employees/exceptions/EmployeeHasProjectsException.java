package database.employees.exceptions;

public class EmployeeHasProjectsException extends RuntimeException{
    public EmployeeHasProjectsException(String object) {
        super(object);
    }
}
 // excepcion que avisa de que un empleado tiene proyectos