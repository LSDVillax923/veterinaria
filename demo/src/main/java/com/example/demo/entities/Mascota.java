package com.example.demo.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la mascota no puede estar vacío")
    @Column(name = "mascota", nullable = false)
    private String nombre;
    
    @NotBlank(message = "La especie no puede estar vacía")
    private String especie;

    @NotBlank(message = "La raza no puede estar vacía")
    private String raza;

    @Positive(message = "La edad debe ser un número positivo")
    private int edad;

    @Positive(message = "El peso debe ser un número positivo")
    private double peso;

    private String foto;
    private String estado;
    private String enfermedad;
    private String observaciones;
    private String tratamiento;
    private String veterinarioAsignado;
    
    @NotNull(message = "La mascota debe tener un propietario")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Tratamiento> tratamientos = new ArrayList<>();

    public Mascota(String nombre, String especie, String raza, int edad, double peso,
                   String foto, String estado, String enfermedad, String observaciones,
                   Cliente cliente) {
        this.nombre        = nombre;
        this.especie       = especie;
        this.raza          = raza;
        this.edad          = edad;
        this.peso          = peso;
        this.foto          = foto;
        this.estado        = estado;
        this.enfermedad    = enfermedad;
        this.observaciones = observaciones;
        this.cliente       = cliente;
        this.tratamientos  = new ArrayList<>();
    }
}
