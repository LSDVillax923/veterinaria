package com.example.demo.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Mascota;
import com.example.demo.repository.MascotaRepository;

@Service
public class MascotaServiceImpl implements MascotaService {
    @Autowired
    MascotaRepository repository;

    @Override
    public Mascota searchById(Integer id) {
        return repository.findById(id);
    }


    @Override
    public Collection<Mascota> searchAll() {
        return repository.findAll();
    }

    @Override
    public void save(Mascota mascota) {
       repository.save(mascota);
        
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);        
    }

}
