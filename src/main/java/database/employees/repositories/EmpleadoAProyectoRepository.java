package database.employees.repositories;

import database.employees.tables.EmpleadoAProyecto;
import database.employees.tables.Empleados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoAProyectoRepository extends JpaRepository<EmpleadoAProyecto, Integer> {
    //query de los empleados sin fecha de baja
    @Query("select e from pr_empleados_proyecto ep join em_empleados e on ep.idEmpleado = e.id where ep.idProyecto = :idProyecto")
    List<Empleados> getEmployeesProject(@Param("idProyecto") int idProyecto);
    // query de los empleados por proyecto
    @Query("select ep.idEmpleado from pr_empleados_proyecto ep where ep.idProyecto = :idProyecto")
    List<Integer> getEmployeesIdProject(@Param("idProyecto") int idProyecto);
    // query de los proyectos por empleado
    @Query("select ep.idProyecto from pr_empleados_proyecto ep where ep.idEmpleado = :idEmpleado")
    List<Integer> getProjectsIdEmployee(@Param("idEmpleado") int idEmpleado);
    // query que comprueba si un proyecto esta asignado a un empleado
    @Query("select ep from pr_empleados_proyecto ep where ep.idEmpleado = :idEmpleado and ep.idProyecto = :idProyecto")
    EmpleadoAProyecto getByProjectAndEmployee(@Param("idProyecto") int idProyecto,
                                          @Param("idEmpleado") int idEmpleado);
}
