package com.example.demo.repository;

import java.util.Collection;
import java.util.HashMap;

import java.util.Map;
import java.util.Comparator;

import org.springframework.stereotype.Repository;

import com.example.demo.entities.Mascota;

@Repository
public class MascotaRepository {
    
    private Map<Integer, Mascota> data = new HashMap<>();

    public MascotaRepository() {
         data.put(1, new Mascota(1, "Firulais", "Perro", "Labrador", 5, 10.5, 
                "ladrador.jpg", "Sano", "Sin enfermedad", "Muy juguetón", 1));
        data.put(2, new Mascota(2, "Michi", "Gato", "Siames", 3, 4.2, 
                "siames.jpg", "Sano", "Sin enfermedad", "Muy cariñoso", 2));
    }

    
    public Mascota findById(Integer id) {
        return data.get(id);
    }

    public Collection<Mascota> findAll() {
         return data.values().stream()
                .sorted(Comparator.comparing(Mascota::getId))
                .toList();
    }

    public void save(Mascota mascota) {
        data.put(mascota.getId(), mascota);
    }

    public void delete(Integer id) {
        data.remove(id);
    }
    
}