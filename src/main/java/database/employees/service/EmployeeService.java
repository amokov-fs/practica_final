package database.employees.service;


import database.employees.tables.EmpleadoAProyecto;
import database.employees.tables.Empleados;
import database.employees.tables.Proyectos;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {

    public ResponseEntity<List<Empleados>> getEmployees();

    public ResponseEntity<List<Proyectos>> getProjects();

    public ResponseEntity<List<Empleados>> getProjectEmployees(Integer idProyecto);
    public ResponseEntity<String> createEmployee(String nifEmpleado,
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

    public ResponseEntity<EmpleadoAProyecto> assignEmployeeToProject(Integer idProyecto,
                                                                     Integer idEmpleado,
                                                                     String fAlta);
    public ResponseEntity<String> deleteEmployee(Integer idEmpleado);

    public ResponseEntity<String> deleteProject(Integer idProyecto);

    public ResponseEntity<String> deleteEmployeeFromProject(Integer idProyecto, Integer idEmpleado);
}
