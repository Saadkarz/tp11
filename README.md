# Spring Data REST - Banking Application API

A RESTful API for managing bank accounts and clients using Spring Boot and Spring Data REST.

**Author:** Saad Karzouz

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Testing with Postman](#testing-with-postman)
- [Database Access](#database-access)
- [Projections](#projections)
- [Custom Queries](#custom-queries)
- [Troubleshooting](#troubleshooting)

## 🎯 Overview

This project is a Spring Boot application that demonstrates the use of Spring Data REST to automatically expose JPA repositories as RESTful web services. It manages a simple banking system with clients and their associated accounts.

## ✨ Features

- **Automatic REST API Generation**: Repositories are automatically exposed as REST endpoints
- **HATEOAS Support**: Hypermedia links for easy navigation between resources
- **Entity Relationships**: One-to-Many relationship between Client and Compte (Account)
- **Custom Projections**: Filter response data based on specific needs
- **Custom Search Methods**: Search accounts by type (EPARGNE/COURANT)
- **ID Exposure**: Entity IDs are included in JSON responses
- **In-Memory Database**: H2 database for quick testing and development
- **Automatic Data Initialization**: Sample data loaded on startup

## 🛠 Technologies Used

- **Java 11**
- **Spring Boot 2.7.18**
- **Spring Data JPA**
- **Spring Data REST**
- **H2 Database** (In-Memory)
- **Lombok** (Reduce boilerplate code)
- **Maven** (Build tool)
- **Jackson** (XML support)

## 📁 Project Structure

```
src/main/java/ma/rest/spring/
├── Application.java                          # Main application class
├── controllers/
│   └── CompteController.java                 # Manual REST controller (alternative)
├── entities/
│   ├── Client.java                           # Client entity
│   ├── Compte.java                           # Account entity
│   ├── TypeCompte.java                       # Account type enum
│   ├── CompteProjection1.java                # Projection for account balance
│   ├── CompteProjection2.java                # Projection for mobile view
│   └── ClientProjection.java                 # Projection for client details
└── repositories/
    ├── ClientRepository.java                 # Client repository
    └── CompteRepository.java                 # Account repository

src/main/resources/
└── application.properties                    # Application configuration
```

## 📦 Installation

### Prerequisites

- Java JDK 11 or higher
- Maven 3.6+ (or use the included Maven wrapper)
- Git (optional)

### Steps

1. **Clone or download the project**
   ```bash
   git clone <repository-url>
   cd tp11
   ```

2. **Build the project**
   ```bash
   .\mvnw.cmd clean install
   ```

## 🚀 Running the Application

### Option 1: Using Maven Spring Boot Plugin
```bash
.\mvnw.cmd spring-boot:run
```

### Option 2: Using the JAR file
```bash
.\mvnw.cmd clean package -DskipTests
java -jar target\spring-0.0.1-SNAPSHOT.jar
```

The application will start on **http://localhost:8082**

## 📚 API Documentation

### Base URL
```
http://localhost:8082/api
```

### Entities

#### Client
- **id**: Long (auto-generated)
- **nom**: String (name)
- **email**: String
- **comptes**: List of Compte (accounts)

#### Compte (Account)
- **id**: Long (auto-generated)
- **solde**: Double (balance)
- **dateCreation**: Date (creation date)
- **type**: TypeCompte (EPARGNE or COURANT)
- **client**: Client (owner)

### Endpoints

#### 1. Clients

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/clients` | Get all clients |
| GET | `/api/clients/{id}` | Get client by ID |
| POST | `/api/clients` | Create a new client |
| PUT | `/api/clients/{id}` | Update a client |
| PATCH | `/api/clients/{id}` | Partial update |
| DELETE | `/api/clients/{id}` | Delete a client |

#### 2. Comptes (Accounts)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/comptes` | Get all accounts |
| GET | `/api/comptes/{id}` | Get account by ID |
| POST | `/api/comptes` | Create a new account |
| PUT | `/api/comptes/{id}` | Update an account |
| PATCH | `/api/comptes/{id}` | Partial update |
| DELETE | `/api/comptes/{id}` | Delete an account |

#### 3. Relationships

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/clients/{id}/comptes` | Get all accounts for a client |
| GET | `/api/comptes/{id}/client` | Get the client of an account |

#### 4. Custom Search

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/comptes/search/byType?t=EPARGNE` | Find accounts by type |
| GET | `/api/comptes/search/byType?t=COURANT` | Find accounts by type |

## 🧪 Testing with Postman

### Example Requests

<img width="1906" height="1134" alt="Screenshot 2025-11-04 155510" src="https://github.com/user-attachments/assets/b931a636-4481-4286-a13d-9bb867792bab" />


<img width="1605" height="1006" alt="Screenshot 2025-11-04 151204" src="https://github.com/user-attachments/assets/97ce5afd-44e1-45b8-a0c1-f5cde0b67e7b" />


<img width="1618" height="1011" alt="Screenshot 2025-11-04 151146" src="https://github.com/user-attachments/assets/d22da3f6-f66c-483a-8e21-4b754d90bf1b" />


<img width="1787" height="1069" alt="Screenshot 2025-11-04 151140" src="https://github.com/user-attachments/assets/736ecb99-b90f-41fe-a7a8-aa79fb286bba" />


#### Get All Accounts
```
GET http://localhost:8082/api/comptes
```

#### Get Account with Projection
```
GET http://localhost:8082/api/comptes/1?projection=mobile
```

Response:
```json
{
  "solde": 5432.10,
  "type": "EPARGNE"
}
```

#### Get Client's Accounts
```
GET http://localhost:8082/api/clients/1/comptes
```

#### Search by Account Type
```
GET http://localhost:8082/api/comptes/search/byType?t=EPARGNE
```

#### Create New Client
```
POST http://localhost:8082/api/clients
Content-Type: application/json

{
  "nom": "John Doe",
  "email": "john.doe@example.com"
}
```

#### Create New Account
```
POST http://localhost:8082/api/comptes
Content-Type: application/json

{
  "solde": 5000,
  "dateCreation": "2025-11-04",
  "type": "COURANT",
  "client": "http://localhost:8082/api/clients/1"
}
```

## 💾 Database Access

### H2 Console

The H2 database console is available at:
```
http://localhost:8082/h2-console
```

**Connection Settings:**
- **JDBC URL**: `jdbc:h2:mem:banque`
- **Username**: `sa`
- **Password**: (leave empty)
- **Driver Class**: `org.h2.Driver`

## 🎭 Projections

### Available Projections

#### For Compte (Account)

1. **solde** - Shows only the balance
   ```
   GET /api/comptes/1?projection=solde
   ```

2. **mobile** - Shows balance and type (for mobile apps)
   ```
   GET /api/comptes/1?projection=mobile
   ```

#### For Client

1. **clientDetails** - Shows only name and email
   ```
   GET /api/clients/1?projection=clientDetails
   ```

### Using Projections in Relationships
```
GET /api/comptes/1/client?projection=clientDetails
```

## 🔍 Custom Queries

### Search by Account Type

The application includes a custom search method to filter accounts by type:

```
GET /api/comptes/search/byType?t=EPARGNE
GET /api/comptes/search/byType?t=COURANT
```

This demonstrates how to add custom search methods using `@RestResource`.

## 🔧 Troubleshooting

### 404 Not Found Error

If you get a 404 error when accessing endpoints:
1. Verify the application is running: `netstat -ano | findstr :8082`
2. Ensure you're using the correct base path: `/api`
3. Check that Spring Data REST dependency is in `pom.xml`
4. Verify repositories are annotated with `@RepositoryRestResource`

### LazyInitializationException

If you encounter this error:
- Ensure collections in entities use `@ToString(exclude = "collectionName")`
- The `Client` entity excludes the `comptes` collection from `toString()`

### Port Already in Use

If port 8082 is already in use:
1. Stop the conflicting process
2. Or change the port in `application.properties`:
   ```properties
   server.port=8083
   ```

### Build Issues

If Maven build fails:
1. Clean the project: `.\mvnw.cmd clean`
2. Delete the `target` folder
3. Rebuild: `.\mvnw.cmd clean install`

## 📝 Configuration

### application.properties

```properties
# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:banque
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Server Configuration
server.port=8082

# Spring Data REST Configuration
spring.data.rest.base-path=/api
```

## 📖 Learning Objectives

This project demonstrates:
- Spring Data REST automatic REST API generation
- JPA entity relationships (One-to-Many, Many-to-One)
- Custom projections for flexible API responses
- HATEOAS for discoverable REST APIs
- Custom repository methods with `@RestResource`
- H2 in-memory database usage
- Data initialization with `CommandLineRunner`

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

## 📄 License

This project is created for educational purposes.

## 👤 Author

**Saad Karzouz**

---

*Built with ❤️ using Spring Boot*

