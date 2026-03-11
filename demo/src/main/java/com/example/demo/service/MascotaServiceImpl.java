package com.example.demo.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Mascota;
import com.example.demo.repository.MascotaRepository;

@Service
public class MascotaServiceImpl implements MascotaService {
    @Autowired
    MascotaRepository repository;

    @Override
    public Mascota searchById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Collection<Mascota> searchAll() {
        return repository.findAll();
    }

    @Override
    public List<Mascota> searchByClienteId(Long clienteId) {
        return repository.findByCliente_Id(clienteId);
    }

    @Override
    public void save(Mascota mascota) {
       repository.save(mascota);
        
    }

    @Override
    public void deactivate(Long id) {
        Mascota mascota = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada con ID: " + id));
        mascota.setEstado("inactiva");
        repository.save(mascota);
    }
}
