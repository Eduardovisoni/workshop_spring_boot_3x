package com.ejemplo.demo.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Respuesta que devuelve el servidor con el saludo generado")
public record SaludoResponse(
        @Schema(description = "Mensaje de saludo con el nombre normalizado", example = "Hola, Estudiante Ana. Bienvenido a Spring Boot 3!")
        String mensaje,
        @Schema(description = "Momento exacto en que se genero el saludo (UTC)")
        Instant timestamp
) {
}
