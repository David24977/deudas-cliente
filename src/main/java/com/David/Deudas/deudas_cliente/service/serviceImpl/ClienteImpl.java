package com.David.Deudas.deudas_cliente.service.serviceImpl;

import com.David.Deudas.deudas_cliente.dto.ClienteRequestDto;
import com.David.Deudas.deudas_cliente.dto.ClienteResponseDto;
import com.David.Deudas.deudas_cliente.dto.ClienteTotalDeudaDto;
import com.David.Deudas.deudas_cliente.model.Cliente;
import com.David.Deudas.deudas_cliente.model.Deuda;
import com.David.Deudas.deudas_cliente.repository.ClienteRepository;
import com.David.Deudas.deudas_cliente.repository.DeudaRepository;
import com.David.Deudas.deudas_cliente.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ClienteImpl implements ClienteService {
    private final ClienteRepository clienteRepository;
    private final DeudaRepository deudaRepository;

    public ClienteImpl(ClienteRepository clienteRepository, DeudaRepository deudaRepository) {
        this.clienteRepository = clienteRepository;
        this.deudaRepository = deudaRepository;
    }

    @Override
    public List<ClienteResponseDto> listarTodosClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public ClienteResponseDto crearCliente(ClienteRequestDto clienteRequestDto) {
        Cliente nuevo = new Cliente();
        nuevo.setNombre(clienteRequestDto.nombre());
        nuevo.setDni(clienteRequestDto.dni());
        clienteRepository.save(nuevo);
        return mapToResponseDto(nuevo);

    }

    @Override
    public ClienteResponseDto modificarCliente(UUID id, ClienteRequestDto clienteRequestDto) {
        Cliente modificado = clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        modificado.setNombre(clienteRequestDto.nombre());
        modificado.setDni(clienteRequestDto.dni());
        clienteRepository.save(modificado);
        return mapToResponseDto(modificado);

    }
    @Override
    public ClienteResponseDto modificarParcialCliente(UUID id, ClienteRequestDto clienteRequestDto) {
        Cliente modificarCliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));

            if (clienteRequestDto.nombre() != null) {
                modificarCliente.setNombre(clienteRequestDto.nombre());
            }

            if (clienteRequestDto.dni() != null) {
                modificarCliente.setDni(clienteRequestDto.dni());
            }
            clienteRepository.save(modificarCliente);
            return mapToResponseDto(modificarCliente);
        }


    @Override
    public ClienteResponseDto eliminarCliente(UUID id) {
        Cliente eliminado = clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        clienteRepository.delete(eliminado);
        return mapToResponseDto(eliminado);
    }

    @Override
    public ClienteResponseDto encontrarPorNombre(String nombre) {
        Cliente encontradoNombre = clienteRepository.findByNombre(nombre)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        return mapToResponseDto(encontradoNombre);
    }

    @Override
    public ClienteResponseDto encontrarPorDni(String dni) {
        Cliente encontradoDni = clienteRepository.findByDni(dni)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        return mapToResponseDto(encontradoDni);
    }

    @Override
    public ClienteTotalDeudaDto deudaTotal(UUID id) {
        Cliente encontrado = clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));


        BigDecimal deudaTotal = deudaRepository.findByClienteId(encontrado.getId())
                .stream()
                .map(Deuda::getCantidad)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ClienteTotalDeudaDto(
                encontrado.getId(),
                encontrado.getNombre(),
                deudaTotal,
                LocalDate.now()
        );

    }


    private ClienteResponseDto mapToResponseDto(Cliente cliente) {
        return new ClienteResponseDto(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getDni()
        );
    }
}
