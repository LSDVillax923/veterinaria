package com.example.demo.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Tratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    // LocalDate en lugar de Date para consistencia con el resto del proyecto
    @NotNull(message = "La fecha no puede estar vacía")
    private LocalDate fecha;

    @NotNull(message = "El tratamiento debe estar asociado a una mascota")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;

    @NotNull(message = "El tratamiento debe tener un veterinario asignado")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veterinario_id", nullable = false)
    private Veterinario veterinario;

    @OneToMany(
        mappedBy = "tratamiento",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private List<TratamientoDroga> drogas = new ArrayList<>();

    public Tratamiento(String descripcion, LocalDate fecha, Mascota mascota, Veterinario veterinario) {
        this.descripcion = descripcion;
        this.fecha       = fecha;
        this.mascota     = mascota;
        this.veterinario = veterinario;
        this.drogas      = new ArrayList<>();
    }
}