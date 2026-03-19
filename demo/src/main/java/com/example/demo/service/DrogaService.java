package com.example.demo.service;

import java.util.Collection;

import com.example.demo.entities.Droga;

public interface DrogaService {

    Droga searchById(Long id);

    Collection<Droga> searchAll();

    Collection<Droga> searchDisponibles();

    void save(Droga droga);

    void update(Long id, Droga datos);

    void delete(Long id);

    void descontarUnidades(Long id, int cantidad);
}
