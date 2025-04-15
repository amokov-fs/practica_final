package database.employees.exceptions;

public class StartDateAfterEndDateException extends RuntimeException{
    public StartDateAfterEndDateException(String object) {
        super(object);
    }
}

//expecion de fecha posterior a otra que no debe ser posterior