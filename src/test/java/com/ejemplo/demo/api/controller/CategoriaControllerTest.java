package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.CategoriaRequest;
import com.ejemplo.demo.api.dto.CategoriaResponse;
import com.ejemplo.demo.domain.service.CategoriaService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoriaController.class)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService categoriaService;

    @Test
    @DisplayName("POST valido debe retornar 201")
    void postValidoDebeRetornar201() throws Exception {
        String body = """
                {
                  "nombre": "Bebidas",
                  "descripcion": "Gaseosas y jugos"
                }
                """;

        when(categoriaService.crear(any(CategoriaRequest.class))).thenReturn(
                new CategoriaResponse(1L, "Bebidas", "Gaseosas y jugos", LocalDateTime.now())
        );

        mockMvc.perform(post("/api/v1/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Bebidas"));
    }

    @Test
    @DisplayName("POST invalido debe retornar 400")
    void postInvalidoDebeRetornar400() throws Exception {
        String body = """
                {
                  "nombre": "",
                  "descripcion": "Sin nombre"
                }
                """;

        mockMvc.perform(post("/api/v1/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("VALIDATION_ERROR"));
    }

    @Test
    @DisplayName("GET por id inexistente debe retornar 404")
    void getPorIdInexistenteDebeRetornar404() throws Exception {
        when(categoriaService.obtenerPorId(999L))
                .thenThrow(new EntityNotFoundException("Categoria no encontrada con id: 999"));

        mockMvc.perform(get("/api/v1/categorias/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.codigo").value("RESOURCE_NOT_FOUND"));
    }
}
