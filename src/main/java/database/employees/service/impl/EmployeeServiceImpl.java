package database.employees.service.impl;

import database.employees.repositories.EmpleadoAProyectoRepository;
import database.employees.repositories.EmpleadosRepository;
import database.employees.repositories.ProyectosRepository;
import database.employees.service.EmployeeService;
import database.employees.tables.EmpleadoAProyecto;
import database.employees.tables.Empleados;
import database.employees.tables.Proyectos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmpleadosRepository empleadosRepository;

    @Autowired
    ProyectosRepository proyectosRepository;

    @Autowired
    EmpleadoAProyectoRepository empleadoAProyectoRepository;

    public ResponseEntity<List<Empleados>> getEmployees(){

        List<Empleados> empleados = empleadosRepository.getAll();
        return new ResponseEntity<>(empleados, HttpStatus.OK);
    }

    public ResponseEntity<List<Proyectos>> getProjects(){

        List<Proyectos> proyectos = proyectosRepository.getAll();
        return new ResponseEntity<>(proyectos, HttpStatus.OK);
    }

    public ResponseEntity<List<EmpleadoAProyecto>> getProjectEmployees(Integer idProyecto){
        List<EmpleadoAProyecto> empleadosProyecto = empleadoAProyectoRepository.getEmpleadoAProyectoByIdEquals(idProyecto);
        return new ResponseEntity<>(empleadosProyecto, HttpStatus.OK);
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
                                                    String edoEmpleado,
                                                    String uniEmpleado){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fechaNacimiento = LocalDate.parse(fNacEmpleado, formatter);
        LocalDate fechaAlta = LocalDate.parse(fAltaEmpleado, formatter);



        Empleados empleado = new Empleados(nifEmpleado,
                                            nombreEmpleado,
                                            ap1Empleado,
                                            ap2Empleado,
                                            fechaNacimiento,
                                            tel1Empleado,
                                            tel2Empleado,
                                            emailEmpleado,
                                            fechaAlta,
                                            edoEmpleado,
                                            uniEmpleado);

        empleadosRepository.save(empleado);
        return new ResponseEntity<>(empleado, HttpStatus.CREATED);
    }

    public ResponseEntity<Proyectos> createProject(String desc,
                                                   String fInicio,
                                                   String fFin,
                                                   String lugar,
                                                   String obser){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fechaInicio = LocalDate.parse(fInicio, formatter);
        LocalDate fechaFin = LocalDate.parse(fFin, formatter);

        Proyectos proyecto = new Proyectos(desc,fechaInicio,fechaFin,lugar,obser);
        proyectosRepository.save(proyecto);
        return new ResponseEntity<>(proyecto, HttpStatus.CREATED);
    }

    public ResponseEntity<EmpleadoAProyecto> assignEmployeeToProject(Integer idProyecto,
                                                                     Integer idEmpleado,
                                                                     String fAlta){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fechaAlta = LocalDate.parse(fAlta, formatter);

        EmpleadoAProyecto empleadoAProyecto = new EmpleadoAProyecto(idProyecto,idEmpleado,fechaAlta);
        empleadoAProyectoRepository.save(empleadoAProyecto);
        return new ResponseEntity<>(empleadoAProyecto, HttpStatus.CREATED);
    }

    public void updateEmployee(String employee){
        System.out.println("update "+employee);
    }
}
