package com.example.demo.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Cliente;
import com.example.demo.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired  
    private ClienteRepository repository;

    @Override
    public Cliente searchById(Long id) {
       return repository.findById(id).orElse(null);
    }

    public Collection<Cliente> searchAll() {
        return repository.findAll();
    }

    @Override
    public void save(Cliente cliente) {
        if (cliente.getNombre() == null || cliente.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
        }
        if (cliente.getApellido() == null || cliente.getApellido().isBlank()) {
            throw new IllegalArgumentException("El apellido del cliente no puede estar vacío.");
        }
        if (cliente.getCorreo() == null || !cliente.getCorreo().contains("@")) {
            throw new IllegalArgumentException("El correo debe contener '@'.");
        }
        if (cliente.getCelular() == null || cliente.getCelular().length() < 10) {
            throw new IllegalArgumentException("El celular debe tener al menos 10 caracteres.");
        }

        // ── Unicidad de correo (solo en creación) ─────────────────────────────
        if (cliente.getId() == null) {
            repository.findByCorreo(cliente.getCorreo()).ifPresent(existing -> {
                throw new IllegalArgumentException("Ya existe un cliente con ese correo.");
            });
        }

       repository.save(cliente);
        
    }


    @Override
    public void delete(Long id) {
        repository.deleteById(id);        
    }

    @Override
    public Cliente login(String correo, String contrasenia) {
        // Validar formato de correo en la capa de servicio
        if (correo == null || !correo.contains("@")) {
            throw new IllegalArgumentException("El correo debe contener '@'.");
        }

        return repository.findByCorreo(correo)
                .filter(c -> c.getContrasenia().equals(contrasenia))
                .orElse(null);
    }
}
