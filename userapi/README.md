This project is a RESTful API developed with Java 17 and Spring Boot, designed to manage users while implementing security, validation, and containerization best practices.

The API supports full CRUD operations, user authentication, AES-256 password encryption, input validation (RFC and phone format), and automatic API documentation using OpenAPI/Swagger. The application also enforces unique tax identifiers and stores timestamps using the Madagascar time zone (Indian/Antananarivo), as required by the specification.

The architecture follows a layered design (Controller → Service), ensuring separation of concerns and maintainability. Unit tests were implemented to validate business logic, and the application is fully containerized using Docker for consistent deployment and portability.

Key technical highlights:

Java 17 + Spring Boot

RESTful architecture

AES-256 password encryption

Input validation using regex

UUID-based identifiers

Proper HTTP status handling

OpenAPI/Swagger documentation

Unit testing with Maven

Dockerized deployment

The application can be executed either locally using Maven or via Docker, requiring no additional configuration.

This solution demonstrates backend development fundamentals, security awareness, clean code structure, and deployable packaging suitable for modern development environments.

---------------------Installation & Execution---------------------
Prerequisites (Local execution)

Java 17

Maven 3.9+

Port 8080 available

Run locally
mvn clean package
mvn spring-boot:run


Or:

java -jar target/userapi-0.0.1-SNAPSHOT.jar


Application will start at:

http://localhost:8080

---------------------Docker Support---------------------

This project includes a multi-stage Docker configuration.

Build image
docker build -t userapi .

Run container
docker run -p 8080:8080 userapi


API available at:

http://localhost:8080


Swagger UI:

http://localhost:8080/swagger-ui.html

---------------------Available Endpoints---------------------
Method	Endpoint	Description
POST	/users	Create new user
GET	/users	Retrieve all users
GET	/users/{id}	Retrieve user by ID
PATCH	/users/{id}	Partially update user
DELETE	/users/{id}	Delete user
POST	/login	Authenticate user

---------------------Security Considerations---------------------

Passwords are encrypted using AES-256 before storage.

The password field is write-only and not exposed in responses.

RFC (taxId) must be unique.

Input validation implemented using regular expressions.

Proper HTTP status codes are returned for validation and authentication errors.

---------------------Running Unit Tests---------------------
mvn test