package com.example.demo.errors;

public class AdminException extends RuntimeException {

    public AdminException(Long id) {
        super("Admin no encontrado con id: " + id);
    }
}
