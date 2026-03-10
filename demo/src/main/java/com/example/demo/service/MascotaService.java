package com.example.demo.service;

import java.util.Collection;
import java.util.List;

import com.example.demo.entities.Mascota;

public interface MascotaService {
    public Mascota searchById(Long id);

    public Collection<Mascota> searchAll();

    List<Mascota> searchByClienteId(Long clienteId);

    public void save(Mascota mascota);

    void deactivate(Long id);

}
