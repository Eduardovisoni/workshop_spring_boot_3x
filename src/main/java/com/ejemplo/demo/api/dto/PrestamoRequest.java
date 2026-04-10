package com.ejemplo.demo.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Datos necesarios para simular un prestamo")
public record PrestamoRequest(

        @Schema(description = "Monto del prestamo en quetzales", example = "10000.00", minimum = "0.01")
        @NotNull(message = "El monto es obligatorio")
        @DecimalMin(value = "0.01", message = "El monto debe ser mayor que 0")
        BigDecimal monto,

        @Schema(description = "Tasa de interes anual en porcentaje, por ejemplo 12 significa 12% anual", example = "12.0", minimum = "0.01")
        @NotNull(message = "La tasa anual es obligatoria")
        @DecimalMin(value = "0.01", message = "La tasa anual debe ser mayor que 0")
        BigDecimal tasaAnual,

        @Schema(description = "Plazo del prestamo en meses, entre 1 y 360", example = "12", minimum = "1", maximum = "360")
        @NotNull(message = "Los meses son obligatorios")
        @Min(value = 1, message = "Los meses deben ser al menos 1")
        @Max(value = 360, message = "Los meses no pueden ser mayores a 360")
        Integer meses
) {
}