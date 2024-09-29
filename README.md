# Beauty Manager API

## Overview

Beauty Manager is a functional programming-based API built with Kotlin and Spring Boot for managing appointments in a beauty salon. The API allows users to create, read, update, and delete appointments, as well as view available time slots.

## Technologies

- Kotlin 
- Java 21
- Spring Boot 3.3.4
- MongoDB
- Arrow-kt (Functional companion to Kotlin)
- Spring Doc OpenAPI (Swagger UI)
- Docker & Docker Compose
- TestContainers for integration testing
- CQRS concepts

## Features

- REST API for managing salon appointments
- Functional programming approach using Arrow-kt
- MongoDB for data persistence
- Swagger UI for API documentation and testing
- Dockerized application and database for easy deployment
- Comprehensive test suite using JUnit 5 and TestContainers

## Getting Started

### Prerequisites

- JDK 21
- Maven
- Docker and Docker Compose

### Running the Application

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/beautymanager.git
   cd beautymanager
   ```

2. Build the application:
   ```
   mvn clean package
   ```

3. Start the application and MongoDB using Docker Compose:
   ```
   docker-compose up --build
   ```

The application will be available at `http://localhost:8080`.

### API Documentation

Once the application is running, you can access the Swagger UI documentation at:

```
http://localhost:8080/swagger-ui.html
```

## Development

### Project Structure

- `src/main/kotlin/` - Kotlin source files
- `src/test/kotlin/` - Kotlin test files
- `docker/` - Docker-related files 
- `pom.xml` - Maven configuration file



### Testing

Run the tests using:

```
mvn test
```

This project uses TestContainers for integration tests, ensuring that tests run against a real MongoDB instance.



## License

This project is open-source and available under the [MIT License](LICENSE).

