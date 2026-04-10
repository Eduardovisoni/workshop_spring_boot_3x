package com.ejemplo.demo.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.ejemplo.demo.api.dto.SaludoResponse;
import com.ejemplo.demo.domain.service.SaludoService;

import com.ejemplo.demo.api.dto.SaludoRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Tag(name = "Saludos", description = "Endpoints para probar el flujo basico de la API")
@RestController
@RequestMapping("/api/v1")
public class SaludoController {

    @Operation(summary = "Health check", description = "Verifica que el servidor este corriendo. Util para saber si el proyecto levanto bien.")
    @ApiResponse(responseCode = "200", description = "El servidor esta activo")
    @GetMapping
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "estado", "ok",
                "mensaje", "Workshop Spring Boot activo"
        ));
    }


    private final SaludoService saludoService;

    public SaludoController(SaludoService saludoService) {
        this.saludoService = saludoService;
    }


    @Operation(summary = "Saludo por GET", description = "Recibe un nombre por query param, lo normaliza y devuelve un saludo. Si no se manda nombre, usa 'Mundo' por defecto.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Saludo generado correctamente"),
        @ApiResponse(responseCode = "400", description = "Error de negocio, por ejemplo si el nombre tiene numeros")
    })
    @GetMapping("/saludos")
    public ResponseEntity<SaludoResponse> saludar(
            @Parameter(description = "Nombre de la persona a saludar", example = "Ana")
            @RequestParam(defaultValue = "Mundo") String nombre
    ) {
        return ResponseEntity.ok(saludoService.crearSaludo(nombre));
    }


    @Operation(summary = "Saludo por POST", description = "Igual que el GET pero recibe el nombre en el body como JSON. Aplica validacion con jakarta.validation.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Saludo generado correctamente"),
        @ApiResponse(responseCode = "400", description = "Validacion fallida, el nombre esta vacio o es muy largo")
    })
    @PostMapping("/saludos")
    public ResponseEntity<SaludoResponse> saludarPost(@Valid @RequestBody SaludoRequest request) {
        return ResponseEntity.ok(saludoService.crearSaludo(request.nombre()));
    }
}
