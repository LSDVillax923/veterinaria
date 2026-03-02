package com.example.demo.controller;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Cliente;
import com.example.demo.entities.Mascota;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.MascotaRepository;

@RequestMapping("/mascotas")
@Controller
public class MascotaController {
    
    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private ClienteRepository clienteRepository;  // Para obtener datos del dueño

    @GetMapping({ "", "/", "/listarMascotas", "/listarMascotas.html", "/lostarmascotas.html" })
    public String listarMascotas(Model model) {
        Collection<Mascota> mascotas = mascotaRepository.findAll();
        // Crear un mapa de clientes para mostrar el dueño en la tabla
        Map<Integer, Cliente> clientesMap = clienteRepository.findAll().stream()
                .collect(Collectors.toMap(Cliente::getId, c -> c));
        
        model.addAttribute("mascotas", mascotas);
        model.addAttribute("clientesMap", clientesMap);
        model.addAttribute("totalMascotas", mascotas.size());
        // Estadísticas simuladas (podrían calcularse reales)
        model.addAttribute("saludables", 5);
        model.addAttribute("tratamiento", 2);
        model.addAttribute("atencion", 1);
        return "listarMascotas"; 
    }

    @GetMapping({ "/detalle", "/detalleMascota.html" })
    public String verMascotaPorParametro(@RequestParam(required = false) Integer id, Model model) {
        if (id == null) {
            model.addAttribute("errorMascota", "Debes enviar un id, por ejemplo /mascotas/detalleMascota.html?id=1");
            return listarMascotas(model);
        }
        return verMascota(id, model);
    }

    @GetMapping("/{id}")
    public String verMascota(@PathVariable Integer id, Model model) {
        Mascota mascota = mascotaRepository.findById(id);
        if (mascota == null) {
            model.addAttribute("errorMascota", "No se encontró una mascota con ID " + id + ".");
            return listarMascotas(model);
        }
        // Obtener el cliente dueño
        Cliente dueno = clienteRepository.findById(mascota.getClienteId());
        model.addAttribute("mascota", mascota);
        model.addAttribute("dueno", dueno);
        return "detalleMascota";
    }
}