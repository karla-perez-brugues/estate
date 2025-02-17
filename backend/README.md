# Backend

This project is the backend for the application Estate.  
It contains the authentication and an API to retrieve the rentals, send a message and show the user account.

## Requirements

Make sure Java is installed and check the version with `java -version`. This application requires Java 21.

## Data base

Make sure mysql is installed with `mysql -V`.  
Create a new database named `estate`.  
Then, at the root of the project, create a file named `.env` with these 3 properties and fill them according to your configuration :
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `SECRET_KEY`

The different tables will be created automatically when the development server is started.

## Development server

Run `mvn spring-boot:run` for a dev server.  
The backend application is running on `http://localhost:3001`.

## Swagger

Now that the development server is running, you will find the API documentation [here](http://localhost:3001/swagger-ui/index.html).

## Dependencies

- `Spring Data JPA` Persist data in SQL stores with Java Persistence API using Spring Data and Hibernate.
- `OAuth2 Client` Spring Boot integration for Spring Security's OAuth2/OpenID Connect client features.
- `Spring Security` Highly customizable authentication and access-control framework for Spring applications.
- `Spring Web` Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container.
- `MySQL Driver` MySQL JDBC driver.
- `Model Mapper` Map objects by determining how one object model is mapped to another called a Data Transformation Object (DTO).
- `Swagger` Generate API documentation.

## How it works

### configuration

This package contains all the configuration classes.

### controller

This package contains all the Rest Controllers that expose the API.
- `request` : this package contains all the request DTOs.
- `response` : this package contains all the response DTOs.

### exception

This package contains all the exception classes.

### handler

This package contains the response handler that allows to format the responses sent to the frontend.

### model

This package contains all the JPA models.

### repository

This package contains all the CRUD repositories.

### service 

This package contains all the services used in the application with the dependency injection.