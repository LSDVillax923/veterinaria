package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Tratamiento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    private String fecha;
    private int cantidad;

    @ManyToOne
    private Veterinario veterinario;

    public Tratamiento(String descripcion, String fecha, int cantidad) {
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.cantidad = cantidad;
    }

}
