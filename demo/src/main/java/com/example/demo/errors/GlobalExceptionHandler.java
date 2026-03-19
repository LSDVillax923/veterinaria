package com.example.demo.errors;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // ── Errores de cliente ─────────────────────────────────────────────────────
    @ExceptionHandler(ClienteException.class)
    public String handleClienteException(ClienteException ex, Model model) {
        model.addAttribute("errorTitle",    "Error — Cliente no encontrado");
        model.addAttribute("errorSubtitle", "No pudimos encontrar al cliente");
        model.addAttribute("errorMessage",  ex.getMessage());
        return "error404";
    }

    // ── Errores de mascota ─────────────────────────────────────────────────────
    @ExceptionHandler(MascotaException.class)
    public String handleMascotaException(MascotaException ex, Model model) {
        model.addAttribute("errorTitle",    "Error — Mascota no encontrada");
        model.addAttribute("errorSubtitle", "No pudimos encontrar la mascota");
        model.addAttribute("errorMessage",  ex.getMessage());
        return "error405";
    }

    // ── Errores de validación (lanzados desde los services con IllegalArgumentException) ──
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorTitle",    "Error de validación");
        model.addAttribute("errorSubtitle", "Los datos enviados no son válidos");
        model.addAttribute("errorMessage",  ex.getMessage());
        return "error404";
    }

    // ── Error genérico — captura cualquier excepción no manejada ───────────────
    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        model.addAttribute("errorTitle",    "Error inesperado");
        model.addAttribute("errorSubtitle", "Algo salió mal en el servidor");
        model.addAttribute("errorMessage",  "Por favor intenta de nuevo más tarde.");
        return "error404";
    }
}