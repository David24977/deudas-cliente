package com.David.Deudas.deudas_cliente.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record DeudaResponseDto(UUID deudaId, String clienteNombre, String clienteDni, String deudaConcepto,
                               BigDecimal DeudaCantidad, LocalDate deudaFecha, UUID clienteId) {
}
