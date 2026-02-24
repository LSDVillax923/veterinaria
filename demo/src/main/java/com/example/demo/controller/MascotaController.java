package com.example.demo.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entities.Mascota;
import com.example.demo.service.MascotaService;


@RequestMapping("/mascotas")
@Controller
public class MascotaController {
    @Autowired
    private MascotaService mascotaService;

    @GetMapping
    public String listarMascotas(Model model) {
        Collection<Mascota> mascotas = mascotaService.searchAll();
        model.addAttribute("mascotas", mascotas);
        return "listaMascotas"; 
    }

}
