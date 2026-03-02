package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entities.Cliente;
import com.example.demo.entities.Mascota;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.MascotaRepository;

@Controller
@RequestMapping("/inicio")
public class IndexController {

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private MascotaRepository mascotaRepository;

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

    // Buscar cliente por email y contraseña
    Cliente cliente = clienteRepository.findAll().stream()
            .filter(c -> c.getCorreo().equals(email) && c.getContrasenia().equals(password))
            .findFirst()
            .orElse(null);

    // Si no existe el cliente
    if (cliente == null) {
        redirectAttributes.addFlashAttribute("error", "Correo o contraseña incorrectos");
        return "redirect:/inicio/login";
    }

    // Buscar mascota asociada al cliente
    Mascota mascota = mascotaRepository.findAll().stream()
            .filter(m -> m.getClienteId().equals(cliente.getId()))
            .findFirst()
            .orElse(null);

    // Si el cliente no tiene mascota
    if (mascota == null) {
        redirectAttributes.addFlashAttribute("error", "No tienes mascotas registradas");
        return "redirect:/inicio/login";
    }

    // Redirigir al detalle de la mascota
    return "redirect:/mascotas/" + mascota.getId();
}

    @GetMapping("/registro")
    public String register() {
        return "registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@RequestParam String nombre,
                                    @RequestParam String email,
                                    @RequestParam String telefono,
                                    @RequestParam String password,
                                    @RequestParam String confirmar,
                                    RedirectAttributes redirectAttributes) {
        // Validación básica
        if (!password.equals(confirmar)) {
            redirectAttributes.addFlashAttribute("error", "Las contraseñas no coinciden");
            return "redirect:/inicio/registro";
        }
        // Crear nuevo cliente con ID automático (simulado)
        int newId = clienteRepository.findAll().stream().mapToInt(Cliente::getId).max().orElse(0) + 1;
        Cliente nuevo = new Cliente(newId, nombre, "", email, password, telefono);
        clienteRepository.save(nuevo);
        redirectAttributes.addFlashAttribute("mensaje", "Registro exitoso. Por favor inicia sesión.");
        return "redirect:/inicio/login";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "olvidar-clave";
    }

    @PostMapping("/forgot-password")
    public String procesarForgotPassword(@RequestParam String email, RedirectAttributes redirectAttributes) {
        // Lógica simulada de envío de correo
        redirectAttributes.addFlashAttribute("mensaje", "Se han enviado instrucciones a " + email);
        return "redirect:/inicio/login";
    }
}