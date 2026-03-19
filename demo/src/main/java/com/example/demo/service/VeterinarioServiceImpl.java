package com.example.demo.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Veterinario;
import com.example.demo.errors.VeterinarioException;
import com.example.demo.repository.VeterinarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class VeterinarioServiceImpl implements VeterinarioService {

    @Autowired
    private VeterinarioRepository repository;

    @Override
    public Veterinario searchById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new VeterinarioException(id));
    }

    @Override
    public Collection<Veterinario> searchAll() {
        return repository.findAll();
    }

    @Override
    public Collection<Veterinario> searchActivos() {
        return repository.findByEstado("activo");
    }

    @Override
    public Collection<Veterinario> searchInactivos() {
        return repository.findByEstado("inactivo");
    }

    @Override
    public void save(Veterinario veterinario) {
        if (veterinario.getNombre() == null || veterinario.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del veterinario no puede estar vacío.");
        }
        if (veterinario.getCorreo() == null || !veterinario.getCorreo().contains("@")) {
            throw new IllegalArgumentException("El correo debe contener '@'.");
        }
        // Unicidad solo en creación
        if (veterinario.getId() == null) {
            repository.findByCorreo(veterinario.getCorreo()).ifPresent(e -> {
                throw new IllegalArgumentException("Ya existe un veterinario con ese correo.");
            });
            repository.findByCedula(veterinario.getCedula()).ifPresent(e -> {
                throw new IllegalArgumentException("Ya existe un veterinario con esa cédula.");
            });
        }
        repository.save(veterinario);
    }

    @Override
    public void update(Long id, Veterinario datos) {
        Veterinario vet = searchById(id);
        vet.setNombre(datos.getNombre());
        vet.setCedula(datos.getCedula());
        vet.setCelular(datos.getCelular());
        vet.setCorreo(datos.getCorreo());
        vet.setEspecialidad(datos.getEspecialidad());
        vet.setContrasenia(datos.getContrasenia());
        vet.setImageURL(datos.getImageURL());
        repository.save(vet);
    }

    @Override
    public void cambiarEstado(Long id, String estado) {
        Veterinario vet = searchById(id);
        vet.setEstado(estado);
        repository.save(vet);
    }

    @Override
    public void delete(Long id) {
        searchById(id);
        repository.deleteById(id);
    }

    @Override
    public Veterinario login(String correo, String contrasenia) {
        return repository.findByCorreo(correo)
            .filter(v -> v.getContrasenia().equals(contrasenia))
            .orElse(null);
    }

    @Override
    public void incrementarAtenciones(Long id) {
        Veterinario vet = searchById(id);
        vet.setNum_Atenciones(vet.getNum_Atenciones() + 1);
        repository.save(vet);
    }
}
