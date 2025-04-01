package database.employees.tables;

import jakarta.persistence.*;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "em_empleados")
public class Empleados {
    @Id
    @SequenceGenerator(
            name = "ID_EMPLEADO",
            sequenceName = "ID_EMPLEADO",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "ID_EMPLEADO"
    )
    @Column (name = "ID_EMPLEADO")
    private Integer id;
    @Column(
            columnDefinition = "VARCHAR(9)",
            name = "TX_NIF"
    )
    private String nif;
    @Column(
            columnDefinition = "VARCHAR(30)",
            name = "TX_NOMBRE"

    )
    private String nombre;
    @Column(
            columnDefinition = "VARCHAR(40)",
            name = "TX_APELLIDO1"
    )
    private String apellido1;
    @Column(
            columnDefinition = "VARCHAR(40)",
            name = "TX_APELLIDO2"
    )
    private String apellido2;
    @Column(
            columnDefinition = "DATE",
            name = "F_NACIMIENTO"
    )
    private LocalDate fechaNacimiento;
    @Column(
            columnDefinition = "VARCHAR(12)",
            name = "N_TELEFONO1"
    )
    private String ntelefono1;

    @Column(
            columnDefinition = "VARCHAR(12)",
            name = "N_TELEFONO2"
    )
    private String ntelefono2;

    @Column(
            columnDefinition = "VARCHAR(40)",
            name = "TX_EMAIL"
    )
    private String email;
    @Column(
            columnDefinition = "DATE",
            name = "F_ALTA"
    )
    private LocalDate fechaAlta;
    @Column(
            columnDefinition = "DATE",
            name = "F_BAJA",
            nullable = true
    )
    private LocalDate fechaBaja;
    @Column(
            columnDefinition = "VARCHAR(1)",
            name = "CX_EDOCIVIL"
    )
    private String ecivil;
    @Column(
            columnDefinition = "VARCHAR(1)",
            name = "B_FORMACIONU"
    )
    private String formacionU;

    public Empleados() {
    }

    public Empleados(String nif, String nombre, String apellido1, String apellido2, LocalDate fechaNacimiento, String ntelefono1, String ntelefono2, String email, LocalDate fechaAlta, LocalDate fechaBaja, String ecivil, String formacionU) {

        this.nif = nif;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.fechaNacimiento = fechaNacimiento;
        this.ntelefono1 = ntelefono1;
        this.ntelefono2 = ntelefono2;
        this.email = email;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.ecivil = ecivil;
        this.formacionU = formacionU;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNtelefono1() {
        return ntelefono1;
    }

    public void setNtelefono1(String ntelefono1) {
        this.ntelefono1 = ntelefono1;
    }

    public String getNtelefono2() {
        return ntelefono2;
    }

    public void setNtelefono2(String ntelefono2) {
        this.ntelefono2 = ntelefono2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDate getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getEcivil() {
        return ecivil;
    }

    public void setEcivil(String ecivil) {
        this.ecivil = ecivil;
    }

    public String getFormacionU() {
        return formacionU;
    }

    public void setFormacionU(String formacionU) {
        this.formacionU = formacionU;
    }

    @Override
    public String toString() {
        return "Empleados{" +
                "id=" + id +
                ", nif='" + nif + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido1='" + apellido1 + '\'' +
                ", apellido2='" + apellido2 + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", ntelefono1='" + ntelefono1 + '\'' +
                ", ntelefono2='" + ntelefono2 + '\'' +
                ", email='" + email + '\'' +
                ", fechaAlta='" + fechaAlta + '\'' +
                ", fechaBaja='" + fechaBaja + '\'' +
                ", ecivil='" + ecivil + '\'' +
                ", formacionU='" + formacionU + '\'' +
                '}';
    }
}


/* CREATE TABLE PRACTICA.EM_EMPLEADOS
  (
   ID_EMPLEADO 				INT(7) PRIMARY KEY NOT NULL,
   TX_NIF 					VARCHAR(9),
   TX_NOMBRE 				VARCHAR(30) NOT NULL,
   TX_APELLIDO1 			VARCHAR(40) NOT NULL,
   TX_APELLIDO2 			VARCHAR(40) NOT NULL,
   F_NACIMIENTO				DATE  NOT NULL,
   N_TELEFONO1 				VARCHAR(12) NOT NULL,
   N_TELEFONO2 				VARCHAR(12) NOT NULL,
   TX_EMAIL 				VARCHAR(40) NOT NULL,
   F_ALTA 					DATE  NOT NULL,
   F_BAJA					DATE,
   CX_EDOCIVIL 				CHAR(1) NOT NULL,
   B_FORMACIONU 			CHAR(1) NOT NULL
);*/