package database.employees.tables;


import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.cglib.core.Local;

import java.sql.Date;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "pr_proyectos")
public class Proyectos {

    final String msgDescripcion = "La descripcion no puede estar vacia";


    @Id
    @SequenceGenerator(
            name = "ID_PROYECTO",
            sequenceName = "ID_PROYECTO",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "ID_PROYECTO"
    )
    @Column(name = "ID_PROYECTO")
    private Integer id;
    @NotBlank(message = msgDescripcion)
    @Column(
            columnDefinition = "VARCHAR(125)",
            name = "TX_DESCRIPCIÓN",
            nullable = false
    )
    private String descripcion;
    @NotNull
    @Column(
            columnDefinition = "DATE",
            name = "F_INICIO",
            nullable = false

    )
    private Date fechaInicio;
    @Column(
            columnDefinition = "DATE",
            name = "F_FIN"

    )
    private Date fechaFin;
    @Column(
            columnDefinition = "DATE",
            name = "F_BAJA"

    )
    private Date fechaBaja;
    @Column(
            columnDefinition = "VARCHAR(30)",
            name = "TX_LUGAR"
    )
    private String lugar;
    @Column(
            columnDefinition = "VARCHAR(300)",
            name = "TX_OBSERVACIONES"
    )
    private String observaciones;

    public Proyectos() {
    }

    public Proyectos(String descripcion, Date fechaInicio, Date fechaFin, String lugar, String observaciones) {
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.lugar = lugar;
        this.observaciones = observaciones;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "Proyectos{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", fechaBaja=" + fechaBaja +
                ", lugar='" + lugar + '\'' +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}


/*
CREATE TABLE PRACTICA.PR_PROYECTOS
(
   ID_PROYECTO 				INT(5) PRIMARY KEY NOT NULL,
   TX_DESCRIPCIÓN 			VARCHAR(125) NOT NULL,
   F_INICIO 				DATE NOT NULL,
   F_FIN 					DATE,
   F_BAJA					DATE,
   TX_LUGAR 				VARCHAR(30),
   TX_OBSERVACIONES 		VARCHAR(300)
); */