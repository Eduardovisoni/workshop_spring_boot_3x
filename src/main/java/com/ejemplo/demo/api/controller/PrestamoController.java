package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.PrestamoRequest;
import com.ejemplo.demo.api.dto.PrestamoResponse;
import com.ejemplo.demo.domain.service.PrestamoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Simulaciones", description = "Endpoint desafiante: simulador de prestamos con formula de cuota fija")
@RestController
@RequestMapping("/api/v1/simulaciones")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @Operation(
        summary = "Simular prestamo",
        description = "Calcula la cuota mensual, el interes total y el total a pagar dado un monto, tasa anual y plazo en meses. Usa la formula de amortizacion de cuota fija: cuota = P * (r*(1+r)^n) / ((1+r)^n - 1)"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Simulacion exitosa, devuelve cuota, interes y total"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos, por ejemplo monto negativo o meses fuera de rango"),
        @ApiResponse(responseCode = "500", description = "Error inesperado del servidor")
    })
    @PostMapping("/prestamo")
    public ResponseEntity<PrestamoResponse> simularPrestamo(
            @Valid @RequestBody PrestamoRequest request
    ) {
        return ResponseEntity.ok(prestamoService.simular(request));
    }
}