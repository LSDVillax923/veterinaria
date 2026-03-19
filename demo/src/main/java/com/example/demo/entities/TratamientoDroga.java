package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class TratamientoDroga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La línea debe pertenecer a un tratamiento")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tratamiento_id", nullable = false)
    private Tratamiento tratamiento;

    @NotNull(message = "Debe indicarse una droga")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "droga_id", nullable = false)
    private Droga droga;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private int cantidad;

    public TratamientoDroga(Tratamiento tratamiento, Droga droga, int cantidad) {
        this.tratamiento = tratamiento;
        this.droga       = droga;
        this.cantidad    = cantidad;
    }
}