# Ecommerce Application

## Description
Ecommerce Application is a sample web application for managing an online store, developed using Spring Boot. The application demonstrates core principles of developing RESTful services, working with a database, and automated testing.

## Key Features
- Create, read, update, and delete products (CRUD)
- Manage orders and users
- Integration with a MySQL database
- API documentation with Swagger
- Unit and integration tests using JUnit and Mockito

## Technologies Used
- Java 17
- Spring Boot 2.6
- Spring Data JPA
- MySQL
- Swagger
- JUnit
- Mockito

## Installation and Running
1. Clone the repository:
   ```bash
   git clone https://github.com/isverbitskiy/ecommerce.git
   ```
2.	Navigate to the project directory:
    ```bash
    cd ecommerce
    ```
3.	Configure the database in src/main/resources/application.properties.
4.	Run the application:
    ```bash
    ./mvnw spring-boot:run
    ```
## API Documentation

API documentation is available at: http://localhost:8080/swagger-ui.html

## Testing

To run tests, use:
  ```bash
  ./mvnw test
  ```

## Author
Igor Verbitskiy
