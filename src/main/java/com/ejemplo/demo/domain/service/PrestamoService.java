package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.api.dto.PrestamoRequest;
import com.ejemplo.demo.api.dto.PrestamoResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class PrestamoService {

    private static final MathContext MC = new MathContext(15, RoundingMode.HALF_UP);
    private static final BigDecimal CIEN = new BigDecimal("100");
    private static final BigDecimal DOCE = new BigDecimal("12");

    public PrestamoResponse simular(PrestamoRequest request) {
        BigDecimal monto = request.monto();
        BigDecimal tasaAnual = request.tasaAnual();
        int meses = request.meses();

        if (tasaAnual.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("La tasaAnual no puede ser mayor al 100%");
        }
        if (monto.compareTo(BigDecimal.valueOf(5_000_000)) > 0) {
            throw new IllegalArgumentException("El monto excede el maximo permitido de Q5,000,000");
        }

        BigDecimal tasaMensual = tasaAnual.divide(DOCE, 10, RoundingMode.HALF_UP)
                .divide(CIEN, 10, RoundingMode.HALF_UP);

        if (tasaMensual.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La tasa mensual debe ser mayor que 0");
        }

        BigDecimal unoMasR = BigDecimal.ONE.add(tasaMensual, MC);
        BigDecimal potencia = unoMasR.pow(meses, MC);

        BigDecimal numerador = monto.multiply(tasaMensual.multiply(potencia, MC), MC);
        BigDecimal denominador = potencia.subtract(BigDecimal.ONE, MC);

        BigDecimal cuotaMensual = numerador.divide(denominador, 2, RoundingMode.HALF_UP);
        BigDecimal totalPagar = cuotaMensual.multiply(BigDecimal.valueOf(meses)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal interesTotal = totalPagar.subtract(monto).setScale(2, RoundingMode.HALF_UP);

        return new PrestamoResponse(
                cuotaMensual,
                interesTotal,
                totalPagar
        );
    }
}