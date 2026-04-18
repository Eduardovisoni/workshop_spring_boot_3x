package com.ejemplo.demo.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Datos de un producto")
public record ProductoResponse(

        @Schema(description = "ID del producto", example = "1")
        Long id,

        @Schema(description = "Nombre del producto", example = "Coca Cola 500ml")
        String nombre,

        @Schema(description = "Precio del producto", example = "12.50")
        BigDecimal precio,

        @Schema(description = "Cantidad en stock", example = "100")
        Integer stock,

        @Schema(description = "Nombre de la categoria a la que pertenece", example = "Bebidas")
        String categoriaNombre,

        @Schema(description = "Fecha y hora de creacion")
        LocalDateTime creadoEn
) {
}
