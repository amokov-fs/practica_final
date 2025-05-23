package database.employees.tables;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.sql.Date;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "em_empleados")
public class Empleados {
    final String regexNif = "[0-9]{8}[A-z]";
    final String regexNombre = "[A-ZÀ-ÿ][A-zÀ-ÿ]+[ ]?[A-ZÀ-ÿ]*[A-zÀ-ÿ]*";
    final String regexApellido = "[A-ZÀ-ÿ][A-zÀ-ÿ]+[- ]?[A-ZÀ-ÿ]*[A-zÀ-ÿ]*";
    final String regexTlf1 = "[679][0-9]{8}";
    final String regexTlf2 = "([679][0-9]{8})?";
    final String regexEmail = "[a-z0-9.-]+[@][a-z]+[.][a-z]+";
    final String regexEdoCivil = "[S|C]";
    final String regexFormacionU = "[S|N]";
    final String msgNif = "El NIF debe tener 8 números y una letra";
    final String msgNombre = "El nombre solo puede tener letras";
    final String msgApellido1 = "El apellido 1 solo puede tener letras y/o un guión intercalado";
    final String msgApellido2 = "El apellido 2 solo puede tener letras y/o un guión intercalado";
    final String msgFechaNac = "Fecha de nacimiento incorrecta";
    final String msgFechaAlta = "Fecha de alta incorrecta";
    final String msgTlf1 = "Teléfono 1 incorrecto";
    final String msgTlf2 = "Teléfono 2 incorrecto";
    final String msgEmail = "Email incorrecto";
    final String msgEdoCivil = "Estado civil incorrecto";
    final String msgFormacionU = "Formación unversitaria incorrecta";

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
    @Pattern(regexp = regexNif, message = msgNif)
    @Column(
            columnDefinition = "VARCHAR(9)",
            name = "TX_NIF",
            unique = true
    )
    private String nif;
    @Pattern(regexp = regexNombre, message = msgNombre)
    @Column(
            columnDefinition = "VARCHAR(30)",
            name = "TX_NOMBRE",
            nullable = false

    )
    private String nombre;
    @Pattern(regexp = regexApellido, message = msgApellido1)
    @Column(
            columnDefinition = "VARCHAR(40)",
            name = "TX_APELLIDO1",
            nullable = false
    )
    private String apellido1;
    @Pattern(regexp = regexApellido, message = msgApellido2)
    @Column(
            columnDefinition = "VARCHAR(40)",
            name = "TX_APELLIDO2",
            nullable = false
    )
    private String apellido2;
    @NotNull(message = msgFechaNac)
    @Column(
            columnDefinition = "DATE",
            name = "F_NACIMIENTO",
            nullable = false
    )
    private Date fechaNacimiento;
    @Pattern(regexp = regexTlf1, message = msgTlf1)
    @Column(
            columnDefinition = "VARCHAR(12)",
            name = "N_TELEFONO1",
            nullable = false
    )
    private String ntelefono1;
    @Pattern(regexp = regexTlf2, message = msgTlf2)
    @Column(
            columnDefinition = "VARCHAR(12)",
            name = "N_TELEFONO2"
    )
    private String ntelefono2;
    @Pattern(regexp = regexEmail, message = msgEmail)
    @Column(
            columnDefinition = "VARCHAR(40)",
            name = "TX_EMAIL",
            nullable = false
    )
    private String email;
    @NotNull(message = msgFechaAlta)
    @Column(
            columnDefinition = "DATE",
            name = "F_ALTA",
            nullable = false
    )
    private Date fechaAlta;

    @Column(
            columnDefinition = "DATE",
            name = "F_BAJA",
            nullable = true
    )
    private Date fechaBaja;
    @Pattern(regexp = regexEdoCivil, message = msgEdoCivil)
    @Column(
            columnDefinition = "VARCHAR(1)",
            name = "CX_EDOCIVIL",
            nullable = false
    )
    private String ecivil;
    @Pattern(regexp = regexFormacionU, message = msgFormacionU)
    @Column(
            columnDefinition = "VARCHAR(1)",
            name = "B_FORMACIONU",
            nullable = false
    )
    private String formacionU;

    public Empleados() {
    }

    public Empleados(String nif, String nombre, String apellido1, String apellido2, Date fechaNacimiento, String ntelefono1, String ntelefono2, String email, Date fechaAlta, String ecivil, String formacionU) {

        this.nif = nif;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.fechaNacimiento = fechaNacimiento;
        this.ntelefono1 = ntelefono1;
        this.ntelefono2 = ntelefono2;
        this.email = email;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = null;
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
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

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
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