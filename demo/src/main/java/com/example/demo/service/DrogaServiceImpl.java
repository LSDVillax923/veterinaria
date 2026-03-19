    package com.example.demo.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Droga;
import com.example.demo.errors.DrogaException;
import com.example.demo.repository.DrogaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DrogaServiceImpl implements DrogaService {

    @Autowired
    private DrogaRepository repository;

    @Override
    public Droga searchById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new DrogaException(id));
    }

    @Override
    public Collection<Droga> searchAll() {
        return repository.findAll();
    }

    @Override
    public Collection<Droga> searchDisponibles() {
        return repository.findByUnidadesDisponiblesGreaterThan(0);
    }

    @Override
    public void save(Droga droga) {
        if (droga.getNombre() == null || droga.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la droga no puede estar vacío.");
        }
        if (droga.getPrecioCompra() <= 0) {
            throw new IllegalArgumentException("El precio de compra debe ser mayor a 0.");
        }
        if (droga.getPrecioVenta() <= 0) {
            throw new IllegalArgumentException("El precio de venta debe ser mayor a 0.");
        }
        repository.save(droga);
    }

    @Override
    public void update(Long id, Droga datos) {
        Droga droga = searchById(id);
        droga.setNombre(datos.getNombre());
        droga.setPrecioCompra(datos.getPrecioCompra());
        droga.setPrecioVenta(datos.getPrecioVenta());
        droga.setUnidadesDisponibles(datos.getUnidadesDisponibles());
        droga.setUnidadesVendidas(datos.getUnidadesVendidas());
        repository.save(droga);
    }

    @Override
    public void delete(Long id) {
        searchById(id);
        repository.deleteById(id);
    }

    @Override
    public void descontarUnidades(Long id, int cantidad) {
        Droga droga = searchById(id);
        if (droga.getUnidadesDisponibles() < cantidad) {
            throw new IllegalArgumentException(
                "Stock insuficiente para la droga: " + droga.getNombre());
        }
        droga.setUnidadesDisponibles(droga.getUnidadesDisponibles() - cantidad);
        droga.setUnidadesVendidas(droga.getUnidadesVendidas() + cantidad);
        repository.save(droga);
    }
}
