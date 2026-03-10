package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import jakarta.persistence.Lob;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "mascota", nullable = false )
    private String nombre;
    
    private String especie;
    private String raza;
    private int edad;
    private double peso;
     @Lob
    @Column(name = "foto", columnDefinition = "CLOB")
    private String foto;
    private String estado;
    private String enfermedad;
    private String observaciones;  
    private Long clienteId; // Relación con Cliente

    public Mascota(String nombre, String especie, String raza, int edad, double peso,
                   String foto, String estado, String enfermedad, String observaciones, Long clienteId) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.peso = peso;
        this.foto = foto;
        this.estado = estado;
        this.enfermedad = enfermedad;
        this.observaciones = observaciones;
        this.clienteId = clienteId;
    }
}
