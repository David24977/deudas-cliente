package com.David.Deudas.deudas_cliente.controller;

import com.David.Deudas.deudas_cliente.dto.ClienteRequestDto;
import com.David.Deudas.deudas_cliente.dto.ClienteResponseDto;
import com.David.Deudas.deudas_cliente.dto.ClienteTotalDeudaDto;
import com.David.Deudas.deudas_cliente.dto.DeudaResponseDto;
import com.David.Deudas.deudas_cliente.service.ClienteService;
import com.David.Deudas.deudas_cliente.service.DeudaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;
    private final DeudaService deudaService;

    public ClienteController(ClienteService clienteService, DeudaService deudaService) {
        this.clienteService = clienteService;
        this.deudaService = deudaService;
    }

    @GetMapping
    public List<ClienteResponseDto> listarTodosClientes(){
        return clienteService.listarTodosClientes();
    }

    @PostMapping
    public ClienteResponseDto crearCliente(@Valid @RequestBody ClienteRequestDto clienteRequestDto){
        return clienteService.crearCliente(clienteRequestDto);
    }

    @PutMapping("/{id}")
    public ClienteResponseDto modificarCliente(@PathVariable UUID id, @Valid @RequestBody ClienteRequestDto clienteRequestDto){
        return clienteService.modificarCliente(id, clienteRequestDto);
    }

    @PatchMapping("/{id}")
    public ClienteResponseDto modificarParcialCliente(@PathVariable UUID id, @RequestBody ClienteRequestDto clienteRequestDto){
        return clienteService.modificarParcialCliente(id, clienteRequestDto);
    }

    @DeleteMapping("/{id}")
    public ClienteResponseDto eliminarCliente(@PathVariable UUID id){
        return clienteService.eliminarCliente(id);
    }

    @GetMapping("/nombre")
    public ClienteResponseDto encontrarPorNombre(@RequestParam String nombre){
        return clienteService.encontrarPorNombre(nombre);
    }

    @GetMapping("/dni")
    public ClienteResponseDto encontrarPorDni(@RequestParam String dni){
        return clienteService.encontrarPorDni(dni);

    }

    //Mostrar deudas del cliente
    @GetMapping("/{id}/deudas")
    public List<DeudaResponseDto> ClienteIdListarDeudas(@PathVariable UUID id){
        return deudaService.listarDeudasClienteId(id);
    }

    //Mostrar deuda total cliente(Suma de sus deudas)
    @GetMapping("/{id}/deuda-total")
    public ClienteTotalDeudaDto deudaTotal(@PathVariable UUID id){
        return clienteService.deudaTotal(id);
    }

}
