package database.employees.service.impl;

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
        List<EmpleadoAProyecto> empleadosProyecto = empleadoAProyectoRepository.getEmployeesProject(idProyecto);
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
        LocalDate fechaFin = null;
        if(fFin != null){
            fechaFin = LocalDate.parse(fFin, formatter);
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
                empleadoAEliminar.setFechaBaja(LocalDate.now());
                empleadosRepository.save(empleadoAEliminar);
                return new ResponseEntity<>("Empleado " + empleadoAEliminar.getNombre() + " " + empleadoAEliminar.getApellido1() + " eliminado", HttpStatus.OK);
            }
            return new ResponseEntity<>("No se puede dar de baja al empleado " + empleadoAEliminar.getNombre() + " " + empleadoAEliminar.getApellido1() +" porque está asignado a el/los proyecto/s " + proyectosEmpleado.toString(), HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>("El empleado no existe", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<String> deleteProject(Integer idProyecto){
        Proyectos proyectoAEliminar = proyectosRepository.getProjectById(idProyecto);

        if (proyectoAEliminar != null) {
            List<Integer> empleadosProyecto = empleadoAProyectoRepository.getEmployeesIdProject(idProyecto);
            if (empleadosProyecto.isEmpty()){
                proyectoAEliminar.setFechaBaja(LocalDate.now());
                proyectosRepository.save(proyectoAEliminar);
                return new ResponseEntity<>("Proyecto " + proyectoAEliminar.getDescripcion() + " eliminado", HttpStatus.OK);
            }
            return new ResponseEntity<>("No se puede dar de baja al proyecto " + proyectoAEliminar.getDescripcion() +" porque está asignado a el/los empleado/s " + empleadosProyecto.toString(), HttpStatus.BAD_REQUEST);

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
