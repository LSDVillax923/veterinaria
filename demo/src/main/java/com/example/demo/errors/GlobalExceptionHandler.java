package com.example.demo.errors;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteException.class)

    public String handleNotFoundException(
        ClienteException ex , 
        Model model
        ) {
        model.addAttribute("error505", ex.getMessage());
        return "error505"; // Ruta a la plantilla de error personalizada
    }
    
    @ExceptionHandler(MascotaException.class)

    public String handleMascotaNotFoundException(
        MascotaException ex , 
        Model model
        ) {
        model.addAttribute("error404", ex.getMessage());
        return "error404"; // Ruta a la plantilla de error personalizada
    }
}