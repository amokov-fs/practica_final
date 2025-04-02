package database.employees.service;


import database.employees.tables.Empleados;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeService {

    public ResponseEntity<List<Empleados>> getEmployees();
    public ResponseEntity<Empleados> createEmployee(String nifEmpleado,
                                                    String nombreEmpleado,
                                                    String ap1Empleado,
                                                    String ap2Empleado,
                                                    String fNacEmpleado,
                                                    String tel1Empleado,
                                                    String tel2Empleado,
                                                    String emailEmpleado,
                                                    String fAltaEmpleado,
                                                    String edoEmpleado,
                                                    String uniEmpleado);
    public void updateEmployee(String employee);
}
