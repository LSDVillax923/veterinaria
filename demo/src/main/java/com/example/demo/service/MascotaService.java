package com.example.demo.service;

import java.util.Collection;

import com.example.demo.entities.Mascota;

public interface MascotaService {
    public Mascota searchById(Integer id);

    public Collection<Mascota> searchAll();

    public void save(Mascota mascota);

    public void delete(Integer id);

}
