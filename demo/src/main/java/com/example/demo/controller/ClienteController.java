package com.example.demo.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entities.Cliente;
import com.example.demo.entities.Mascota;
import com.example.demo.service.ClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

       @GetMapping({"", "/", "/listar", "/listar.html"})
    public String listarClientes(
            @RequestParam(required = false) String busqueda,
            Model model) {

        Collection<Cliente> clientes;

        boolean hayFiltro = busqueda != null && !busqueda.isBlank();

        if (hayFiltro) {
            clientes = clienteService.buscarPorFiltros(busqueda);
        } else {
            clientes = clienteService.searchAllWithMascotas();
        }

        model.addAttribute("clientes",      clientes);
        model.addAttribute("totalClientes", clientes.size());
        model.addAttribute("busqueda",      busqueda);
        return "listarClientes";
    }

    @GetMapping("/nuevo")
    public String nuevoCliente() {
        return "nuevoCliente";
    }

    @PostMapping("/nuevo")
    public String guardarNuevoCliente(@ModelAttribute Cliente cliente,
                                       RedirectAttributes redirectAttributes) {
        try {
            clienteService.save(cliente);
            redirectAttributes.addFlashAttribute("mensaje", "Cliente registrado correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/clientes/nuevo";
        }
        return "redirect:/clientes";
    }

    @GetMapping({"/detalle", "/detalle.html"})
    public String verClientePorParametro(
            @RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("error",
                "Debes enviar un id, por ejemplo /clientes/detalle?id=1");
            return listarClientes(null, model);
        }
        return verCliente(id, model);
    }

         @GetMapping({"/{id}", "/{id}.html"})
    public String verCliente(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.searchById(id);
        if (cliente == null) {
            model.addAttribute("error", 
                "No se encontró un cliente con ID " + id + ".");
            return listarClientes(null, model);
        }
        List<Mascota> mascotas = clienteService.getMascotasByCliente(id);
        model.addAttribute("cliente",       cliente);
        model.addAttribute("mascotas",      mascotas);
        model.addAttribute("totalMascotas", mascotas.size());
        return "detalleCliente";
    }

    @GetMapping("/{id}/editar")
    public String editarCliente(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.searchById(id);
        if (cliente == null) {
            return "redirect:/clientes";
        }
        model.addAttribute("cliente", cliente);
        return "editarCliente";
    }

    @PostMapping("/{id}/editar")
    public String guardarEdicion(@PathVariable Long id,
            @ModelAttribute Cliente cliente,
            RedirectAttributes redirectAttributes) {

        if (clienteService.searchById(id) == null) {
            redirectAttributes.addFlashAttribute("error", "No se encontró el cliente");
            return "error";
        }

        cliente.setId(id);

        try {
        clienteService.save(cliente);

        redirectAttributes.addFlashAttribute("mensaje", "Cliente actualizado correctamente");
        
        }catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/clientes/" + id + "/editar";
        }
        return "redirect:/clientes";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarCliente(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Cliente cliente = clienteService.searchById(id);
        if (cliente == null) {
            redirectAttributes.addFlashAttribute("error", "No se encontró el cliente");
            return "redirect:/clientes";
        }
        clienteService.delete(id);
         redirectAttributes.addFlashAttribute("mensaje", "Cliente eliminado correctamente.");
        return "redirect:/clientes";
    }

    // Mis mascotas vista del cliente
    @GetMapping("/{id}/mismascotas")

    public String misMascotas(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.searchById(id);
        if (cliente == null) {
            return "redirect:/inicio/login";
        }

        List<Mascota> mascotas = clienteService.getMascotasByCliente(id);

        model.addAttribute("cliente", cliente);
        model.addAttribute("mascotas", mascotas);
        return "mismascotas";
    }

}