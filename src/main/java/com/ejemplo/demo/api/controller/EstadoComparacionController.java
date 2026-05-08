package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.EstadoResponse;
import com.ejemplo.demo.api.generated.DemoEstadoApi;
import com.ejemplo.demo.domain.model.EstadoManual;
import com.ejemplo.demo.domain.service.EstadoSingletonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "demo-estado", description = "Demuestra la diferencia entre Singleton (@Service) y objeto manual (new)")
@RestController
public class EstadoComparacionController implements DemoEstadoApi {

    private final EstadoSingletonService singletonService;

    public EstadoComparacionController(EstadoSingletonService singletonService) {
        this.singletonService = singletonService;
    }

    @Operation(summary = "Actualizar valor singleton", description = "Spring reutiliza la misma instancia: el valor persiste entre llamadas")
    @Override
    public ResponseEntity<EstadoResponse> actualizarSingleton(Integer valor) {
        return ResponseEntity.ok(new EstadoResponse("singleton", singletonService.actualizar(valor)));
    }

    @Operation(summary = "Leer valor singleton")
    @Override
    public ResponseEntity<EstadoResponse> obtenerSingleton() {
        return ResponseEntity.ok(new EstadoResponse("singleton", singletonService.obtenerActual()));
    }

    @Operation(summary = "Reiniciar valor singleton a 0")
    @Override
    public ResponseEntity<EstadoResponse> reiniciarSingleton() {
        return ResponseEntity.ok(new EstadoResponse("singleton", singletonService.reiniciar()));
    }

    @Operation(summary = "Actualizar valor manual", description = "Cada llamada crea un objeto nuevo con 'new': el valor nunca persiste")
    @Override
    public ResponseEntity<EstadoResponse> actualizarManual(Integer valor) {
        EstadoManual manual = new EstadoManual();
        manual.setValor(valor);
        return ResponseEntity.ok(new EstadoResponse("manual", manual.getValor()));
    }

    @Operation(summary = "Leer valor manual", description = "Siempre devuelve 0 porque crea una instancia nueva en cada request")
    @Override
    public ResponseEntity<EstadoResponse> obtenerManual() {
        EstadoManual manual = new EstadoManual();
        return ResponseEntity.ok(new EstadoResponse("manual", manual.getValor()));
    }
}
