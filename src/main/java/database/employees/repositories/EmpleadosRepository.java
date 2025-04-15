package database.employees.repositories;

import database.employees.tables.Empleados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadosRepository extends JpaRepository<Empleados, Integer> {
    // get de todos los empledos activos
    @Query("Select e from em_empleados e where e.fechaBaja is null")
    List<Empleados> getAll();
    // get de un empleado por su id
    @Query("select e from em_empleados e where e.id = :idEmpleado")
    Empleados getEmployeeById(@Param("idEmpleado") int idEmpleado);
}
