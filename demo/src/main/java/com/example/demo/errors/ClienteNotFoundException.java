package com.example.demo.errors;

public class ClienteNotFoundException extends RuntimeException 
{
    public ClienteNotFoundException(Long id) {
        super("Cliente not found with ID: " + id);
    }
    
}
