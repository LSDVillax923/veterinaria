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
import com.example.demo.errors.MascotaException;
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

        Collection<Mascota> mascotas = ((busqueda != null && !busqueda.isBlank())
                                     || (estado   != null && !estado.isBlank()))
            ? mascotaService.buscarPorFiltros(busqueda, estado)
            : mascotaService.searchAll();

        model.addAttribute("mascotas",           mascotas);
        model.addAttribute("clientesMap",        mascotaService.getClientesMap());
        model.addAttribute("totalMascotas",      mascotas.size());
        model.addAttribute("saludables",         mascotaService.countByEstado(mascotas, "activa"));
        model.addAttribute("inactivas",          mascotaService.countByEstado(mascotas, "inactiva"));
        model.addAttribute("tratamiento",        mascotaService.countByEstado(mascotas, "tratamiento"));
        model.addAttribute("busqueda",           busqueda);
        model.addAttribute("estadoSeleccionado", estado);
        return "listarMascotas";
    }

    @GetMapping({"/detalle", "/detalleMascota.html"})
    public String verMascotaPorParametro(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("errorMascota", "Debes enviar un id, por ejemplo /mascotas/detalle?id=1");
            return listarMascotas(null, null, model);
        }
        return verMascota(id, model);
    }

    @GetMapping("/{id}")
    public String verMascota(@PathVariable Long id, Model model) {
        // Lanza MascotaException si no existe → GlobalExceptionHandler → error404
        Mascota mascota = mascotaService.searchById(id);
        model.addAttribute("mascota",   mascota);
        model.addAttribute("clienteId", mascota.getCliente() != null ? mascota.getCliente().getId() : null);
        return "detalleMascota";
    }

    @GetMapping("/{id}/editar")
    public String editarMascota(@PathVariable Long id, Model model) {
        // Lanza MascotaException si no existe → GlobalExceptionHandler
        model.addAttribute("mascota", mascotaService.searchById(id));
        return "editarMascota";
    }

    @PostMapping("/{id}/editar")
    public String guardarEdicion(@PathVariable Long id,
                                 @ModelAttribute Mascota mascotaActualizada,
                                 RedirectAttributes redirectAttributes) {
        try {
            mascotaService.updateMascota(id, mascotaActualizada);
            redirectAttributes.addFlashAttribute("mensaje", "Mascota actualizada correctamente.");
        } catch (MascotaException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/mascotas/" + id + "/editar";
        }
        return "redirect:/mascotas";
    }

    @PostMapping("/{id}/eliminar")
    public String desactivarMascota(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // Lanza MascotaException si no existe → GlobalExceptionHandler
        mascotaService.deactivate(id);
        redirectAttributes.addFlashAttribute("mensaje", "Mascota marcada como inactiva correctamente.");
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
        } catch (MascotaException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/mascotas/nueva";
        }
        return "redirect:/mascotas";
    }
}