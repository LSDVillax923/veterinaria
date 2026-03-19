package com.example.demo.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    private String cedula;

    @Size(min = 10, message = "El celular debe tener al menos 10 caracteres")
    private String celular;

    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo debe contener '@' y un dominio válido")
    @Column(unique = true, nullable = false)    
    private String correo;

    private String especialidad;

    @NotBlank(message = "La contraseña no puede estar vacía")
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