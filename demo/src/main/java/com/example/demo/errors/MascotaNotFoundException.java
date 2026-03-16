package com.example.demo.errors;

public class MascotaNotFoundException extends RuntimeException {
    
    public MascotaNotFoundException(Long id) {
        super("Mascota not found with ID: " + id);
    }
    
}