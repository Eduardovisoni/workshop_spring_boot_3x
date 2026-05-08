package com.ejemplo.demo.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estado actual de la demo (singleton o manual)")
public record EstadoResponse(
        @Schema(description = "Tipo de estado: 'singleton' o 'manual'", example = "singleton")
        String tipo,
        @Schema(description = "Valor actual almacenado", example = "25")
        int valorActual
) {
}
