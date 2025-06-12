# Mini Ecommerce Microservices Project

This project demonstrates a mini ecommerce architecture using
microservices with Spring Boot.

## Microservices

- **Product Service**: Manage products (CRUD, filtering, pagination)
- **Order Service**: Handle order creation, status updates, and order items
- **Inventory Service**: Updates stock based on orders
- **Payment Service**: Simulates payment processing and publishes notifications
- **Notification Service**: Sends mock notifications (e.g., logs) on
order/payment events

## Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- SQL
- RabbitMQ (for async communication)
- MapStruct (DTO mapping)
- Swagger/OpenAPI
- Redis (optional)
- JUnit + Mockito (testing)

## How to Run

Each service is a standalone Spring Boot application. You can run them
independently via your IDE or terminal.

**Prerequisites:**
- Java 17+
- Maven
- RabbitMQ
- Redis
- MySQL
- Docker

## Communication Overview

+------------------+         +----------------+          +-------------------+
|  Product Service |         |  Order Service |          | Notification      |
|  (CRUD, filtering|         |  (Order API,   |          | Service           |
|   with DB)       |         |   publishes    |          | (Listens to order |
|                  |         |   events)      |          | and payment       |
+------------------+         +--------+-------+          | events, logs)     |
                                    |                   +---------+---------+
                                    |                             |
                                    | publishes                   | subscribes to

                                    | "order created" events      | events
                                    v                             v
                            +----------------+          +-------------------+
                            | Inventory      |          | Payment Service   |
                            | Service        |          | (Payment flow &   |
                            | (Listens to    |          | Camunda workflow) |
                            | order events,  |          |                   |
                            | deducts stock, |          +-------------------+
                            | publishes      |
                            | stock update   |
                            | events)        |
                            +----------------+