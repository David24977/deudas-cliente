package com.David.Deudas.deudas_cliente.service;

import com.David.Deudas.deudas_cliente.dto.ClienteRequestDto;
import com.David.Deudas.deudas_cliente.dto.ClienteResponseDto;
import com.David.Deudas.deudas_cliente.dto.ClienteTotalDeudaDto;

import java.util.List;
import java.util.UUID;

public interface ClienteService {
    List<ClienteResponseDto> listarTodosClientes();
    ClienteResponseDto crearCliente(ClienteRequestDto clienteRequestDto);
    ClienteResponseDto modificarCliente(UUID id, ClienteRequestDto clienteRequestDto);
    ClienteResponseDto modificarParcialCliente(UUID id, ClienteRequestDto clienteRequestDto);
    ClienteResponseDto eliminarCliente(UUID id);
    ClienteResponseDto encontrarPorNombre(String nombre);
    ClienteResponseDto encontrarPorDni(String dni);
    ClienteTotalDeudaDto deudaTotal(UUID id);
}
