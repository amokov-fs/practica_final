package database.employees.controller;

import database.employees.service.EmployeeService;
import database.employees.tables.Empleados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @Autowired
    EmployeeService employeeService;
    @GetMapping
    @RequestMapping("getEmployee")
    public void getEmployee(@RequestParam(value = "employee") String employee){
        employeeService.getEmployee(employee);
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
                                                    @RequestParam(value = "fBajaEmpleado") String fBajaEmpleado,
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
                                            fBajaEmpleado,
                                            edoEmpleado,
                                            uniEmpleado);
    }

    @PutMapping
    @RequestMapping("updateEmployee")
    public void updateEmployee(@RequestParam(value = "employee") String employee){
        employeeService.updateEmployee(employee);
    }
}
