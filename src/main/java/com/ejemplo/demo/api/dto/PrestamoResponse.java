package com.ejemplo.demo.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Resultado de la simulacion del prestamo")
public record PrestamoResponse(
        @Schema(description = "Cuota fija que se paga cada mes", example = "888.49")
        BigDecimal cuotaMensual,
        @Schema(description = "Total de intereses pagados durante todo el plazo", example = "661.88")
        BigDecimal interesTotal,
        @Schema(description = "Suma total a pagar: monto original mas todos los intereses", example = "10661.88")
        BigDecimal totalPagar
) {
}