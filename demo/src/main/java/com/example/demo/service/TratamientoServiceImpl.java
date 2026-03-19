package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Tratamiento;
import com.example.demo.repository.TratamientoRepository;

@Service
public class TratamientoServiceImpl implements TratamientoService {

    @Autowired
    private TratamientoRepository repository;

    @Override
    public List<Tratamiento> listarTodos() {
        return repository.findAllByOrderByFechaAsc();
    }

    @Override
    public List<Tratamiento> listarProgramados() {
        return repository.findTratamientosProgramados();
    }
}