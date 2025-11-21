# 🎯 GUÍA RÁPIDA - Yappa

## Inicio Rápido

### 1️⃣ Con Docker (Recomendado)
```bash
docker-compose up --build
```
✅ Aplicación: http://localhost:8080  
✅ Swagger: http://localhost:8080/swagger-ui.html

### 2️⃣ Sin Docker
```bash
# Compilar
./mvnw clean install

# Ejecutar
./mvnw spring-boot:run
```

---

## Checklist de Funcionalidades

- [x] **Java 17** + **Spring Boot 3.2**
- [x] **PostgreSQL** con Docker
- [x] **Swagger/OpenAPI** - Documentación interactiva
- [x] **Maven** - Gestión de dependencias
- [x] **CRUD Completo** de clientes
- [x] **Validaciones** (Bean Validation)
- [x] **Tests Unitarios** (JUnit + Mockito)
- [x] **DTOs** para request/response
- [x] **Exception Handling** global
- [x] **Flyway** para migraciones
- [x] **Lombok** para clean code
- [x] **Docker Compose** configurado
- [x] Búsqueda por **CUIT** y **Email**
- [x] Constraints **únicos**
- [x] **Timestamps** automáticos

---

## Ejecutar Tests

```bash
# Todos los tests
./mvnw test

# Con cobertura
./mvnw test jacoco:report
```

---

## Endpoints Principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/clientes` | Crear cliente |
| `GET` | `/api/clientes` | Listar todos |
| `GET` | `/api/clientes/{id}` | Obtener por ID |
| `PUT` | `/api/clientes/{id}` | Actualizar |
| `DELETE` | `/api/clientes/{id}` | Eliminar |
| `GET` | `/api/clientes/cuit/{cuit}` | Buscar por CUIT |
| `GET` | `/api/clientes/email/{email}` | Buscar por email |

---

## Modelo de Datos

```json
{
  "nombre": "string",           // Máx 100 caracteres
  "apellido": "string",         // Máx 100 caracteres
  "razonSocial": "string",      // Máx 150 caracteres
  "cuit": "XX-XXXXXXXX-X",      // Formato específico, único
  "fechaNacimiento": "YYYY-MM-DD", // Fecha pasada
  "telefonoCelular": "1234567890", // 10 dígitos
  "email": "user@example.com"   // Email válido, único
}
```

---

## Docker

```bash
# Iniciar
docker-compose up -d

# Ver logs
docker-compose logs -f

# Detener
docker-compose down

# Acceder a PostgreSQL
docker exec -it yappa-postgres psql -U yappa_user -d yappa_db
```

---

## Validaciones Implementadas

- ✅ Campos obligatorios (`@NotBlank`, `@NotNull`)
- ✅ Longitud máxima (`@Size`)
- ✅ Formato CUIT (`@Pattern`)
- ✅ Email válido (`@Email`)
- ✅ Teléfono 10 dígitos (`@Pattern`)
- ✅ Fecha en el pasado (`@Past`)
- ✅ CUIT único (DB constraint)
- ✅ Email único (DB constraint)

---

## Tecnologías Clave

- **Java 17**
- **Spring Boot 3.2**
- **Spring Data JPA**
- **PostgreSQL 15**
- **Flyway**
- **Swagger/OpenAPI 2.3**
- **JUnit 5**
- **Mockito**
- **Lombok**
- **Maven**
- **Docker & Docker Compose**

---

## Estructura del Código

```
src/main/java/com/yappa/
├── config/          # Configuración (Swagger)
├── controller/      # REST Controllers
├── dto/             # Data Transfer Objects
├── entity/          # JPA Entities
├── exception/       # Exception Handling
├── repository/      # JPA Repositories
└── service/         # Business Logic
    └── impl/
```

---

## Contacto

**Autor:** Tomás Vidal  
**GitHub:** @tomaslvidal
