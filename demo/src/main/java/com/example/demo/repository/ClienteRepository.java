package com.example.demo.repository;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.demo.entities.Cliente;

@Repository
public class ClienteRepository {
    private Map<Integer, Cliente> data = new HashMap<>();

    public ClienteRepository() {
        data.put(1, new Cliente(1, "Mario", "Pérez", "mario.perez@example.com", "Clav3@123", "3001234567"));
        data.put(2, new Cliente(2, "Luisa", "Gómez", "luisa.gomez@example.com", "luisa456", "3009876543"));
    }

    
    public Cliente findById(Integer cedula) {
        return data.get(cedula);
    }


    public Collection<Cliente> findAll() {
         return data.values().stream()
                .sorted(Comparator.comparing(Cliente::getId))
                .toList();
    }

    public void save(Cliente mascota) {
        data.put(mascota.getId(), mascota);
    }

    public void delete(Integer cedula) {
        data.remove(cedula);
    }
    
}
