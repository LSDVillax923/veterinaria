package com.example.demo.controller;


import java.util.Collection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Mascota;
import com.example.demo.service.MascotaService;

@RequestMapping("/mascotas")
@Controller
public class MascotaController {
      private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @GetMapping({ "", "/", "/listarMascotas", "/listarMascotas.html", "/lostarmascotas.html" })
    public String listarMascotas(Model model) {
        Collection<Mascota> mascotas = mascotaService.searchAll();
        model.addAttribute("mascotas", mascotas);
        model.addAttribute("totalMascotas", mascotas.size());
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
         Mascota mascota = mascotaService.searchById(id);

        if (mascota == null) {
            model.addAttribute("errorMascota", "No se encontró una mascota con ID " + id + ".");
            return listarMascotas(model);
        }

    

        model.addAttribute("mascota", mascota);
        return "detalleMascota";
    }

}