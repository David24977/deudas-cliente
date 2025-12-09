package com.David.Deudas.deudas_cliente.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.David.Deudas.deudas_cliente.dto.DeudaRequestDto;
import com.David.Deudas.deudas_cliente.dto.DeudaResponseDto;
import com.David.Deudas.deudas_cliente.dto.PagoRequestDto;
import com.David.Deudas.deudas_cliente.service.DeudaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/deudas")
public class DeudaController {
    private final DeudaService deudaService;

    public DeudaController(DeudaService deudaService) {
        this.deudaService = deudaService;
    }

    @GetMapping
    public List<DeudaResponseDto> listarTodasDeudas(){
        return deudaService.listarTodasDeudas();
    }

    @PostMapping
    public DeudaResponseDto crearDeuda(@Valid @RequestBody DeudaRequestDto deudaRequestDto){
        return deudaService.crearDeuda(deudaRequestDto);
    }

    @PutMapping("/{id}")
    public DeudaResponseDto modificarDeuda(@PathVariable UUID id, @Valid @RequestBody DeudaRequestDto deudaRequestDto){
        return  deudaService.modificarDeuda(id, deudaRequestDto);
    }

    @PatchMapping("/{id}")
    public DeudaResponseDto modificarParcialDeuda(@PathVariable UUID id, @RequestBody DeudaRequestDto deudaRequestDto){
        return  deudaService.modificarParcialDeuda(id, deudaRequestDto);
    }

    @DeleteMapping("/{id}")
    public DeudaResponseDto eliminarDeuda(@PathVariable UUID id){
        return deudaService.eliminarDeuda(id);
    }

    //Desde el módulo deudas
    @GetMapping("clientes/{idCliente}")
    public List<DeudaResponseDto> listarDeudasClienteId(@PathVariable UUID idCliente){
        return deudaService.listarDeudasClienteId(idCliente);
    }

    @GetMapping("/fecha")
    public List<DeudaResponseDto> listarDeudasPorFecha(@RequestParam LocalDate fecha){
        return deudaService.listarDeudasPorFecha(fecha);
    }

    @GetMapping("/entre-fechas")
    public List<DeudaResponseDto> ListarDeudasEntrefechaYClienteId(@RequestParam UUID clienteId,
                                                            @RequestParam LocalDate inicio, @RequestParam LocalDate fin){
        return deudaService.ListarDeudasEntrefechaYClienteId( clienteId,  inicio,  fin);
    }

    @GetMapping("/fecha-cliente")
    public List<DeudaResponseDto> ListarDeudasPorFechaYCliente(@RequestParam UUID clienteId, @RequestParam LocalDate fecha){
        return deudaService.ListarDeudasPorFechaYCliente(clienteId, fecha);
    }

    @PatchMapping("/{id}/descuento-deuda")
    public DeudaResponseDto descontarDeuda(@PathVariable UUID id, @RequestBody PagoRequestDto pagoRequestDto){
        return deudaService.descontarDeuda(id, pagoRequestDto.pagoDeuda());
    }

}
