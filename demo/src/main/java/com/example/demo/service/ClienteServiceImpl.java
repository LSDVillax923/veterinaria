package com.example.demo.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Cliente;
import com.example.demo.errors.ClienteNotFoundException;
import com.example.demo.entities.Mascota;
import com.example.demo.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired  
    private ClienteRepository repository;

    @Autowired
    private MascotaService mascotaService;

    @Override
    public Cliente searchById(Long id) {
       return repository.findById(id).orElseThrow(
            () -> new ClienteNotFoundException(id)
       );
    }

     @Override
    public Collection<Cliente> searchAll() {
        return repository.findAll();
    }


    @Override
    public Collection<Cliente> searchAllWithMascotas() {
        Collection<Cliente> clientes = repository.findAll();
        clientes.forEach(cliente -> cliente.setMascotas(mascotaService.searchByClienteId(cliente.getId())));
        return clientes;
    }

    @Override
    public List<Mascota> getMascotasByCliente(Long clienteId) {
        return mascotaService.searchByClienteId(clienteId);
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
        return repository.findByCorreo(correo)
                .filter(cliente -> cliente.getContrasenia().equals(contrasenia))
                .orElse(null);
    }
}