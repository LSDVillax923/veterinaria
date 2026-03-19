package com.example.demo.service;

import java.util.Collection;

import com.example.demo.entities.Droga;
import com.example.demo.entities.Tratamiento;
import com.example.demo.entities.TratamientoDroga;

public interface TratamientoDrogaService {

    Collection<TratamientoDroga> searchByTratamientoId(Long tratamientoId);

    Collection<TratamientoDroga> searchByDrogaId(Long drogaId);

    TratamientoDroga agregar(Tratamiento tratamiento, Droga droga, int cantidad);

    void delete(Long id);
}
