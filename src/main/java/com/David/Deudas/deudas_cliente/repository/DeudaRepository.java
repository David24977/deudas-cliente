package com.David.Deudas.deudas_cliente.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.David.Deudas.deudas_cliente.model.Deuda;

public interface DeudaRepository extends JpaRepository<Deuda, UUID> {
   //Sirve tanto para el módulo cliente, como para el módulo deuda:
    //GET /clientes/123/deudas o GET /deudas?clienteId=123
    List<Deuda>findByClienteId(UUID clienteId);
    List<Deuda>findByFecha(LocalDate fecha);
    List<Deuda>findByFechaBetweenAndClienteId(LocalDate inicio, LocalDate fin, UUID clienteId);
    List<Deuda> findByClienteIdAndFecha(UUID clienteId, LocalDate fecha);

}
