package com.example.demo.controller;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Base64;

import com.example.demo.entities.Cliente;
import com.example.demo.entities.Mascota;
import com.example.demo.service.ClienteService;
import com.example.demo.service.MascotaService;

@RequestMapping("/mascotas")
@Controller
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping({"", "/", "/listarMascotas", "/listarMascotas.html", "/listarmascotas.html"})
    public String listarMascotas(Model model) {
        Collection<Mascota> mascotas = mascotaService.searchAll();
        Map<Long, Cliente> clientesMap = clienteService.searchAll().stream()
                .collect(Collectors.toMap(Cliente::getId, c -> c));

        long saludables  = mascotas.stream().filter(m -> "activa".equalsIgnoreCase(m.getEstado())).count();
        long inactivas   = mascotas.stream().filter(m -> "inactiva".equalsIgnoreCase(m.getEstado())).count();
        long tratamiento = mascotas.stream().filter(m -> "tratamiento".equalsIgnoreCase(m.getEstado())).count();

        model.addAttribute("mascotas", mascotas);
        model.addAttribute("clientesMap", clientesMap);
        model.addAttribute("totalMascotas", mascotas.size());
        model.addAttribute("saludables", saludables);
        model.addAttribute("inactivas", inactivas);
        model.addAttribute("tratamiento", tratamiento);
        return "listarMascotas";
    }

    @GetMapping({"/detalle", "/detalleMascota.html"})
    public String verMascotaPorParametro(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("errorMascota", "Debes enviar un id, por ejemplo /mascotas/detalle?id=1");
            return listarMascotas(model);
        }
        return verMascota(id, model); // ← corregido: verMascota (sin 's')
    }

    @GetMapping("/{id}")
    public String verMascota(@PathVariable Long id, Model model) {
        Mascota mascota = mascotaService.searchById(id);
        if (mascota == null) {
            model.addAttribute("errorMascota", "No se encontró la mascota con ID " + id);
            return "detalleMascota";
        }
        model.addAttribute("mascota", mascota);
        model.addAttribute("clienteId", mascota.getClienteId());
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
                                  @RequestParam(value = "fotoFile", required = false) MultipartFile fotoFile,

                                  RedirectAttributes redirectAttributes) {
        Mascota mascotaExistente = mascotaService.searchById(id);
        if (mascotaExistente == null) {
            return "redirect:/mascotas";
        }
        BeanUtils.copyProperties(mascotaActualizada, mascotaExistente, "id", "foto", "clienteId");

        String fotoEnBase64 = convertirArchivoADataUri(fotoFile);
        if (fotoEnBase64 != null) {
            mascotaExistente.setFoto(fotoEnBase64);
        }
        mascotaService.save(mascotaExistente);
        redirectAttributes.addFlashAttribute("mensaje", "Mascota actualizada correctamente.");
        return "redirect:/mascotas";
    }

    @PostMapping("/{id}/eliminar")
    public String desactivarMascota(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Mascota mascota = mascotaService.searchById(id);
        if (mascota == null) {
            redirectAttributes.addFlashAttribute("error", "No se encontró la mascota.");
            return "redirect:/mascotas";
        }
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
        @RequestParam(value = "fotoFile", required = false) MultipartFile fotoFile,
                                       RedirectAttributes redirectAttributes) {
         String fotoEnBase64 = convertirArchivoADataUri(fotoFile);
        nuevaMascota.setFoto(fotoEnBase64 != null ? fotoEnBase64 : "default.jpg");
        if (nuevaMascota.getEstado() == null || nuevaMascota.getEstado().isBlank()) {
            nuevaMascota.setEstado("activa");
        }
        mascotaService.save(nuevaMascota);
        redirectAttributes.addFlashAttribute("mensaje", "Mascota registrada correctamente.");
        return "redirect:/mascotas";
    }
      private String convertirArchivoADataUri(MultipartFile fotoFile) {
        if (fotoFile == null || fotoFile.isEmpty()) {
            return null;
        }

        String contentType = fotoFile.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return null;
        }

        try {
            String base64 = Base64.getEncoder().encodeToString(fotoFile.getBytes());
            return "data:" + contentType + ";base64," + base64;
        } catch (Exception e) {
            return null;
        }
    }
}
