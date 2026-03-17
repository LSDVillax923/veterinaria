package com.example.demo.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Cliente;
import com.example.demo.entities.Mascota;
import com.example.demo.errors.MascotaNotFoundException;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.MascotaRepository;

@Service
public class MascotaServiceImpl implements MascotaService {
    @Autowired
    
    private MascotaRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Mascota searchById(Long id) {
        return repository.findById(id).orElseThrow(
            () -> new MascotaNotFoundException(id)
        );
    }

    @Override
    public Collection<Mascota> searchAll() {
        return repository.findAll();
    }

    @Override
    public List<Mascota> searchByClienteId(Long clienteId) {
        return repository.findByCliente_Id(clienteId);
    }

    @Override
    public void save(Mascota mascota) {
        
        repository.save(mascota);
    }

    @Override
    public void deactivate(Long id) {
        Mascota mascota = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada con ID: " + id));
        mascota.setEstado("inactiva");
        repository.save(mascota);
    }

    @Override
    public Map<Long, Cliente> getClientesMap() {
        return clienteRepository.findAll().stream()
                .collect(Collectors.toMap(Cliente::getId, cliente -> cliente));
    }

    @Override
    public long countByEstado(Collection<Mascota> mascotas, String estado) {
        return mascotas.stream()
                .filter(mascota -> estado.equalsIgnoreCase(mascota.getEstado()))
                .count();
    }

    @Override
    public void updateMascota(Long id, Mascota mascotaActualizada) {
        Mascota mascotaExistente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la mascota."));

        BeanUtils.copyProperties(mascotaActualizada, mascotaExistente, "id", "foto", "cliente");
        repository.save(mascotaExistente);
    }

    @Override
    public void createMascota(Mascota nuevaMascota, Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("El cliente seleccionado no existe."));

        nuevaMascota.setCliente(cliente);
        nuevaMascota.setFoto("default.jpg");
        if (nuevaMascota.getEstado() == null || nuevaMascota.getEstado().isBlank()) {
            nuevaMascota.setEstado("activa");
        }

        repository.save(nuevaMascota);
    }
}