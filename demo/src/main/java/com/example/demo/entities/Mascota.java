package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Mascota {

    private Integer id;
    private String nombre;
    private String especie;
    private String raza;
    private int edad;
    private double peso;
    private String foto;
    private String estado;
    private String enfermedad;
    private String observaciones;  

}
