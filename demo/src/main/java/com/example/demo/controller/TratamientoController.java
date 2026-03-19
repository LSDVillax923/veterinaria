package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entities.Tratamiento;
import com.example.demo.service.TratamientoService;

@Controller
@RequestMapping("/tratamientos")
public class TratamientoController {

    @Autowired
    private TratamientoService tratamientoService;

    @GetMapping({"", "/", "/programados", "/programados.html"})
    public String listarProgramados(Model model) {
        List<Tratamiento> tratamientosProgramados = tratamientoService.listarProgramados();
        List<Tratamiento> todosLosTratamientos = tratamientoService.listarTodos();

        model.addAttribute("tratamientos", tratamientosProgramados);
        model.addAttribute("totalTratamientosProgramados", tratamientosProgramados.size());
        model.addAttribute("totalTratamientos", todosLosTratamientos.size());
        return "listarTratamientos";
    }
}