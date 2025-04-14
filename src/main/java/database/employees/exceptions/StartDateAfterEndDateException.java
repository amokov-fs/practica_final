package database.employees.exceptions;

public class StartDateAfterEndDateException extends RuntimeException{
    public StartDateAfterEndDateException(String object) {
        super(object);
    }
}
