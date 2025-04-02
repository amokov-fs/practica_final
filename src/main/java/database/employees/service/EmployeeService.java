package database.employees.service;


import database.employees.tables.Empleados;
import database.employees.tables.Proyectos;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {

    public ResponseEntity<List<Empleados>> getEmployees();

    public ResponseEntity<List<Proyectos>> getProjects();
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

    public ResponseEntity<Proyectos> createProject(String desc,
                                                   String fInicio,
                                                   String fFin,
                                                   String lugar,
                                                   String obser);
    public void updateEmployee(String employee);
}
