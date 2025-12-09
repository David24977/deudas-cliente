package com.David.Deudas.deudas_cliente.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ClienteTotalDeudaDto(UUID clienteId, String ClienteNombre, BigDecimal DeudaTotal, LocalDate fechaDeuda) {
}
