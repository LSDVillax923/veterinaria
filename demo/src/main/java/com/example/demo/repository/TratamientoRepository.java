package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Tratamiento;
import com.example.demo.entities.Veterinario;

@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {
    
    // Método personalizado para buscar tratamientos por veterinario
    List<Tratamiento> findByVeterinario(Veterinario veterinario);
}