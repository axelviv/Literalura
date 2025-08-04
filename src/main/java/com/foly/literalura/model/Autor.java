package com.foly.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;

    public Autor() {
    }

    public Autor(DatosAutor a) {
        this.nombre = a.nombre();
        this.fechaNacimiento = a.fechaNacimiento();
        this.fechaFallecimiento = a.fechaFallecimiento();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(Integer fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    @Override
    public String toString() {
        return "-----AUTOR-----" + "\n" +
                "Nombre: " + nombre + "\n" +
                "Nacimiento: " + fechaNacimiento + "\n" +
                "Fallecimiento: " + fechaFallecimiento + "\n" +
                "---------------" + "\n";
    }
}
