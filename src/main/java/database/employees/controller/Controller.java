package database.employees.controller;

import database.employees.service.EmployeeService;
import database.employees.tables.Empleados;
import database.employees.tables.Proyectos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    EmployeeService employeeService;
    @GetMapping
    @RequestMapping("getEmployees")
    public ResponseEntity<List<Empleados>> getEmployees(){
        return employeeService.getEmployees();
    }

    @GetMapping
    @RequestMapping("getProjects")
    public ResponseEntity<List<Proyectos>> getProjects(){
        return employeeService.getProjects();
    }

    @PostMapping
    @RequestMapping("createEmployee")
    public ResponseEntity<Empleados> createEmployee(@RequestParam(value = "nifEmpleado") String nifEmpleado,
                                                    @RequestParam(value = "nombreEmpleado") String nombreEmpleado,
                                                    @RequestParam(value = "ap1Empleado") String ap1Empleado,
                                                    @RequestParam(value = "ap2Empleado") String ap2Empleado,
                                                    @RequestParam(value = "fNacEmpleado") String fNacEmpleado,
                                                    @RequestParam(value = "tlf1Empleado") String tlf1Empleado,
                                                    @RequestParam(value = "tlf2Empleado") String tlf2Empleado,
                                                    @RequestParam(value = "emailEmpleado") String emailEmpleado,
                                                    @RequestParam(value = "fAltaEmpleado") String fAltaEmpleado,
                                                    @RequestParam(value = "edoEmpleado") String edoEmpleado,
                                                    @RequestParam(value = "uniEmpleado") String uniEmpleado) {
        return employeeService.createEmployee(nifEmpleado,
                                            nombreEmpleado,
                                            ap1Empleado,
                                            ap2Empleado,
                                            fNacEmpleado,
                                            tlf1Empleado,
                                            tlf2Empleado,
                                            emailEmpleado,
                                            fAltaEmpleado,
                                            edoEmpleado,
                                            uniEmpleado);
    }

    @PostMapping
    @RequestMapping("createProject")
    public ResponseEntity<Proyectos> createProject(@RequestParam(value = "desc") String desc,
                                                    @RequestParam(value = "fInicio") String fInicio,
                                                    @RequestParam(value = "fFin") String fFin,
                                                    @RequestParam(value = "lugar") String lugar,
                                                    @RequestParam(value = "obser") String obser) {
        return employeeService.createProject(desc,fInicio,fFin,lugar,obser);
    }

    @PutMapping
    @RequestMapping("updateEmployee")
    public void updateEmployee(@RequestParam(value = "employee") String employee){
        employeeService.updateEmployee(employee);
    }
}
