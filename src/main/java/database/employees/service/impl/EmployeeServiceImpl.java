package database.employees.service.impl;

import database.employees.exceptions.ProjectHasEmployeesException;
import database.employees.repositories.EmpleadoAProyectoRepository;
import database.employees.repositories.EmpleadosRepository;
import database.employees.repositories.ProyectosRepository;
import database.employees.service.EmployeeService;
import database.employees.tables.EmpleadoAProyecto;
import database.employees.tables.Empleados;
import database.employees.tables.Proyectos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
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

    public ResponseEntity<List<Empleados>> getProjectEmployees(Integer idProyecto){
        List<Empleados> empleadosProyecto = empleadoAProyectoRepository.getEmployeesProject(idProyecto);
        return new ResponseEntity<>(empleadosProyecto, HttpStatus.OK);
    }

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
                                                    String uniEmpleado){
        Date fechaNacimiento = Date.valueOf(fNacEmpleado);
        Date fechaAlta = Date.valueOf(fAltaEmpleado);

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
        return new ResponseEntity<>(empleado.toString(), HttpStatus.CREATED);
    }

    public ResponseEntity<Proyectos> createProject(String desc,
                                                   String fInicio,
                                                   String fFin,
                                                   String lugar,
                                                   String obser){
        Date fechaInicio = Date.valueOf(fInicio);
        Date fechaFin = null;
        if(fFin != null){
            fechaFin = Date.valueOf(fFin);
        }


        Proyectos proyecto = new Proyectos(desc,fechaInicio,fechaFin,lugar,obser);
        proyectosRepository.save(proyecto);
        return new ResponseEntity<>(proyecto, HttpStatus.CREATED);
    }

    public ResponseEntity<EmpleadoAProyecto> assignEmployeeToProject(Integer idProyecto,
                                                                     Integer idEmpleado,
                                                                     String fAlta){
        LocalDate fechaAlta = null;
        if (fAlta != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            fechaAlta = LocalDate.parse(fAlta, formatter);
        }

        Empleados empleado = empleadosRepository.getEmployeeById(idEmpleado);
        Proyectos proyecto = proyectosRepository.getProjectById(idProyecto);

        if (empleado.getFechaBaja() == null && proyecto.getFechaBaja() == null){
            EmpleadoAProyecto empleadoAProyecto = new EmpleadoAProyecto(idProyecto,idEmpleado,fechaAlta);
            empleadoAProyectoRepository.save(empleadoAProyecto);
            return new ResponseEntity<>(empleadoAProyecto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new EmpleadoAProyecto(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> deleteEmployee(Integer idEmpleado){
        Empleados empleadoAEliminar = empleadosRepository.getEmployeeById(idEmpleado);
        if (empleadoAEliminar != null) {
            List<Integer> proyectosEmpleado = empleadoAProyectoRepository.getProjectsIdEmployee(idEmpleado);
            if (proyectosEmpleado.isEmpty()){
                empleadoAEliminar.setFechaBaja(new Date(System.currentTimeMillis()));
                empleadosRepository.save(empleadoAEliminar);
                return new ResponseEntity<>("Empleado " + empleadoAEliminar.getNombre() + " " + empleadoAEliminar.getApellido1() + " eliminado", HttpStatus.OK);
            }
            throw new ProjectHasEmployeesException("No se puede dar de baja al empleado " + empleadoAEliminar.getNombre() + " " + empleadoAEliminar.getApellido1() +" porque está asignado a el/los proyecto/s " + proyectosEmpleado);
        }
        return new ResponseEntity<>("El empleado no existe", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<String> deleteProject(Integer idProyecto){
        Proyectos proyectoAEliminar = proyectosRepository.getProjectById(idProyecto);

        if (proyectoAEliminar != null) {
            List<Integer> empleadosProyecto = empleadoAProyectoRepository.getEmployeesIdProject(idProyecto);
            if (empleadosProyecto.isEmpty()){
                proyectoAEliminar.setFechaBaja(new Date(System.currentTimeMillis()));
                proyectosRepository.save(proyectoAEliminar);
                return new ResponseEntity<>("Proyecto " + proyectoAEliminar.getDescripcion() + " eliminado", HttpStatus.OK);
            }
            throw new ProjectHasEmployeesException("No se puede dar de baja al proyecto " + proyectoAEliminar.getDescripcion() +" porque está asignado a el/los empleado/s " + empleadosProyecto);

        }
        return new ResponseEntity<>("El proyecto no existe", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<String> deleteEmployeeFromProject(Integer idProyecto,Integer idEmpleado){
        EmpleadoAProyecto asignacionAEliminar = empleadoAProyectoRepository.getByProjectAndEmployee(idProyecto,idEmpleado);

        Empleados empleado = empleadosRepository.getEmployeeById(idEmpleado);
        Proyectos proyecto = proyectosRepository.getProjectById(idProyecto);

        if (empleado.getFechaBaja() == null && proyecto.getFechaBaja() == null) {
            empleadoAProyectoRepository.delete(asignacionAEliminar);
            return new ResponseEntity<>("Empleado " + idEmpleado + " eliminado del proyecto " + idProyecto,HttpStatus.OK);
        }
        return new ResponseEntity<>("La relacion no existe" , HttpStatus.BAD_REQUEST);
    }
}
