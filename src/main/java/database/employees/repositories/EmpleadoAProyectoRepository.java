package database.employees.repositories;

import database.employees.tables.EmpleadoAProyecto;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoAProyectoRepository extends JpaRepository<EmpleadoAProyecto, Integer> {
    @Query("select ep from pr_empleados_proyecto ep where ep.idProyecto = :idProyecto")
    List<EmpleadoAProyecto> getEmployeesProject(@Param("idProyecto") int idProyecto);

    @Query("select ep.idProyecto from pr_empleados_proyecto ep where ep.idEmpleado = :idEmpleado")
    List<Integer> getProjectsEmployee(@Param("idEmpleado") int idEmpleado);
}
