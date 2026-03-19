package com.example.demo.errors;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteNotFoundException.class)

    public String handleNotFoundException(
        ClienteNotFoundException ex , 
        Model model
        ) {
        model.addAttribute("error505", ex.getMessage());
        return "error505"; // Ruta a la plantilla de error personalizada
    }
    
    @ExceptionHandler(MascotaNotFoundException.class)

    public String handleMascotaNotFoundException(
        MascotaNotFoundException ex , 
        Model model
        ) {
        model.addAttribute("errorTitle", "Error 404");
        model.addAttribute("errorSubtitle", "La mascota no está registrada");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error404"; // Ruta a la plantilla de error personalizada
    }
}