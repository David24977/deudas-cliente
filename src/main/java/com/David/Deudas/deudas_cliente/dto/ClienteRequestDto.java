package com.David.Deudas.deudas_cliente.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteRequestDto(
        @NotBlank
        @Valid
        @Size(min = 3, max = 30)
        String nombre,
        @NotBlank
        @Valid
        @Size(max = 9)
        String dni) {
}
