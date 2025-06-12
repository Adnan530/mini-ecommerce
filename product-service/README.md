# Product Service

A Spring Boot microservice for managing product data, offering full CRUD operations with pagination and dynamic filtering. It uses Spring Data JPA with SQL Server and applies the Specification pattern for flexible querying.

---
Swagger UI Link : http://localhost:8085/swagger-ui/index.html
---

## Features

- CRUD endpoints for managing products
- Pagination and sorting support
- Dynamic filtering via Specification pattern
- SQL Server integration using Spring Data JPA
- Caching and soft delete support (if needed)

---

## Tech Stack

- **Java**: 17+
- **Spring Boot**: 3.1.0
- **Spring Data JPA**: ORM for SQL Server
- **SQL Server**: Relational database
- **Lombok**: Reduces boilerplate code
- **SLF4J**: Logging abstraction
- **Spring Web**: RESTful API layer

## Architecture

+------------------+ +--------------------------+ +-----------------------+
| Admin / CMS UI | <---> | Product Service | <---> | SQL |
| | | - CRUD APIs | | Product Inventory DB |
| | | - Pagination & Filters | +-----------------------+
+------------------+ +--------------------------+

