package database.employees.repositories;

import database.employees.tables.Empleados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadosRepository extends JpaRepository<Empleados, Integer> {

    @Query("Select e from em_empleados e where e.fechaBaja is null")
    List<Empleados> getAll();
}
