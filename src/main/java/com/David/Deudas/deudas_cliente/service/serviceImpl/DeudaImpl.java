package com.David.Deudas.deudas_cliente.service.serviceImpl;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.David.Deudas.deudas_cliente.dto.DeudaRequestDto;
import com.David.Deudas.deudas_cliente.dto.DeudaResponseDto;
import com.David.Deudas.deudas_cliente.model.Cliente;
import com.David.Deudas.deudas_cliente.model.Deuda;
import com.David.Deudas.deudas_cliente.repository.ClienteRepository;
import com.David.Deudas.deudas_cliente.repository.DeudaRepository;
import com.David.Deudas.deudas_cliente.service.DeudaService;

@Service
public class DeudaImpl implements DeudaService {
    private final DeudaRepository deudaRepository;
    private final ClienteRepository clienteRepository;
    public DeudaImpl(DeudaRepository deudaRepository, ClienteRepository clienteRepository) {
        this.deudaRepository = deudaRepository;
        this.clienteRepository = clienteRepository;
    }



    @Override
    public List<DeudaResponseDto> listarTodasDeudas() {
        return deudaRepository.findAll()
                .stream()
                .map(this::mapToDeudaDto)
                .toList();
    }

    @Override
    public DeudaResponseDto crearDeuda(DeudaRequestDto deudaRequestDto) {
        Cliente encontrado = clienteRepository.findById(deudaRequestDto.clienteId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));

        Deuda creada = new Deuda();
        creada.setCliente(encontrado);
        creada.setConcepto(deudaRequestDto.concepto());
        creada.setCantidad(deudaRequestDto.cantidad());
        creada.setFecha(deudaRequestDto.fecha());
        deudaRepository.save(creada);
        return mapToDeudaDto(creada);
    }

    @Override
    public DeudaResponseDto modificarDeuda(UUID id, DeudaRequestDto deudaRequestDto) {

        Deuda encontradaDeuda = deudaRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deuda no encontrada"));


        encontradaDeuda.setConcepto(deudaRequestDto.concepto());

        encontradaDeuda.setCantidad(deudaRequestDto.cantidad());

        encontradaDeuda.setFecha(deudaRequestDto.fecha());

        deudaRepository.save(encontradaDeuda);
        return mapToDeudaDto(encontradaDeuda);
    }

    @Override
    public DeudaResponseDto modificarParcialDeuda(UUID id, DeudaRequestDto deudaRequestDto) {

        Deuda encontradaDeuda = deudaRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deuda no encontrada"));

        if(deudaRequestDto.concepto() != null){
            encontradaDeuda.setConcepto(deudaRequestDto.concepto());
        }
        if(deudaRequestDto.cantidad() != null){
            encontradaDeuda.setCantidad(deudaRequestDto.cantidad());
        }
        if(deudaRequestDto.fecha() != null){
            encontradaDeuda.setFecha(deudaRequestDto.fecha());
        }
        deudaRepository.save(encontradaDeuda);
        return mapToDeudaDto(encontradaDeuda);
    }

    @Override
    public DeudaResponseDto eliminarDeuda(UUID id) {
        Deuda eliminadaDeuda = deudaRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deuda no encontrado"));
        deudaRepository.delete(eliminadaDeuda);
        return mapToDeudaDto(eliminadaDeuda);
    }


    @Override
    public DeudaResponseDto descontarDeuda(UUID id, BigDecimal pagado) {
        Deuda encontrarDeuda = deudaRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deuda no encontrada"));

        if (pagado == null || pagado.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El importe pagado debe ser mayor que 0");
        }

        if (pagado.compareTo(encontrarDeuda.getCantidad()) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El pago no puede superar la deuda actual");
        }
        BigDecimal descontarDeuda = encontrarDeuda.getCantidad().subtract(pagado);
        encontrarDeuda.setCantidad(descontarDeuda);
        deudaRepository.save(encontrarDeuda);
        return mapToDeudaDto(encontrarDeuda);
    }

    @Override
    public List<DeudaResponseDto> listarDeudasClienteId(UUID idCliente) {
        Cliente clienteEncontrado = clienteRepository.findById(idCliente)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
       return deudaRepository.findByClienteId(clienteEncontrado.getId())
               .stream()
               .map(this::mapToDeudaDto)
               .toList();


    }

    @Override
    public List<DeudaResponseDto> listarDeudasPorFecha(LocalDate fecha) {
        return deudaRepository.findByFecha(fecha)
                .stream()
                .map(this::mapToDeudaDto)
                .toList();
    }

    @Override
    public List<DeudaResponseDto> ListarDeudasEntrefechaYClienteId(UUID clienteId, LocalDate inicio, LocalDate fin) {
        Cliente clienteEncontrado = clienteRepository.findById(clienteId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));

            return deudaRepository.findByFechaBetweenAndClienteId( inicio, fin, clienteEncontrado.getId())
                    .stream()
                    .map(this::mapToDeudaDto)
                    .toList();
    }

    @Override
    public List<DeudaResponseDto> ListarDeudasPorFechaYCliente(UUID clienteId, LocalDate fecha) {
        Cliente clienteEncontrado = clienteRepository.findById(clienteId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        return deudaRepository.findByClienteIdAndFecha(clienteEncontrado.getId(), fecha)
                .stream()
                .map(this::mapToDeudaDto)
                .toList();
    }

    private DeudaResponseDto mapToDeudaDto(Deuda deuda){
       return new DeudaResponseDto(
                deuda.getId(),
                deuda.getCliente().getNombre(),
                deuda.getCliente().getDni(),
                deuda.getConcepto(),
                deuda.getCantidad(),
                deuda.getFecha(),
                deuda.getCliente().getId()
        );
    }
}
