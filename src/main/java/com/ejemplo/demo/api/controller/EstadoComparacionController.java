package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.domain.model.EstadoManual;
import com.ejemplo.demo.domain.service.EstadoSingletonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Demo Singleton", description = "Demuestra la diferencia entre Singleton (@Service) y objeto manual (new)")
@RestController
@RequestMapping("/api/v1/demo/estado")
public class EstadoComparacionController {

    private final EstadoSingletonService singletonService;

    public EstadoComparacionController(EstadoSingletonService singletonService) {
        this.singletonService = singletonService;
    }

    @Operation(summary = "Actualizar valor singleton", description = "Spring reutiliza la misma instancia: el valor persiste entre llamadas")
    @PostMapping("/singleton/{valor}")
    public Map<String, Object> actualizarSingleton(@PathVariable int valor) {
        return Map.of("tipo", "singleton", "valorActual", singletonService.actualizar(valor));
    }

    @Operation(summary = "Leer valor singleton")
    @GetMapping("/singleton")
    public Map<String, Object> leerSingleton() {
        return Map.of("tipo", "singleton", "valorActual", singletonService.obtenerActual());
    }

    @Operation(summary = "Reiniciar valor singleton a 0")
    @PostMapping("/singleton/reset")
    public Map<String, Object> reiniciarSingleton() {
        return Map.of("tipo", "singleton", "valorActual", singletonService.reiniciar());
    }

    @Operation(summary = "Actualizar valor manual", description = "Cada llamada crea un objeto nuevo con 'new': el valor nunca persiste")
    @PostMapping("/manual/{valor}")
    public Map<String, Object> actualizarManual(@PathVariable int valor) {
        EstadoManual manual = new EstadoManual();
        manual.setValor(valor);
        return Map.of("tipo", "manual", "valorActual", manual.getValor());
    }

    @Operation(summary = "Leer valor manual", description = "Siempre devuelve 0 porque crea una instancia nueva en cada request")
    @GetMapping("/manual")
    public Map<String, Object> leerManual() {
        EstadoManual manual = new EstadoManual();
        return Map.of("tipo", "manual", "valorActual", manual.getValor());
    }
}
