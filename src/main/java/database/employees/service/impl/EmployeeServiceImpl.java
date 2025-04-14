package database.employees.service.impl;

import database.employees.exceptions.ProjectHasEmployeesException;
import database.employees.exceptions.StartDateAfterEndDateException;
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

    final String msgEmployeeInProject1 = "No se puede dar de baja al empleado ";
    final String msgEmployeeInProject2 = " porque está asignado a el/los proyecto/s ";
    final String msgProjectToEmployee1 = "No se puede dar de baja el proyecto ";
    final String msgProjectToEmployee2 = " porque está asignado a el/los empleado/s ";
    final String msgEmployeeNotExist = "El empleado no existe";
    final String msgEmployeeEdited = "Empleado editado";
    final String msgProjectNotExist = "El proyecto no existe";
    final String msgProjectEdited = "Proyecto editado";
    final String msgRemovedFromProject = " eliminado del proyecto ";
    final String msgAssignNotExist = "La relacion no existe";
    final String msgStartDateAfterEndDate = "La fecha de inicio es posterior a la fecha final";
    final String msgBirthDateAfterCreateDate = "La fecha de nacimiento es posterior a la fecha de alta";

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

    public ResponseEntity<Empleados> getEmployeeById(Integer idEmpleado) {
        Empleados empleado = empleadosRepository.getEmployeeById(idEmpleado);
        return new ResponseEntity<>(empleado, HttpStatus.OK);
    }

    public ResponseEntity<Proyectos> getProjectById(Integer idProyecto){
        Proyectos proyecto = proyectosRepository.getProjectById(idProyecto);
        return new ResponseEntity<>(proyecto, HttpStatus.OK);
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

        if (fechaNacimiento.after(fechaAlta)) {
            throw new StartDateAfterEndDateException(msgBirthDateAfterCreateDate);
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

        if (fechaFin != null && fechaInicio.after(fechaFin)) {
            throw new StartDateAfterEndDateException(msgStartDateAfterEndDate);
        }

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
            String nombreCompleto = empleadoAEliminar.getNombre() + " " + empleadoAEliminar.getApellido1();
            List<Integer> proyectosEmpleado = empleadoAProyectoRepository.getProjectsIdEmployee(idEmpleado);
            if (proyectosEmpleado.isEmpty()){
                empleadoAEliminar.setFechaBaja(new Date(System.currentTimeMillis()));
                empleadosRepository.save(empleadoAEliminar);
                return new ResponseEntity<>("Empleado " + nombreCompleto + " eliminado", HttpStatus.OK);
            }

            String msgException = msgEmployeeInProject1+ nombreCompleto +msgEmployeeInProject2 + proyectosEmpleado;
            throw new ProjectHasEmployeesException(msgException);
        }
        return new ResponseEntity<>(msgEmployeeNotExist, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<String> updateEmployee(Empleados empleadoActualizado) {
        if (empleadoActualizado.getFechaNacimiento().after(empleadoActualizado.getFechaAlta())) {
            throw new StartDateAfterEndDateException(msgBirthDateAfterCreateDate);
        }
        Empleados empleado = empleadosRepository.getEmployeeById(empleadoActualizado.getId());
        empleado.setNombre(empleadoActualizado.getNombre());
        empleado.setNif(empleadoActualizado.getNif());
        empleado.setApellido1(empleadoActualizado.getApellido1());
        empleado.setApellido2(empleadoActualizado.getApellido2());
        empleado.setFechaNacimiento(empleadoActualizado.getFechaNacimiento());
        empleado.setFechaAlta(empleadoActualizado.getFechaAlta());
        empleado.setNtelefono1(empleadoActualizado.getNtelefono1());
        empleado.setNtelefono2(empleadoActualizado.getNtelefono2());
        empleado.setEmail(empleadoActualizado.getEmail());
        empleado.setEcivil(empleadoActualizado.getEcivil());
        empleado.setFormacionU(empleadoActualizado.getFormacionU());
        empleadosRepository.save(empleado);
        return new ResponseEntity<>(msgEmployeeEdited, HttpStatus.OK);
    }

    public ResponseEntity<String> updateProject(Proyectos proyectoActualizado) {
        if (proyectoActualizado.getFechaFin() != null && proyectoActualizado.getFechaInicio().after(proyectoActualizado.getFechaFin())) {
            throw new StartDateAfterEndDateException(msgStartDateAfterEndDate);
        }
        Proyectos proyecto = proyectosRepository.getProjectById(proyectoActualizado.getId());
        proyecto.setDescripcion(proyectoActualizado.getDescripcion());
        proyecto.setFechaInicio(proyectoActualizado.getFechaInicio());
        proyecto.setFechaFin(proyectoActualizado.getFechaFin());
        proyecto.setLugar(proyectoActualizado.getLugar());
        proyecto.setObservaciones(proyectoActualizado.getObservaciones());
        proyectosRepository.save(proyecto);
        return new ResponseEntity<>(msgProjectEdited, HttpStatus.OK);
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
            throw new ProjectHasEmployeesException(msgProjectToEmployee1 + proyectoAEliminar.getDescripcion() + msgProjectToEmployee2 + empleadosProyecto);

        }
        return new ResponseEntity<>(msgProjectNotExist, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<String> deleteEmployeeFromProject(Integer idProyecto,Integer idEmpleado){
        EmpleadoAProyecto asignacionAEliminar = empleadoAProyectoRepository.getByProjectAndEmployee(idProyecto,idEmpleado);

        Empleados empleado = empleadosRepository.getEmployeeById(idEmpleado);
        Proyectos proyecto = proyectosRepository.getProjectById(idProyecto);

        if (empleado.getFechaBaja() == null && proyecto.getFechaBaja() == null) {
            empleadoAProyectoRepository.delete(asignacionAEliminar);
            return new ResponseEntity<>("Empleado " + idEmpleado + msgRemovedFromProject + idProyecto,HttpStatus.OK);
        }
        return new ResponseEntity<>(msgAssignNotExist , HttpStatus.BAD_REQUEST);
    }
}
