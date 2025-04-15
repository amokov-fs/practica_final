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
    // mensajes a mostrar
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

    // repositorios
    @Autowired
    EmpleadosRepository empleadosRepository;

    @Autowired
    ProyectosRepository proyectosRepository;

    @Autowired
    EmpleadoAProyectoRepository empleadoAProyectoRepository;

    public ResponseEntity<List<Empleados>> getEmployees(){
        // devuelve la lista de empleados activos
        List<Empleados> empleados = empleadosRepository.getAll();
        return new ResponseEntity<>(empleados, HttpStatus.OK);
    }

    // devuelve el empleado en base a su id
    public ResponseEntity<Empleados> getEmployeeById(Integer idEmpleado) {
        Empleados empleado = empleadosRepository.getEmployeeById(idEmpleado);
        return new ResponseEntity<>(empleado, HttpStatus.OK);
    }

    // devuelve el proyecto en base a su id
    public ResponseEntity<Proyectos> getProjectById(Integer idProyecto){
        Proyectos proyecto = proyectosRepository.getProjectById(idProyecto);
        return new ResponseEntity<>(proyecto, HttpStatus.OK);
    }

    // devuelve los proyectos sin fecha de baja
    public ResponseEntity<List<Proyectos>> getProjects(){

        List<Proyectos> proyectos = proyectosRepository.getAll();
        return new ResponseEntity<>(proyectos, HttpStatus.OK);
    }

    // devuelve los empleados de un proyecto
    public ResponseEntity<List<Empleados>> getProjectEmployees(Integer idProyecto){
        List<Empleados> empleadosProyecto = empleadoAProyectoRepository.getEmployeesProject(idProyecto);
        return new ResponseEntity<>(empleadosProyecto, HttpStatus.OK);
    }

    // crea un empleado con su nif, nombre, apellidos, fechas de nacimiento y alta,
    // telefonos, email, estado civil y formacion universitaria
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
        // pasa los strings de las fechas validados a Date
        Date fechaNacimiento = Date.valueOf(fNacEmpleado);
        Date fechaAlta = Date.valueOf(fAltaEmpleado);

        if (fechaNacimiento.after(fechaAlta)) { // si la fecha de nacimiento es posterior a la de alta, lanza excepcion
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

        empleadosRepository.save(empleado); // guarda el empleado y devuelve created
        return new ResponseEntity<>(empleado.toString(), HttpStatus.CREATED);
    }

    // crea el proyecto conn su descricpion, fechas de inicio y fin, lugar y observaciones
    public ResponseEntity<Proyectos> createProject(String desc,
                                                   String fInicio,
                                                   String fFin,
                                                   String lugar,
                                                   String obser){
        // pasa los strings validados de las fechas a Date
        Date fechaInicio = Date.valueOf(fInicio);
        Date fechaFin = null;
        if(fFin != null){
            fechaFin = Date.valueOf(fFin);
        }


        Proyectos proyecto = new Proyectos(desc,fechaInicio,fechaFin,lugar,obser);

        // si la fecha de inicio es posterior a la de fin lanza una excepción
        if (fechaFin != null && fechaInicio.after(fechaFin)) {
            throw new StartDateAfterEndDateException(msgStartDateAfterEndDate);
        }

        proyectosRepository.save(proyecto); //guarda proyecto y devuelve Created
        return new ResponseEntity<>(proyecto, HttpStatus.CREATED);
    }

    // asigna un empleado a un proyecto
    public ResponseEntity<EmpleadoAProyecto> assignEmployeeToProject(Integer idProyecto,
                                                                     Integer idEmpleado,
                                                                     String fAlta){
        LocalDate fechaAlta = null; //pasa las fechas a LocalDate
        if (fAlta != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            fechaAlta = LocalDate.parse(fAlta, formatter);
        }

        // saca el empleado y el proyecto de las bb.dd. por su id
        Empleados empleado = empleadosRepository.getEmployeeById(idEmpleado);
        Proyectos proyecto = proyectosRepository.getProjectById(idProyecto);

        // si el empleado y el proyecto estan activos hace la asignacion, si no devielve badrequest
        if (empleado.getFechaBaja() == null && proyecto.getFechaBaja() == null){
            EmpleadoAProyecto empleadoAProyecto = new EmpleadoAProyecto(idProyecto,idEmpleado,fechaAlta);
            empleadoAProyectoRepository.save(empleadoAProyecto);
            return new ResponseEntity<>(empleadoAProyecto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new EmpleadoAProyecto(), HttpStatus.BAD_REQUEST);

    }

    // borra el empleado
    public ResponseEntity<String> deleteEmployee(Integer idEmpleado){
        // lo busca en la base de datos
        Empleados empleadoAEliminar = empleadosRepository.getEmployeeById(idEmpleado);
        if (empleadoAEliminar != null) {
            // si esta en la bd le pone la fecha de baja
            String nombreCompleto = empleadoAEliminar.getNombre() + " " + empleadoAEliminar.getApellido1();
            List<Integer> proyectosEmpleado = empleadoAProyectoRepository.getProjectsIdEmployee(idEmpleado);
            if (proyectosEmpleado.isEmpty()){
                empleadoAEliminar.setFechaBaja(new Date(System.currentTimeMillis()));
                empleadosRepository.save(empleadoAEliminar);
                return new ResponseEntity<>("Empleado " + nombreCompleto + " eliminado", HttpStatus.OK);
            }
            // si el empleado tiene proyectos lanza expecion
            String msgException = msgEmployeeInProject1+ nombreCompleto +msgEmployeeInProject2 + proyectosEmpleado;
            throw new ProjectHasEmployeesException(msgException);
        } // si no esta en la bd devuelve no content
        return new ResponseEntity<>(msgEmployeeNotExist, HttpStatus.NO_CONTENT);
    }

    // edita empleado
    public ResponseEntity<String> updateEmployee(Empleados empleadoActualizado) {
        // si la fecha de nacimiento del empleado es posterior a la de alta lanza excepcion
        if (empleadoActualizado.getFechaNacimiento().after(empleadoActualizado.getFechaAlta())) {
            throw new StartDateAfterEndDateException(msgBirthDateAfterCreateDate);
        }
        // busca el empleado en la bd
        Empleados empleado = empleadosRepository.getEmployeeById(empleadoActualizado.getId());

        //acutaliza sus datos
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
        // lo guarda y devuelve OK
        empleadosRepository.save(empleado);
        return new ResponseEntity<>(msgEmployeeEdited, HttpStatus.OK);
    }

    // edita proyecto
    public ResponseEntity<String> updateProject(Proyectos proyectoActualizado) {
        // si la fecha de inicio del proyecto es posterior a la de fin lanza excepcion
        if (proyectoActualizado.getFechaFin() != null && proyectoActualizado.getFechaInicio().after(proyectoActualizado.getFechaFin())) {
            throw new StartDateAfterEndDateException(msgStartDateAfterEndDate);
        }
        //busca proyecto en la bd
        Proyectos proyecto = proyectosRepository.getProjectById(proyectoActualizado.getId());
        //actualiza sus datos
        proyecto.setDescripcion(proyectoActualizado.getDescripcion());
        proyecto.setFechaInicio(proyectoActualizado.getFechaInicio());
        proyecto.setFechaFin(proyectoActualizado.getFechaFin());
        proyecto.setLugar(proyectoActualizado.getLugar());
        proyecto.setObservaciones(proyectoActualizado.getObservaciones());
        //guarda en la bd y devuelve OK
        proyectosRepository.save(proyecto);
        return new ResponseEntity<>(msgProjectEdited, HttpStatus.OK);
    }

    public ResponseEntity<String> deleteProject(Integer idProyecto){
        // busca el proyecto en la bd
        Proyectos proyectoAEliminar = proyectosRepository.getProjectById(idProyecto);

        if (proyectoAEliminar != null) { // si está le pone la fecha de baja
            List<Integer> empleadosProyecto = empleadoAProyectoRepository.getEmployeesIdProject(idProyecto);
            if (empleadosProyecto.isEmpty()){
                proyectoAEliminar.setFechaBaja(new Date(System.currentTimeMillis()));
                proyectosRepository.save(proyectoAEliminar);
                return new ResponseEntity<>("Proyecto " + proyectoAEliminar.getDescripcion() + " eliminado", HttpStatus.OK);
            }
            // si el proyecto tiene empleados lanza una excepción
            throw new ProjectHasEmployeesException(msgProjectToEmployee1 + proyectoAEliminar.getDescripcion() + msgProjectToEmployee2 + empleadosProyecto);

        } // si no está en la bd devuelve no content
        return new ResponseEntity<>(msgProjectNotExist, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<String> deleteEmployeeFromProject(Integer idProyecto,Integer idEmpleado){
        //busca la asignacion a eliminar en la bd
        EmpleadoAProyecto asignacionAEliminar = empleadoAProyectoRepository.getByProjectAndEmployee(idProyecto,idEmpleado);
        // busca el empleado y el proyecto en la bd
        Empleados empleado = empleadosRepository.getEmployeeById(idEmpleado);
        Proyectos proyecto = proyectosRepository.getProjectById(idProyecto);

        // si están activos elimina la asignacion, si no devuelve bad request
        if (empleado.getFechaBaja() == null && proyecto.getFechaBaja() == null) {
            empleadoAProyectoRepository.delete(asignacionAEliminar);
            return new ResponseEntity<>("Empleado " + idEmpleado + msgRemovedFromProject + idProyecto,HttpStatus.OK);
        }
        return new ResponseEntity<>(msgAssignNotExist , HttpStatus.BAD_REQUEST);
    }
}
