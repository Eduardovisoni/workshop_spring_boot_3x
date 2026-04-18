package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.api.dto.PrestamoRequest;
import com.ejemplo.demo.api.dto.PrestamoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PrestamoServiceTest {

    private final PrestamoService prestamoService = new PrestamoService();

    @Test
    @DisplayName("Debe calcular correctamente la cuota mensual e intereses")
    void debeCalcularSimulacionDePrestamo() {
        PrestamoRequest request = new PrestamoRequest(
                BigDecimal.valueOf(10000),
                BigDecimal.valueOf(12),
                12
        );

        PrestamoResponse response = prestamoService.simular(request);

        assertEquals(new BigDecimal("888.49"), response.cuotaMensual());
        assertEquals(new BigDecimal("661.88"), response.interesTotal());
        assertEquals(new BigDecimal("10661.88"), response.totalPagar());
    }

    @Test
    @DisplayName("Debe fallar cuando la tasa anual supera el 100%")
    void debeFallarCuandoTasaEsMayorACien() {
        PrestamoRequest request = new PrestamoRequest(
                BigDecimal.valueOf(10000),
                BigDecimal.valueOf(101),
                12
        );

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> prestamoService.simular(request)
        );
        assertTrue(ex.getMessage().contains("100%"));
    }

    @Test
    @DisplayName("Debe fallar cuando el monto supera el maximo permitido")
    void debeFallarCuandoMontoExcedeLimite() {
        PrestamoRequest request = new PrestamoRequest(
                BigDecimal.valueOf(6_000_000),
                BigDecimal.valueOf(12),
                12
        );

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> prestamoService.simular(request)
        );
        assertTrue(ex.getMessage().contains("maximo"));
    }
}
