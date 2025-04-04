package database.employees.tables;

import jakarta.persistence.*;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "pr_empleados_proyecto")
public class EmpleadoAProyecto {
    @Id
    @SequenceGenerator(
            name = "ID_EMPLEADO_A_PROYECTO",
            sequenceName = "ID_EMPLEADO_A_PROYECTO",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "ID_EMPLEADO_A_PROYECTO"
    )
    @Column(name = "ID_EMPLEADO_A_PROYECTO")
    private Integer id;
    @Column(columnDefinition = "INT(5)",
            name = "ID_PROYECTO",
            nullable = false)
    private Integer idProyecto;
    @Column(columnDefinition = "INT(7)",
            name = "ID_EMPLEADO",
            nullable = false)
    private Integer idEmpleado;
    @Column(
            columnDefinition = "DATE",
            name = "F_ALTA"

    )
    private LocalDate fechaAlta;

    public EmpleadoAProyecto() {
    }

    public EmpleadoAProyecto(Integer idProyecto, Integer idEmpleado, LocalDate fechaAlta) {
        this.idProyecto = idProyecto;
        this.idEmpleado = idEmpleado;
        this.fechaAlta = fechaAlta;
    }

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    @Override
    public String toString() {
        return "EmpleadosAProyectos{" +
                "idProyecto=" + idProyecto +
                ", idEmpleado=" + idEmpleado +
                ", fechaAlta=" + fechaAlta +
                '}';
    }
}

/*
CREATE TABLE PRACTICA.PR_EMPLEADOS_PROYECTO
(
   ID_PROYECTO 				INT(5) NOT NULL,
   ID_EMPLEADO 				INT(7) NOT NULL,
   F_ALTA					DATE,
   CONSTRAINT PK_PR_EMPLEADOS_PROYECTO PRIMARY KEY (ID_PROYECTO, ID_EMPLEADO)
); */