package com.example.demo.service;

import java.util.Collection;
import java.util.List;

import com.example.demo.entities.Cliente;
import com.example.demo.entities.Mascota;

public interface ClienteService {
    
    public Cliente searchById(Long id);

    public Collection<Cliente> searchAll();

    Collection<Cliente> searchAllWithMascotas();

    List<Mascota> getMascotasByCliente(Long clienteId);

    public void save(Cliente cliente);

    public void delete(Long id);

    Cliente login(String correo, String contrasenia);

    Collection<Cliente> buscarPorFiltros(String query);
}