package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.PrestamoRequest;
import com.ejemplo.demo.api.dto.PrestamoResponse;
import com.ejemplo.demo.api.generated.SimulacionesApi;
import com.ejemplo.demo.domain.service.PrestamoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "simulaciones", description = "Endpoint desafiante: simulador de prestamos con formula de cuota fija")
@RestController
public class PrestamoController implements SimulacionesApi {

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
    @Override
    public ResponseEntity<PrestamoResponse> simularPrestamo(PrestamoRequest prestamoRequest) {
        return ResponseEntity.ok(prestamoService.simular(prestamoRequest));
    }
}
