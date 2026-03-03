package com.example.demo.controller;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entities.Cliente;
import com.example.demo.entities.Mascota;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.MascotaRepository;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @GetMapping({ "", "/", "/listar", "/listar.html" })
    public String listarClientes(Model model) {
        Collection<Cliente> clientes = clienteRepository.findAll();

        clientes.forEach(cliente -> {
            List<Mascota> mascotas = mascotaRepository.findAll().stream()
                    .filter(m -> m.getClienteId().equals(cliente.getId()))
                    .toList();
            cliente.setMascotas(new ArrayList<>(mascotas));
        });

        model.addAttribute("clientes", clientes);
        model.addAttribute("totalClientes", clientes.size());
        return "listarClientes";
    }

    @GetMapping("/nuevo")
    public String nuevoCliente() {
        return "nuevoCliente";
    }

    @PostMapping("/nuevo")
    public String guardarNuevoCliente(@RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String correo,
            @RequestParam String contrasenia,
            @RequestParam String celular,
            RedirectAttributes redirectAttributes) {

        int newId = clienteRepository.findAll().stream().mapToInt(Cliente::getId).max().orElse(0) + 1;
        Cliente cliente = new Cliente(newId, nombre, apellido, correo, contrasenia, celular);
        clienteRepository.save(cliente);
        redirectAttributes.addFlashAttribute("mensaje", "Cliente registrado correctamente");
        return "redirect:/clientes";
    }

    @GetMapping({ "/detalle", "/detalle.html" })
    public String verClientePorParametro(@RequestParam(required = false) Integer id, Model model) {
        if (id == null) {
            model.addAttribute("error", "Debes enviar un id, por ejemplo /clientes/detalle.html?id=1");
            return listarClientes(model);
        }
        return verCliente(id, model);
    }

    @GetMapping("/{id}")
    public String verCliente(@PathVariable Integer id, Model model) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            model.addAttribute("error", "No se encontró un cliente con ID " + id + ".");
            return listarClientes(model);
        }

        List<Mascota> mascotasDelCliente = mascotaRepository.findAll().stream()
                .filter(m -> m.getClienteId().equals(id))
                .toList();

        model.addAttribute("cliente", cliente);
        model.addAttribute("mascotas", mascotasDelCliente);
        model.addAttribute("totalMascotas", mascotasDelCliente.size());
        return "detalleCliente";
    }

    @GetMapping("/{id}/editar")
    public String editarCliente(@PathVariable Integer id, Model model) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            return "redirect:/clientes";
        }
        model.addAttribute("cliente", cliente);
        return "editarCliente";
    }

    @PostMapping("/{id}/editar")
    public String guardarEdicion(@PathVariable Integer id,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String correo,
            @RequestParam String contrasenia,
            @RequestParam String celular,
            RedirectAttributes redirectAttributes) {

        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            redirectAttributes.addFlashAttribute("error", "No se encontró el cliente");
            return "redirect:/clientes";
        }

        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setCorreo(correo);
        cliente.setContrasenia(contrasenia);
        cliente.setCelular(celular);

        clienteRepository.save(cliente);

        redirectAttributes.addFlashAttribute("mensaje", "Cliente actualizado correctamente");
        return "redirect:/clientes";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarCliente(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            redirectAttributes.addFlashAttribute("error", "No se encontró el cliente");
            return "redirect:/clientes";
        }

        long mascotasAsociadas = mascotaRepository.findAll().stream()
                .filter(m -> m.getClienteId().equals(id))
                .count();

        if (mascotasAsociadas > 0) {
            redirectAttributes.addFlashAttribute("error",
                    "No puedes eliminar este cliente porque tiene " + mascotasAsociadas + " mascota(s) asociada(s).");
            return "redirect:/clientes";
        }

        clienteRepository.delete(id);
        redirectAttributes.addFlashAttribute("mensaje", "Cliente eliminado correctamente");
        return "redirect:/clientes";
    }
}