package com.David.Deudas.deudas_cliente.service;

import com.David.Deudas.deudas_cliente.dto.DeudaRequestDto;
import com.David.Deudas.deudas_cliente.dto.DeudaResponseDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface DeudaService {
    List<DeudaResponseDto> listarTodasDeudas();
    DeudaResponseDto crearDeuda(DeudaRequestDto deudaRequestDto);
    DeudaResponseDto modificarDeuda(UUID id, DeudaRequestDto deudaRequestDto);
    DeudaResponseDto modificarParcialDeuda(UUID id, DeudaRequestDto deudaRequestDto);
    DeudaResponseDto eliminarDeuda(UUID id);

    DeudaResponseDto descontarDeuda(UUID id, BigDecimal pagado);

    List<DeudaResponseDto> listarDeudasClienteId(UUID clienteId);
    List<DeudaResponseDto> listarDeudasPorFecha(LocalDate fecha);
    List<DeudaResponseDto> ListarDeudasEntrefechaYClienteId(UUID clienteId, LocalDate inicio, LocalDate fin);
    List<DeudaResponseDto> ListarDeudasPorFechaYCliente(UUID clienteId, LocalDate fecha);
}
