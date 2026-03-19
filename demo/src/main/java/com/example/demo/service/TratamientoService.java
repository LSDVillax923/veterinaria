package com.example.demo.service;

import java.util.List;

import com.example.demo.entities.Tratamiento;

public interface TratamientoService {

    List<Tratamiento> listarTodos();

    List<Tratamiento> listarProgramados();
}