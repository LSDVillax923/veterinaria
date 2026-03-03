package com.example.demo.entities;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cliente {
    private Integer id;
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasenia;
    private String celular;

    private List<Mascota> mascotas = new ArrayList<>();

    // Constructor sin mascotas (para compatibilidad con datos existentes)
    public Cliente(Integer id, String nombre, String apellido,
                   String correo, String contrasenia, String celular) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.celular = celular;
        this.mascotas = new ArrayList<>();
    }
}
