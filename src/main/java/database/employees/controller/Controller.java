package database.employees.controller;

import database.employees.service.EmployeeService;
import database.employees.tables.Empleados;
import database.employees.tables.EmpleadoAProyecto;
import database.employees.tables.Proyectos;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class Controller {

    final String regexNif = "[0-9]{8}[A-z]";
    final String regexNombre = "[A-ZÀ-ÿ][A-zÀ-ÿ]+[ ]?[A-ZÀ-ÿ]*[A-zÀ-ÿ]*";
    final String regexApellido = "[A-ZÀ-ÿ][A-zÀ-ÿ]+[- ]?[A-ZÀ-ÿ]*[A-zÀ-ÿ]*";
    final String regexFecha = "([0-9]{4})[-](0[1-9]|1[012])[-]([012][0-9]|3[0-1])";
    final String regexTlf1 = "[679][0-9]{8}";
    final String regexTlf2 = "([679][0-9]{8})?";
    final String regexEmail = "[a-z0-9.-]+[@][a-z]+[.][a-z]+";
    final String regexEdoCivil = "[S|C]";
    final String regexFormacionU = "[S|N]";
    final String regexDescripcion = "[A-zÀ-ÿ0-9 -*+/]+";
    final String msgNif = "El NIF debe tener 8 números y una letra";
    final String msgNombre = "El nombre solo puede tener letras";
    final String msgApellido1 = "El apellido 1 solo puede tener letras y/o un guión intercalado";
    final String msgApellido2 = "El apellido 2 solo puede tener letras y/o un guión intercalado";
    final String msgFechaNac = "Fecha de nacimiento incorrecta";
    final String msgFechaAlta = "Fecha de alta incorrecta";
    final String msgFechaInicio = "Fecha de inicio incorrecta";
    final String msgFechaFin = "Fecha de fin incorrecta";
    final String msgTlf1 = "Teléfono 1 incorrecto";
    final String msgTlf2 = "Teléfono 2 incorrecto";
    final String msgEmail = "Email incorrecto";
    final String msgEdoCivil = "Estado civil incorrecto";
    final String msgFormacionU = "Formación unversitaria incorrecta";
    final String msgDescripcion = "La descripcion no puede estar vacia";

    @Autowired
    EmployeeService employeeService;
    @GetMapping
    @RequestMapping("getEmployees")
    public ResponseEntity<List<Empleados>> getEmployees(){
        return employeeService.getEmployees();
    }

    @GetMapping
    @RequestMapping("getEmployeeById")
    public ResponseEntity<Empleados> getEmployeeById(
            @RequestParam(value = "idEmpleado") @NotNull Integer idEmpleado) {
        return employeeService.getEmployeeById(idEmpleado);
    }

    @GetMapping
    @RequestMapping("getProjectById")
    public ResponseEntity<Proyectos> getProjectById(
            @RequestParam(value = "idProyecto") @NotNull Integer idProyecto) {
        return employeeService.getProjectById(idProyecto);
    }

    @GetMapping
    @RequestMapping("getProjects")
    public ResponseEntity<List<Proyectos>> getProjects(){
        return employeeService.getProjects();
    }

    @GetMapping
    @RequestMapping("getProjectEmployees")
    public ResponseEntity<List<Empleados>> getProjectEmployees(
            @RequestParam(value = "idProyecto") @NotNull Integer idProyecto){
        return employeeService.getProjectEmployees(idProyecto);
    }

    @PostMapping
    @RequestMapping("createEmployee")
    public ResponseEntity<String> createEmployee(
            @RequestParam(value = "nifEmpleado") @Pattern(regexp = regexNif, message = msgNif) String nifEmpleado,
            @RequestParam(value = "nombreEmpleado") @Pattern(regexp = regexNombre, message = msgNombre) String nombreEmpleado,
            @RequestParam(value = "ap1Empleado") @Pattern(regexp = regexApellido, message = msgApellido1) String ap1Empleado,
            @RequestParam(value = "ap2Empleado") @Pattern(regexp = regexApellido, message = msgApellido2) String ap2Empleado,
            @RequestParam(value = "fNacEmpleado") @Pattern(regexp = regexFecha, message = msgFechaNac) String fNacEmpleado,
            @RequestParam(value = "tlf1Empleado") @Pattern(regexp = regexTlf1, message = msgTlf1) String tlf1Empleado,
            @RequestParam(value = "tlf2Empleado", required = false)
                @Pattern(regexp = regexTlf2, message = msgTlf2) String tlf2Empleado,
            @RequestParam(value = "emailEmpleado") @Pattern(regexp = regexEmail, message = msgEmail) String emailEmpleado,
            @RequestParam(value = "fAltaEmpleado") @Pattern(regexp = regexFecha, message = msgFechaAlta) String fAltaEmpleado,
            @RequestParam(value = "edoEmpleado") @Pattern(regexp = regexEdoCivil, message = msgEdoCivil) String edoEmpleado,
            @RequestParam(value = "uniEmpleado") @Pattern(regexp = regexFormacionU, message = msgFormacionU) String uniEmpleado) {
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
    public ResponseEntity<Proyectos> createProject(
            @RequestParam(value = "desc")
                @Pattern(regexp = regexDescripcion, message = msgDescripcion) String desc,
            @RequestParam(value = "fInicio")
                @Pattern(regexp = regexFecha, message = msgFechaInicio) String fInicio,
            @RequestParam(value = "fFin", required = false)
                @Pattern(regexp = regexFecha , message = msgFechaFin) String fFin,
            @RequestParam(value = "lugar", required = false) String lugar,
            @RequestParam(value = "obser", required = false) String obser) {
        return employeeService.createProject(desc,fInicio,fFin,lugar,obser);
    }

    @PostMapping
    @RequestMapping("assignEmployeeToProject")
    public ResponseEntity<EmpleadoAProyecto> assignEmployeeToProject(
            @RequestParam(value = "idProyecto") @NotNull Integer idProyecto,
            @RequestParam(value = "idEmpleado") @NotNull Integer idEmpleado,
            @RequestParam(value = "fAlta", required = false)
                          @Pattern(regexp = regexFecha, message = msgFechaAlta) String fAlta) {
        return employeeService.assignEmployeeToProject(idProyecto,idEmpleado,fAlta);
    }

    @PutMapping
    @RequestMapping("updateEmployee")
    public ResponseEntity<String> updateEmployee(@Valid @RequestBody Empleados empleado) {
        return employeeService.updateEmployee(empleado);
    }

    @PutMapping
    @RequestMapping("updateProject")
    public ResponseEntity<String> updateProject(@Valid @RequestBody Proyectos proyecto) {
        return employeeService.updateProject(proyecto);
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
