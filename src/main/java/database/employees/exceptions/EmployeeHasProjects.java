package database.employees.exceptions;

public class EmployeeHasProjects extends RuntimeException{
    public EmployeeHasProjects(String object) {
        super(object);
    }
}
