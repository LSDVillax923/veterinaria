package com.example.demo.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;

    @Column(unique = true)
    private Long cedula;

    @Column(unique = true, nullable = false)
    private String correo;

    private String contrasenia;
    private String celular;

    @Transient  
    private List<Mascota> mascotas = new ArrayList<>();

    // Constructor 
    public Cliente(String nombre, String apellido, Long cedula,
                   String correo, String contrasenia, String celular) {
        this.nombre      = nombre;
        this.apellido    = apellido;
        this.cedula      = cedula;
        this.correo      = correo;
        this.contrasenia = contrasenia;
        this.celular     = celular;
        this.mascotas    = new ArrayList<>();
    }
}
