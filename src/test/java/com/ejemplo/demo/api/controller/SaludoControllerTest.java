package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.SaludoResponse;

import com.ejemplo.demo.domain.service.SaludoService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.time.Instant;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.ejemplo.demo.api.controller.SaludoController;

@WebMvcTest(SaludoController.class)
class SaludoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private SaludoService saludoService;

    @Test
    @DisplayName("Debe responder health del workshop")
    void debeResponderHealthDelWorkshop() throws Exception {
        mockMvc.perform(get("/api/v1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("ok"));
    }

    @Test
    @DisplayName("Debe responder saludo con nombre")
    void debeResponderSaludo() throws Exception {

        when(saludoService.crearSaludo("Ana"))
                .thenReturn(new SaludoResponse(
                        "Hola, Estudiante Ana. Bienvenido a Spring Boot 3!",
                        Instant.now()
                ));

        mockMvc.perform(get("/api/v1/saludos")
                        .param("nombre", "Ana"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Hola, Estudiante Ana. Bienvenido a Spring Boot 3!"));
    }
    
    @Test
    @DisplayName("Debe fallar validacion en POST")
    void debeFallarValidacionEnPost() throws Exception {
        String body = """
                {
                  "nombre": ""
                }
                """;

        mockMvc.perform(post("/api/v1/saludos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("VALIDATION_ERROR"));
    }
    
    
}
