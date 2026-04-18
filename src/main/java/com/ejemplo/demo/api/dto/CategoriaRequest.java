package com.ejemplo.demo.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Datos para crear o actualizar una categoria")
public record CategoriaRequest(

        @Schema(description = "Nombre de la categoria", example = "Bebidas")
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
        String nombre,

        @Schema(description = "Descripcion opcional de la categoria", example = "Gaseosas y jugos")
        @Size(max = 255, message = "La descripcion no puede superar 255 caracteres")
        String descripcion
) {
}
