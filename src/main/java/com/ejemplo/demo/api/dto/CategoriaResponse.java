package com.ejemplo.demo.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Datos de una categoria")
public record CategoriaResponse(

        @Schema(description = "ID de la categoria", example = "1")
        Long id,

        @Schema(description = "Nombre de la categoria", example = "Bebidas")
        String nombre,

        @Schema(description = "Descripcion de la categoria", example = "Gaseosas y jugos")
        String descripcion,

        @Schema(description = "Fecha y hora de creacion")
        LocalDateTime creadoEn
) {
}
