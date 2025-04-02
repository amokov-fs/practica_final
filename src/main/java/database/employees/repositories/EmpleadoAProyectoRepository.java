package database.employees.repositories;

import database.employees.tables.EmpleadoAProyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoAProyectoRepository extends JpaRepository<EmpleadoAProyecto, Integer> {
    @Query("select ep from pr_empleados_proyecto ep where ep.idProyecto = :idProyecto")
    List<EmpleadoAProyecto> getEmployeesProject(@Param("idProyecto") int idProyecto);

    @Query("select ep.idEmpleado from pr_empleados_proyecto ep where ep.idProyecto = :idProyecto")
    List<Integer> getEmployeesIdProject(@Param("idProyecto") int idProyecto);

    @Query("select ep.idProyecto from pr_empleados_proyecto ep where ep.idEmpleado = :idEmpleado")
    List<Integer> getProjectsIdEmployee(@Param("idEmpleado") int idEmpleado);
}
