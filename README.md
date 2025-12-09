# Deudas-Cliente API (Backend Spring Boot)

## Este proyecto implementa una API REST completa para la gestión de clientes y sus deudas asociadas. Está desarrollado con Spring Boot siguiendo buenas prácticas de arquitectura, validación, manejo de errores y separación por capas.

### La aplicación permite registrar clientes, crear deudas, modificarlas, realizar pagos parciales y consultar resúmenes. Está diseñada para ser un ejemplo profesional de un backend limpio, organizado y fácil de entender.

## Características principales

- Gestión de clientes: creación, actualización, búsqueda por nombre o DNI y eliminación.
- Gestión de deudas: creación, modificación completa o parcial (PATCH), eliminación y consultas avanzadas.
- Asociación Cliente ↔ Deuda con relación ManyToOne.
- Cálculo automático del total adeudado por cliente.
- Validación de datos de entrada mediante anotaciones Jakarta Validation.
- Respuestas estructuradas mediante DTOs para evitar exponer entidades.
- Manejo centralizado de errores con un GlobalExceptionHandler.
- Documentación automática con OpenAPI/Swagger.

## Arquitectura y organización

### El proyecto sigue una estructura clara:

- Controladores (Controller): exponen los endpoints REST.
- Servicios (Service y ServiceImpl): contienen la lógica de negocio.
- Repositorios (Repository): acceden a la base de datos mediante JPA.
- Modelos (Entity): representan las tablas Cliente y Deuda.
- DTOs: diferencian datos de entrada y datos de salida.
- Configuración: incluye OpenAPI y el manejador global de excepciones.

### Esta arquitectura facilita la escalabilidad y la reutilización del código.

## Modelo de datos

El sistema trabaja con dos entidades:

- Cliente: contiene id (UUID), nombre y DNI.
- Deuda: contiene id (UUID), concepto, cantidad, fecha y referencia al cliente.

Un cliente puede tener múltiples deudas, y cada deuda pertenece a un único cliente.

## Endpoints principales

## Clientes:
- POST /clientes → Crear un cliente
- GET /clientes → Listar todos los clientes
- GET /clientes/nombre?nombre=... → Buscar por nombre
- GET /clientes/dni?dni=... → Buscar por DNI
- PUT /clientes/{id} → Modificar cliente
- DELETE /clientes/{id} → Eliminar cliente
- GET /clientes/{id}/deudas → Listar deudas de un cliente
- GET /clientes/{id}/deuda-total → Calcular deuda total a fecha de hoy

## Deudas:
- POST /deudas → Crear una deuda asociada a un cliente
- GET /deudas → Listar todas las deudas
- GET /deudas?clienteId=... → Filtrar por cliente
- GET /deudas/fecha?fecha=... → Buscar deudas por fecha
- PUT /deudas/{id} → Modificar deuda
- PATCH /deudas/{id}/descuento → Pago parcial y recálculo de cantidad
- DELETE /deudas/{id} → Eliminar deuda

## Validaciones

### La API verifica obligatoriamente:
- Formato de nombre y DNI
- Tamaños máximos de texto
- Cantidades monetarias válidas
- Fechas correctas
- No permitir pagos mayores que la deuda actual
- No permitir campos vacíos en operaciones completas (PUT)

Además, el endpoint PATCH permite actualizaciones parciales, actualizando solo los campos enviados.

## DTOs empleados

Se utilizan request DTOs para recibir información del cliente usuario y response DTOs para devolver información ya procesada. Esto ofrece:

- Seguridad (no exponer entidades internas).
- Flexibilidad en los datos devueltos.
- Control total sobre el formato de respuesta.

## Manejo de errores

Todos los errores se gestionan mediante un GlobalExceptionHandler que devuelve una estructura clara con:

- Código HTTP
- Mensaje descriptivo

Esto mejora la experiencia del frontend y facilita el debugging.

## Base de datos

El proyecto utiliza MySQL con UUID como identificadores.
Spring JPA crea y actualiza automáticamente las tablas mediante hibernate.ddl-auto en modo "update".

Tecnologías utilizadas

- Java 21
- Spring Boot (Web, JPA, Validation)
- MySQL
- Lombok
- OpenAPI 3 (Swagger UI)
- Maven

## Objetivo del proyecto

Este backend está pensado como un ejemplo profesional para:

- Practicar relaciones entre entidades (ManyToOne).
- Implementar lógica realista de negocio (deudas, pagos, totales).
- Mostrar buenas prácticas para un portfolio de desarrollador backend.
- Servir como base para futuros proyectos que gestionen importes, usuarios o facturación.

## Maintainer: 
David Ferrer Sapiña
