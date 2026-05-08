package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.HealthResponse;
import com.ejemplo.demo.api.dto.SaludoRequest;
import com.ejemplo.demo.api.dto.SaludoResponse;
import com.ejemplo.demo.api.generated.WorkshopApi;
import com.ejemplo.demo.domain.service.SaludoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "workshop", description = "Endpoints basicos del workshop (health y saludos)")
@RestController
public class SaludoController implements WorkshopApi {

    private final SaludoService saludoService;

    public SaludoController(SaludoService saludoService) {
        this.saludoService = saludoService;
    }

    @Operation(summary = "Health check", description = "Verifica que el servidor este corriendo.")
    @ApiResponse(responseCode = "200", description = "El servidor esta activo")
    @Override
    public ResponseEntity<HealthResponse> getWorkshopHealth() {
        return ResponseEntity.ok(new HealthResponse("ok", "Workshop Spring Boot activo"));
    }

    @Operation(summary = "Saludo por GET", description = "Recibe un nombre por query param, lo normaliza y devuelve un saludo. Si no se manda nombre, usa 'Mundo' por defecto.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Saludo generado correctamente"),
        @ApiResponse(responseCode = "400", description = "Error de negocio, por ejemplo si el nombre tiene numeros")
    })
    @Override
    public ResponseEntity<SaludoResponse> saludarPorGet(
            @Parameter(description = "Nombre de la persona a saludar", example = "Ana") String nombre) {
        return ResponseEntity.ok(saludoService.crearSaludo(nombre));
    }

    @Operation(summary = "Saludo por POST", description = "Igual que el GET pero recibe el nombre en el body como JSON. Aplica validacion con jakarta.validation.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Saludo generado correctamente"),
        @ApiResponse(responseCode = "400", description = "Validacion fallida, el nombre esta vacio o es muy largo")
    })
    @Override
    public ResponseEntity<SaludoResponse> saludarPorPost(SaludoRequest saludoRequest) {
        return ResponseEntity.ok(saludoService.crearSaludo(saludoRequest.nombre()));
    }
}
