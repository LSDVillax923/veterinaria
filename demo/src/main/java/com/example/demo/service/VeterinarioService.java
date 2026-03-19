package com.example.demo.service;

import java.util.Collection;

import com.example.demo.entities.Veterinario;

public interface VeterinarioService {

    Veterinario searchById(Long id);

    Collection<Veterinario> searchAll();

    Collection<Veterinario> searchActivos();

    Collection<Veterinario> searchInactivos();

    void save(Veterinario veterinario);

    void update(Long id, Veterinario datos);

    // Activa o desactiva al veterinario (estado laboral)
    void cambiarEstado(Long id, String estado);

    void delete(Long id);

    Veterinario login(String correo, String contrasenia);

    void incrementarAtenciones(Long id);
}
