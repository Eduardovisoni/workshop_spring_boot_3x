package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.PrestamoRequest;
import com.ejemplo.demo.api.dto.PrestamoResponse;
import com.ejemplo.demo.domain.service.PrestamoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PrestamoController.class)
class PrestamoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrestamoService prestamoService;

    @Test
    @DisplayName("Debe simular prestamo correctamente")
    void debeSimularPrestamoCorrectamente() throws Exception {
        String body = """
                {
                  "monto": 10000,
                  "tasaAnual": 12,
                  "meses": 12
                }
                """;

        when(prestamoService.simular(any(PrestamoRequest.class))).thenReturn(new PrestamoResponse(
                new BigDecimal("888.49"),
                new BigDecimal("661.88"),
                new BigDecimal("10661.88")
        ));

        mockMvc.perform(post("/api/v1/simulaciones/prestamo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cuotaMensual").value(888.49))
                .andExpect(jsonPath("$.interesTotal").value(661.88))
                .andExpect(jsonPath("$.totalPagar").value(10661.88));
    }

    @Test
    @DisplayName("Debe retornar 400 cuando el monto es invalido")
    void debeRetornar400CuandoMontoEsInvalido() throws Exception {
        String body = """
                {
                  "monto": 0,
                  "tasaAnual": 12,
                  "meses": 12
                }
                """;

        mockMvc.perform(post("/api/v1/simulaciones/prestamo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("VALIDATION_ERROR"));
    }
}