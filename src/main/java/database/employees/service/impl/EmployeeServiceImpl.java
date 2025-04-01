package database.employees.service.impl;

import database.employees.repositories.EmpleadosRepository;
import database.employees.service.EmployeeService;
import database.employees.tables.Empleados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmpleadosRepository empleadosRepository;

    public void getEmployee(String employee){
        System.out.println("get "+employee);
    }

    public ResponseEntity<Empleados> createEmployee(String nifEmpleado,
                                                    String nombreEmpleado,
                                                    String ap1Empleado,
                                                    String ap2Empleado,
                                                    String fNacEmpleado,
                                                    String tel1Empleado,
                                                    String tel2Empleado,
                                                    String emailEmpleado,
                                                    String fAltaEmpleado,
                                                    String fBajaEmpleado,
                                                    String edoEmpleado,
                                                    String uniEmpleado){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fechaNacimiento = LocalDate.parse(fNacEmpleado, formatter);
        LocalDate fechaAlta = LocalDate.parse(fAltaEmpleado, formatter);
        LocalDate fechaBaja = null;
        if (!Objects.equals(fBajaEmpleado, "") && fBajaEmpleado != null){
            fechaBaja = LocalDate.parse(fBajaEmpleado, formatter);
        }


        Empleados empleado = new Empleados(nifEmpleado,
                                            nombreEmpleado,
                                            ap1Empleado,
                                            ap2Empleado,
                                            fechaNacimiento,
                                            tel1Empleado,
                                            tel2Empleado,
                                            emailEmpleado,
                                            fechaAlta,
                                            fechaBaja,
                                            edoEmpleado,
                                            uniEmpleado);
        empleadosRepository.save(empleado);
        return new ResponseEntity<>(empleado, HttpStatus.CREATED);
    }

    public void updateEmployee(String employee){
        System.out.println("update "+employee);
    }
}
