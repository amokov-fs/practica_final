package database.employees.exceptions;

public class ProjectHasEmployeesException extends RuntimeException{
    public ProjectHasEmployeesException(String object) {
        super(object);
    }
}

//excepcion de proyecto tiene empleados
