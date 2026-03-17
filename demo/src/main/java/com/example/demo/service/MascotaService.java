package com.example.demo.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.example.demo.entities.Cliente;
import com.example.demo.entities.Mascota;

public interface MascotaService {
     Mascota searchById(Long id);

    Collection<Mascota> searchAll();

    List<Mascota> searchByClienteId(Long clienteId);


    void save(Mascota mascota);

    void deactivate(Long id);

    Map<Long, Cliente> getClientesMap();

    long countByEstado(Collection<Mascota> mascotas, String estado);

    void updateMascota(Long id, Mascota mascotaActualizada);

    void createMascota(Mascota nuevaMascota, Long clienteId);
}