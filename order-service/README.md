# 📦 Order Service

A Spring Boot microservice that provides a REST API to place and retrieve orders. It publishes `OrderCreatedEvent` to RabbitMQ and listens for `OrderStatusUpdateEvent` to keep order status in sync across services.

---
Swagger UI Link : http://localhost:8081/swagger-ui/index.html
---

## Features

- REST endpoints to:
    - Create orders
    - View order details
- Publishes `OrderCreatedEvent` to RabbitMQ
- Listens for `OrderStatusUpdateEvent` to update order status
- Automatically persists order changes with timestamps

---

## Tech Stack

- **Java**: 17+
- **Spring Boot**: 3.1.0
- **RabbitMQ**: Message Broker
- **Spring Data JPA**: ORM for PostgreSQL/MySQL
- **Lombok**: Boilerplate reduction
- **SLF4J**: Logging abstraction
- **Spring Web**: For REST APIs

## Architecture

+---------------+ +-------------------------+ +------------------------+
| API Clients | -----> | Order Service | -----> | RabbitMQ |
| | | - Create Order (POST) | | order.created.queue |
| | | - View Orders (GET) | +------------------------+
| | | | |
| | | <---- status updates ---|<-----------------+
+---------------+ | (via RabbitMQ) |
+-------------------------+