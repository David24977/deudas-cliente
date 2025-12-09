package com.David.Deudas.deudas_cliente.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record DeudaRequestDto(
        @Valid
        @NotBlank
        @Size(min = 3, max = 100)
        String concepto,
        @Valid
        @NotNull
        BigDecimal cantidad,

        @NotNull
        LocalDate fecha,

        @NotNull
        UUID clienteId
) {
}
