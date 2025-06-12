# 🏷️ Inventory Service

A Spring Boot microservice responsible for listening to `OrderCreated` events via RabbitMQ, checking inventory availability, updating stock, and responding with appropriate `InventoryUpdateEvent` and `OrderStatusUpdateEvent`. This service is a core part of the event-driven microservices architecture.
---
## Features

- Listens to RabbitMQ for `OrderCreatedEvent`
- Validates and reserves inventory asynchronously
- Publishes:
    - `InventoryUpdateEvent`
    - `OrderStatusUpdateEvent` (if reservation succeeds)
- Easily extendable to handle restocking and low-inventory alerts

---

## Tech Stack

- **Java**: 17+
- **Spring Boot**: 3.1.0
- **RabbitMQ**: Message Broker
- **Spring Data JPA**: Data access layer
- **Lombok**: Logging and constructor injection
- **SLF4J**: Logging abstraction

## Architecture

+------------------+ +-------------------------+ +------------------------+
| Order Service | ---> | RabbitMQ (Direct) | ---> | Inventory Service |
| | | order.created.queue | | - Validate stock |
| | | | | - Emit stock events |
+------------------+ +-------------------------+ +------------------------+
|
v
+--------------------------+
| Notification / Order Svc |
+--------------------------+

