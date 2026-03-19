package com.example.demo.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Veterinario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String cedula;
    private String celular;
    private String correo;
    private String especialidad;
    private String contrasenia;
    private String imageURL;
    private String estado;
    private int num_Atenciones;

    @OneToMany(mappedBy = "veterinario")
    private List<Tratamiento> tratamientos;

    public Veterinario(String nombre, String cedula, String celular, String correo, String especialidad, String contrasenia, String imageURL, String estado) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.celular = celular;
        this.correo = correo;
        this.especialidad = especialidad;
        this.contrasenia = contrasenia;
        this.imageURL = imageURL;
        this.estado = estado;
    }
}