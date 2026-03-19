package com.example.demo.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Droga;
import com.example.demo.entities.Tratamiento;
import com.example.demo.entities.TratamientoDroga;
import com.example.demo.repository.TratamientoDrogaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TratamientoDrogaServiceImpl implements TratamientoDrogaService {

    @Autowired
    private TratamientoDrogaRepository repository;

    @Override
    public Collection<TratamientoDroga> searchByTratamientoId(Long tratamientoId) {
        return repository.findByTratamientoId(tratamientoId);
    }

    @Override
    public Collection<TratamientoDroga> searchByDrogaId(Long drogaId) {
        return repository.findByDrogaId(drogaId);
    }

    @Override
    public TratamientoDroga agregar(Tratamiento tratamiento, Droga droga, int cantidad) {
        if (cantidad < 1) {
            throw new IllegalArgumentException("La cantidad debe ser al menos 1.");
        }
        return repository.save(new TratamientoDroga(tratamiento, droga, cantidad));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException(
                "Línea de tratamiento no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }
}
