package com.ejemplo.demo.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Datos que se envian para pedir un saludo")
public record SaludoRequest(
        @Schema(description = "Nombre de la persona a saludar", example = "Ana", maxLength = 50)
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 50, message = "El nombre no debe exceder 50 caracteres")
        String nombre
) {
}
