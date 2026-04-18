package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.ProductoRequest;
import com.ejemplo.demo.api.dto.ProductoResponse;
import com.ejemplo.demo.domain.service.ProductoService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Test
    @DisplayName("POST valido debe retornar 201")
    void postValidoDebeRetornar201() throws Exception {
        String body = """
                {
                  "nombre": "Coca Cola 500ml",
                  "precio": 12.50,
                  "stock": 100,
                  "categoriaId": 1
                }
                """;

        when(productoService.crear(any(ProductoRequest.class))).thenReturn(
                new ProductoResponse(1L, "Coca Cola 500ml", new BigDecimal("12.50"), 100, "Bebidas", LocalDateTime.now())
        );

        mockMvc.perform(post("/api/v1/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Coca Cola 500ml"))
                .andExpect(jsonPath("$.categoriaNombre").value("Bebidas"));
    }

    @Test
    @DisplayName("POST invalido debe retornar 400")
    void postInvalidoDebeRetornar400() throws Exception {
        String body = """
                {
                  "nombre": "",
                  "precio": -5,
                  "stock": 100,
                  "categoriaId": 1
                }
                """;

        mockMvc.perform(post("/api/v1/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("VALIDATION_ERROR"));
    }

    @Test
    @DisplayName("GET por id inexistente debe retornar 404")
    void getPorIdInexistenteDebeRetornar404() throws Exception {
        when(productoService.obtenerPorId(999L))
                .thenThrow(new EntityNotFoundException("Producto no encontrado con id: 999"));

        mockMvc.perform(get("/api/v1/productos/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.codigo").value("RESOURCE_NOT_FOUND"));
    }
}
