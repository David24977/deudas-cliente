package com.David.Deudas.deudas_cliente.dto;

import java.util.UUID;

public record ClienteResponseDto(UUID clienteId, String clienteNombre, String clienteDni) {
}
