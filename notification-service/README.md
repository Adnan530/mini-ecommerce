# 📢 Notification Service

A Spring Boot microservice responsible for listening to system events (e.g., `PaymentProcessed`) via RabbitMQ and logging mock notifications. This service is designed to be part of an event-driven microservices architecture.
---
## Features

- Listens to RabbitMQ queues
- Handles `PaymentEvent` messages
- Logs mock notifications for successful and failed payments
- Easily extensible for email/SMS integrations

---

## Tech Stack

- **Java**: 17+
- **Spring Boot**: 3.1.0
- **RabbitMQ**: Message Broker
- **Lombok**: Logging and boilerplate removal
- **SLF4J**: Logging abstraction

## Architecture

+------------------+       +-------------------------+       +------------------------+
|  Order Service   | --->  |   RabbitMQ (Direct)     | --->  |  Notification Service  |
|  / Payment Svc   |       |  payment.processed.queue|       |   - Log mock alerts    |
+------------------+       +-------------------------+       +------------------------+


