package database.employees.repositories;

import database.employees.tables.Proyectos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectosRepository extends JpaRepository<Proyectos, Integer> {

    @Query("Select p from pr_proyectos p where p.fechaBaja is null")
    List<Proyectos> getAll ();
}
