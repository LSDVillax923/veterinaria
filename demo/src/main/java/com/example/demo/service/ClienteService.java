package com.example.demo.service;

import java.util.Collection;

import com.example.demo.entities.Cliente;

public interface ClienteService {
    
    public Cliente searchById(Integer cedula);

    public Collection<Cliente> searchAll();

    public void save(Cliente cliente);

    public void delete(Integer cedula);
}
