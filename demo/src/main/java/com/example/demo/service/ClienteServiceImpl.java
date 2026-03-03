package com.example.demo.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Cliente;
import com.example.demo.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository repository;

    public ClienteServiceImpl(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente searchById(Integer id) {
        return repository.findById(id);
    }

    public Collection<Cliente> searchAll() {
        return repository.findAll();
    }

    @Override
    public void save(Cliente cliente) {
       repository.save(cliente);
        
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);        
    }

}
