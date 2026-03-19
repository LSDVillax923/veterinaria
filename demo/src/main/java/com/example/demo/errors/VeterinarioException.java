package com.example.demo.errors;

public class VeterinarioException extends RuntimeException {

    public VeterinarioException(Long id) {
        super("Veterinario no encontrado con id: " + id);
    }
}
