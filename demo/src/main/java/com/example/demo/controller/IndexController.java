package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entities.Cliente;
import com.example.demo.entities.Mascota;
import com.example.demo.service.ClienteService;
import com.example.demo.service.MascotaService;

@Controller
@RequestMapping("/inicio")
public class IndexController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private MascotaService mascotaService;

    @GetMapping
    public String inicio() {
        return "inicio";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email,
                                 @RequestParam String password,
                                 RedirectAttributes redirectAttributes) {
        Cliente cliente;
        try {
            cliente = clienteService.login(email, password);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/inicio/login";
        }

        if (cliente == null) {
            redirectAttributes.addFlashAttribute("error", "Correo o contraseña incorrectos.");
            return "redirect:/inicio/login";
        }

        List<Mascota> mascotas = mascotaService.searchByClienteId(cliente.getId());
        if (mascotas.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "No tienes mascotas registradas.");
            return "redirect:/inicio/login";
        }

        return "redirect:/clientes/" + cliente.getId() + "/mismascotas";
    }

    @GetMapping("/registro")
    public String register() {
        return "registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@RequestParam String nombre,
                                    @RequestParam String apellido,   // ← añadido
                                    @RequestParam String email,
                                    @RequestParam String telefono,
                                    @RequestParam String password,
                                    @RequestParam String confirmar,
                                    RedirectAttributes redirectAttributes) {
        if (!password.equals(confirmar)) {
            redirectAttributes.addFlashAttribute("error", "Las contraseñas no coinciden.");
            return "redirect:/inicio/registro";
        }
        try {
            Cliente nuevo = new Cliente(nombre, apellido, email, telefono, password);
            clienteService.save(nuevo);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/inicio/registro";
        }

        redirectAttributes.addFlashAttribute("mensaje", "Registro exitoso. Por favor inicia sesión.");
        return "redirect:/inicio/login";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "olvidar-clave";
    }

    @PostMapping("/forgot-password")
    public String procesarForgotPassword(@RequestParam String email,
                                          RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensaje", "Se han enviado instrucciones a " + email);
        return "redirect:/inicio/login";
    }
}
