package com.example.demo.errors;

public class DrogaException extends RuntimeException {

    public DrogaException(Long id) {
        super("Droga no encontrado con id: " + id);
    }
}
