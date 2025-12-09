package com.David.Deudas.deudas_cliente.repository;


import com.David.Deudas.deudas_cliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Optional<Cliente> findByNombre(String nombre);
    Optional<Cliente> findByDni(String dni);
}
