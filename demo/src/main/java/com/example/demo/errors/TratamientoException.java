package com.example.demo.errors;

public class TratamientoException extends RuntimeException {

    public TratamientoException(Long id) {
        super("Tratamiento no encontrado con id: " + id);
    }
}
