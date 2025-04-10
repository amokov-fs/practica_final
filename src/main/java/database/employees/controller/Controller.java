package database.employees.controller;

import database.employees.service.EmployeeService;
import database.employees.tables.Empleados;
import database.employees.tables.EmpleadoAProyecto;
import database.employees.tables.Proyectos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
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
    public ResponseEntity<List<Empleados>> getProjectEmployees(@RequestParam(value = "idProyecto") @NotNull Integer idProyecto){
        return employeeService.getProjectEmployees(idProyecto);
    }

    @PostMapping
    @RequestMapping("createEmployee")
    public ResponseEntity<String> createEmployee(
            @RequestParam(value = "nifEmpleado") @Pattern(regexp = "[0-9]{8}[A-z]", message = "El NIF debe tener 8 números y una letra") String nifEmpleado,
            @RequestParam(value = "nombreEmpleado") @Pattern(regexp = "[A-ZÀ-ÿ][A-zÀ-ÿ]+[ ]?[A-ZÀ-ÿ]*[A-zÀ-ÿ]*", message = "El nombre solo puede tener letras") String nombreEmpleado,
            @RequestParam(value = "ap1Empleado") @Pattern(regexp = "[A-ZÀ-ÿ][A-zÀ-ÿ]+[- ]?[A-ZÀ-ÿ]*[A-zÀ-ÿ]*", message = "El apellido 1 solo puede tener letras y/o un guión intercalado") String ap1Empleado,
            @RequestParam(value = "ap2Empleado") @Pattern(regexp = "[A-ZÀ-ÿ][A-zÀ-ÿ]+[- ]?[A-ZÀ-ÿ]*[A-zÀ-ÿ]*", message = "El apellido 2 solo puede tener letras y/o un guión intercalado") String ap2Empleado,
            @RequestParam(value = "fNacEmpleado") @Pattern(regexp = "([0-9]{4})[/](0[1-9]|1[012])[/]([012][0-9]|3[0-1])", message = "Fecha de nacimiento incorrecta") String fNacEmpleado,
            @RequestParam(value = "tlf1Empleado") @Pattern(regexp = "[679][0-9]{8}", message = "Teléfono 1 incorrecto") String tlf1Empleado,
            @RequestParam(value = "tlf2Empleado", required = false) @Pattern(regexp = "([679][0-9]{8})?", message = "Teléfono 2 incorrecto") String tlf2Empleado,
            @RequestParam(value = "emailEmpleado") @Pattern(regexp = "[a-z0-9]+[@][a-z]+[.][a-z]+", message = "Email incorrecto") String emailEmpleado,
            @RequestParam(value = "fAltaEmpleado") @Pattern(regexp = "([0-9]{4})[/](0[1-9]|1[012])[/]([012][0-9]|3[0-1])", message = "Fecha de alta incorrecta") String fAltaEmpleado,
            @RequestParam(value = "edoEmpleado") @Pattern(regexp = "[S|C]", message = "Estado civil incorrecto") String edoEmpleado,
            @RequestParam(value = "uniEmpleado") @Pattern(regexp = "[S|N]", message = "Formación universitaria incorrecta") String uniEmpleado) {
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
    public ResponseEntity<Proyectos> createProject(@RequestParam(value = "desc") @Pattern(regexp = "[A-zÀ-ÿ0-9 -*+/]+", message = "La descripcion no puede estar vacia") String desc,
                                                    @RequestParam(value = "fInicio")
                                                        @Pattern(regexp = "([0-9]{4})[/](0[1-9]|1[012])[/]([012][0-9]|3[0-1])", message = "Fecha de inicio incorrecta") String fInicio,
                                                    @RequestParam(value = "fFin", required = false)
                                                        @Pattern(regexp = "([0-9]{4})[/](0[1-9]|1[012])[/]([012][0-9]|3[0-1])" , message = "Fecha de finalización incorrecta") String fFin,
                                                    @RequestParam(value = "lugar", required = false) String lugar,
                                                    @RequestParam(value = "obser", required = false) String obser) {
        return employeeService.createProject(desc,fInicio,fFin,lugar,obser);
    }

    @PostMapping
    @RequestMapping("assignEmployeeToProject")
    public ResponseEntity<EmpleadoAProyecto> assignEmployeeToProject(@RequestParam(value = "idProyecto") @NotNull Integer idProyecto,
                                                           @RequestParam(value = "idEmpleado") @NotNull Integer idEmpleado,
                                                           @RequestParam(value = "fAlta", required = false) @Pattern(regexp = "([0-9]{4})[/](0[1-9]|1[012])[/]([012][0-9]|3[0-1])") String fAlta) {
        return employeeService.assignEmployeeToProject(idProyecto,idEmpleado,fAlta);
    }

    @DeleteMapping
    @RequestMapping("deleteEmployee")
    public ResponseEntity<String> deleteEmployee(@RequestParam(value = "idEmpleado") @NotNull Integer idEmpleado){
        return employeeService.deleteEmployee(idEmpleado);
    }

    @DeleteMapping
    @RequestMapping("deleteProject")
    public ResponseEntity<String> deleteProject(@RequestParam(value = "idProyecto") @NotNull Integer idProyecto){
        return employeeService.deleteProject(idProyecto);
    }

    @DeleteMapping
    @RequestMapping("deleteEmployeeFromProject")
    public ResponseEntity<String> deleteEmployeeFromProject(@RequestParam(value = "idProyecto") @NotNull Integer idProyecto,
                                                            @RequestParam(value = "idEmpleado") @NotNull Integer idEmpleado){
        return employeeService.deleteEmployeeFromProject(idProyecto,idEmpleado);
    }
}
