package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Tratamiento;

@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {

    // Historial médico de una mascota
    List<Tratamiento> findByMascotaId(Long mascotaId);

    // Tratamientos realizados por un veterinario
    List<Tratamiento> findByVeterinarioId(Long veterinarioId);

    // Tratamientos en un rango de fechas 
    List<Tratamiento> findByFechaBetween(LocalDate inicio, LocalDate fin);

    // Tratamientos de un veterinario en un rango de fechas
    List<Tratamiento> findByVeterinarioIdAndFechaBetween(Long veterinarioId, LocalDate inicio, LocalDate fin);
}
