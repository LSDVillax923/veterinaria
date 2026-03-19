package com.example.demo.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Admin;
import com.example.demo.errors.AdminException;
import com.example.demo.repository.AdminRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository repository;

    @Override
    public Admin searchById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new AdminException(id));
    }

    @Override
    public Collection<Admin> searchAll() {
        return repository.findAll();
    }

    @Override
    public void save(Admin admin) {
        if (admin.getNombre() == null || admin.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del admin no puede estar vacío.");
        }
        if (admin.getCorreo() == null || !admin.getCorreo().contains("@")) {
            throw new IllegalArgumentException("El correo debe contener '@'.");
        }
        // Unicidad de correo solo en creación
        if (admin.getId() == null) {
            repository.findByCorreo(admin.getCorreo()).ifPresent(existing -> {
                throw new IllegalArgumentException("Ya existe un admin con ese correo.");
            });
        }
        repository.save(admin);
    }

    @Override
    public void update(Long id, Admin datos) {
        Admin admin = searchById(id);
        admin.setNombre(datos.getNombre());
        admin.setApellido(datos.getApellido());
        admin.setCorreo(datos.getCorreo());
        admin.setContrasenia(datos.getContrasenia());
        repository.save(admin);
    }

    @Override
    public void delete(Long id) {
        searchById(id);
        repository.deleteById(id);
    }

    @Override
    public Admin login(String correo, String contrasenia) {
        return repository.findByCorreo(correo)
            .filter(a -> a.getContrasenia().equals(contrasenia))
            .orElse(null);
    }
}
