# Implementation Summary - Spring Data REST API

## What Was Implemented

### Step 9: Expose IDs in JSON Responses
- Updated `Application.java` to use `RepositoryRestConfiguration.exposeIdsFor()` method
- IDs are now exposed for both `Compte` and `Client` entities

### Step 11: Client-Compte Relationship Management

#### a. Created Client Entity
- **File**: `src/main/java/ma/rest/spring/entities/Client.java`
- Added `@OneToMany` relationship with Compte
- Fields: id, nom, email, comptes
- Added `@ToString(exclude = "comptes")` to avoid LazyInitializationException

#### b. Updated Compte Entity
- **File**: `src/main/java/ma/rest/spring/entities/Compte.java`
- Added `@ManyToOne` relationship with Client
- Added `client` field with `@JoinColumn(name = "client_id")`

#### c. Created ClientRepository
- **File**: `src/main/java/ma/rest/spring/repositories/ClientRepository.java`
- Extends `JpaRepository<Client, Long>`
- Annotated with `@RepositoryRestResource`

#### d. Data Initialization
- **File**: `src/main/java/ma/rest/spring/Application.java`
- Creates two clients: Amal and Ali
- Associates 3 accounts with clients:
  - 2 accounts for Amal (1 EPARGNE, 1 COURANT)
  - 1 account for Ali (1 EPARGNE)

#### e. Created ClientProjection
- **File**: `src/main/java/ma/rest/spring/entities/ClientProjection.java`
- Projection name: "clientDetails"
- Exposes only: nom and email

### Step 12: Search by Account Type

#### a. Updated CompteRepository
- **File**: `src/main/java/ma/rest/spring/repositories/CompteRepository.java`
- Added method: `findByType(@Param("t") TypeCompte type)`
- Annotated with `@RestResource(path = "/byType")`

## How to Build and Run

```bash
# Build the project
.\mvnw.cmd clean package -DskipTests

# Run the application
java -jar target\spring-0.0.1-SNAPSHOT.jar

# Or use Maven Spring Boot plugin
.\mvnw.cmd spring-boot:run
```

## API Endpoints to Test

### Basic CRUD Operations

1. **Get all accounts**
   ```
   GET http://localhost:8082/api/comptes
   ```

2. **Get account by ID with projection**
   ```
   GET http://localhost:8082/api/comptes/1?projection=mobile
   ```

3. **Get all clients**
   ```
   GET http://localhost:8082/api/clients
   ```

4. **Get client by ID**
   ```
   GET http://localhost:8082/api/clients/1
   ```

### Relationship Navigation

5. **Get all accounts for a specific client**
   ```
   GET http://localhost:8082/api/clients/1/comptes
   ```

6. **Get client information for a specific account**
   ```
   GET http://localhost:8082/api/comptes/1/client
   ```

7. **Get client with projection**
   ```
   GET http://localhost:8082/api/comptes/1/client?projection=clientDetails
   ```

### Custom Search

8. **Search accounts by type (EPARGNE)**
   ```
   GET http://localhost:8082/api/comptes/search/byType?t=EPARGNE
   ```

9. **Search accounts by type (COURANT)**
   ```
   GET http://localhost:8082/api/comptes/search/byType?t=COURANT
   ```

### Available Projections

- **For Compte**: 
  - `solde` - Shows only balance
  - `mobile` - Shows balance and type

- **For Client**:
  - `clientDetails` - Shows only name and email

## Key Features

✅ Spring Data REST automatically exposes repositories as REST endpoints
✅ IDs are exposed in JSON responses
✅ Automatic HATEOAS links for navigation between resources
✅ Custom projections for flexible response formats
✅ Custom search methods with @RestResource
✅ One-to-Many and Many-to-One relationships properly configured
✅ In-memory H2 database with auto-initialization

## Files Modified/Created

### Created:
- `Client.java` - Client entity
- `ClientRepository.java` - Client repository
- `ClientProjection.java` - Client projection

### Modified:
- `Compte.java` - Added client relationship
- `CompteRepository.java` - Added findByType method
- `Application.java` - Updated initialization with clients and relationships
- `application.properties` - Fixed encoding issues

## Notes

- The application uses Spring Boot 2.7.18
- Database: H2 in-memory database
- Base path for REST API: `/api`
- Server port: `8082`
- H2 Console available at: `http://localhost:8082/h2-console`
  - JDBC URL: `jdbc:h2:mem:banque`
  - Username: `sa`
  - Password: (empty)

## Troubleshooting

If you encounter a 404 error:
1. Ensure the application is running on port 8082
2. Check that you're using the correct base path `/api`
3. Verify Spring Data REST dependency is in pom.xml
4. Make sure repositories are annotated with `@RepositoryRestResource`

If you get LazyInitializationException:
- Collections in relationships should be excluded from `@ToString`
- Use `@ToString(exclude = "collectionName")` on entities with OneToMany relationships

