# tul
# Shopping Cart Services 

This sample application demonstrates a simple shopping cart.

The shopping cart service offers the following REST endpoints:

Swagger UI : http://localhost:8080/api/swagger-ui/index.html

Api doc: http://localhost:8080/api/v2/api-docs?group=api


For simplicity, no authentication is implemented, shopping cart IDs are  UUIDs to ensure uniqueness

## Getting Started

These instructions allow you to run a copy of the running project on your local machine for development and testing purposes.
### Prerequisites

- Java 8

### Installing

**Note:** Before compilation, you might configure your IDE with Lombok plugin

Compilation

```
./gradlew build
```

Running locally

```
./gradlew bootRun
```

**Note:** DB migrations will be run using Liquibase project and will be executed when the project start


## Running the test cases

Run the test cases execute

```
./gradlew test
```


## Build With

- SpringBoot
- Spring Framework (Core, MVC)
- Mockk
- JUnit 5 Jupiter
- Mockito
- Gradle 7.1.x

## Architecture
- DDD domain driven design

The distribution of the packages are based on DDD following following a structure like:

├── application

├── config

├── domain

└── infrastructure

- CQRS stands for Command Query Responsibility Segregation

Within the application package they are divided into commands and queries as proposed by CQRS


- Clean Architecture 

Each service is divided into use cases as proposed clean Architecture

## Author

- Gabriel Ortiz - (https://github.com/gaortiz1)
