package com.ejemplo.demo.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta del health check del workshop")
public record HealthResponse(
        @Schema(description = "Estado del servidor", example = "ok")
        String estado,
        @Schema(description = "Mensaje informativo", example = "Workshop Spring Boot activo")
        String mensaje
) {
}
