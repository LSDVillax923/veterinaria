package com.example.demo.controller;

import java.util.Collection;


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

import com.example.demo.entities.Mascota;
import com.example.demo.service.ClienteService;
import com.example.demo.service.MascotaService;

@Controller
@RequestMapping("/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private ClienteService clienteService;

      @GetMapping({"", "/", "/listarMascotas", "/listarMascotas.html"})
    public String listarMascotas(
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false) String estado,
            Model model) {

        Collection<Mascota> mascotas;

        boolean hayFiltros = (busqueda != null && !busqueda.isBlank())
                          || (estado   != null && !estado.isBlank());

        if (hayFiltros) {
            mascotas = mascotaService.buscarPorFiltros(busqueda, estado);
        } else {
            mascotas = mascotaService.searchAll();
        }

        long saludables  = mascotaService.countByEstado(mascotas, "activa");
        long inactivas   = mascotaService.countByEstado(mascotas, "inactiva");
        long tratamiento = mascotaService.countByEstado(mascotas, "tratamiento");

        model.addAttribute("mascotas",           mascotas);
        model.addAttribute("clientesMap",        mascotaService.getClientesMap());
        model.addAttribute("totalMascotas",      mascotas.size());
        model.addAttribute("saludables",         saludables);
        model.addAttribute("inactivas",          inactivas);
        model.addAttribute("tratamiento",        tratamiento);
        model.addAttribute("busqueda",           busqueda);
        model.addAttribute("estadoSeleccionado", estado);
        return "listarMascotas";
    }

     @GetMapping({"/detalle", "/detalleMascota.html"})
    public String verMascotaPorParametro(
            @RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("errorMascota",
                "Debes enviar un id, por ejemplo /mascotas/detalle?id=1");
            return listarMascotas(null, null, model);
        }
        return verMascota(id, model);
    }

    @GetMapping("/{id}")
    public String verMascota(@PathVariable Long id, Model model) {
        Mascota mascota = mascotaService.searchById(id);
        if (mascota == null) {
            model.addAttribute("errorMascota", "No se encontró la mascota con ID " + id);
            return "error404";
        }
        model.addAttribute("mascota", mascota);
        model.addAttribute("clienteId", mascota.getCliente() != null ? mascota.getCliente().getId() : null);
        return "detalleMascota";
    }

    @GetMapping("/{id}/editar")
    public String editarMascota(@PathVariable Long id, Model model) {
        Mascota mascota = mascotaService.searchById(id);
        if (mascota == null) {
            return "redirect:/mascotas";
        }
        model.addAttribute("mascota", mascota);
        return "editarMascota";
    }

    @PostMapping("/{id}/editar")
    public String guardarEdicion(@PathVariable Long id,
        @ModelAttribute Mascota mascotaActualizada,
            RedirectAttributes redirectAttributes) {
        try {
            mascotaService.updateMascota(id, mascotaActualizada);
            redirectAttributes.addFlashAttribute("mensaje", "Mascota actualizada correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/mascotas";
        }
        return "redirect:/mascotas";
    }
    @PostMapping("/{id}/eliminar")
    public String desactivarMascota(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            mascotaService.deactivate(id);
            redirectAttributes.addFlashAttribute("mensaje", "Mascota marcada como inactiva correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/mascotas";
    }
    @GetMapping("/nueva")
    public String nuevaMascota(Model model) {
        model.addAttribute("clientes", clienteService.searchAll());
        return "nuevaMascota";
    }

    @PostMapping("/nueva")
    public String guardarNuevaMascota(@ModelAttribute Mascota nuevaMascota,

        @RequestParam Long clienteId,
            RedirectAttributes redirectAttributes) {
        try {
            mascotaService.createMascota(nuevaMascota, clienteId);
            redirectAttributes.addFlashAttribute("mensaje", "Mascota registrada correctamente.");
            return "redirect:/mascotas";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/mascotas/nueva";
        }
        }
}
