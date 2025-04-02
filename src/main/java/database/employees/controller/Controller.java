package database.employees.controller;

import database.employees.service.EmployeeService;
import database.employees.tables.Empleados;
import database.employees.tables.EmpleadoAProyecto;
import database.employees.tables.Proyectos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    @RequestMapping("getProjectEmployees")
    public ResponseEntity<List<EmpleadoAProyecto>> getProjectEmployees(@RequestParam(value = "idProyecto") Integer idProyecto){
        return employeeService.getProjectEmployees(idProyecto);
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

    @PostMapping
    @RequestMapping("assignEmployeeToProject")
    public ResponseEntity<EmpleadoAProyecto> assignEmployeeToProject(@RequestParam(value = "idProyecto") Integer idProyecto,
                                                           @RequestParam(value = "idEmpleado") Integer idEmpleado,
                                                           @RequestParam(value = "fAlta") String fAlta) {
        return employeeService.assignEmployeeToProject(idProyecto,idEmpleado,fAlta);
    }

    @DeleteMapping
    @RequestMapping("deleteEmployee")
    public ResponseEntity<String> deleteEmployee(@RequestParam(value = "idEmpleado") Integer idEmpleado){
        return employeeService.deleteEmployee(idEmpleado);
    }

    @DeleteMapping
    @RequestMapping("deleteProject")
    public ResponseEntity<String> deleteProject(@RequestParam(value = "idProyecto") Integer idProyecto){
        return employeeService.deleteProject(idProyecto);
    }
}
