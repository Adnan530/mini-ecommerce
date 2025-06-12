# Payment Service

A Spring Boot microservice that simulates the payment flow for orders. It listens to inventory update events, processes payments, and publishes payment status events to other services (e.g., Order Service).

## Features

- Listens to inventory update events via RabbitMQ
- Simulates payment processing
- Publishes payment processed events to a RabbitMQ queue
- Integrates with Redis for caching if needed
- Optionally integrates with BPMN (Camunda) for workflow orchestration

---

## Tech Stack

- **Java**: 17+
- **Spring Boot**: 3.1.0
- **Spring AMQP**: For RabbitMQ integration
- **Redis**: Caching layer
- **Lombok**: Reduces boilerplate code
- **SLF4J**: Logging abstraction
- **Spring Web**: RESTful API layer (if exposed)

## Architecture

+------------------+       +----------------------+       +------------------+
| Inventory System | ----> |   Payment Service    | ----> |   Order Service  |
| (Publishes Event)|       | - Payment Simulation |       | (Updates Status) |
|                  |       | - RabbitMQ Events    |       |                  |
+------------------+       +----------------------+       +------------------+


