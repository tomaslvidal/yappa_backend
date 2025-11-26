# 🚀 Yappa

## Sistema de ABM de Clientes

Aplicación Full Stack para la gestión completa de clientes (Alta, Baja, Modificación) desarrollada con:
- **Backend**: Java 17, Spring Boot 3, PostgreSQL
- **Frontend**: React.js 18, Bootstrap 5, Vite
- **Infraestructura**: Docker, Docker Compose

---

## Tabla de Contenidos

- [Tecnologías](#-tecnologías)
- [Características](#-características)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Requisitos Previos](#-requisitos-previos)
- [Instalación y Ejecución](#-instalación-y-ejecución)
- [Documentación API REST](#-documentación-api-rest)
- [Base de Datos](#-base-de-datos)
- [Tests](#-tests)
- [Docker](#-docker)
- [Variables de Entorno](#-variables-de-entorno)

---

## Tecnologías

### Backend
| Tecnología | Versión | Propósito |
|-----------|---------|-----------|
| **Java** | 17 | Lenguaje de programación |
| **Spring Boot** | 3.2.0 | Framework principal |
| **Spring Data JPA** | - | ORM y persistencia |
| **PostgreSQL** | 15 | Base de datos |
| **Flyway** | - | Migraciones de BD |
| **Swagger/OpenAPI** | 2.3.0 | Documentación API |
| **Lombok** | 1.18.30 | Reducción de boilerplate |
| **JUnit 5** | - | Testing unitario |
| **Mockito** | - | Mocking para tests |
| **Maven** | - | Gestión de dependencias |

### Frontend
| Tecnología | Versión | Propósito |
|-----------|---------|-----------|
| **React** | 18.2.0 | Librería UI |
| **React Router** | 6.20.0 | Navegación SPA |
| **Bootstrap** | 5.3.2 | Framework CSS |
| **React Bootstrap** | 2.9.1 | Componentes React |
| **Axios** | 1.6.2 | Cliente HTTP |
| **Formik** | 2.4.5 | Manejo de formularios |
| **Yup** | 1.3.3 | Validación de esquemas |
| **Vite** | 5.0.5 | Build tool |
| **Nginx** | Alpine | Servidor web |

### Infraestructura
| Tecnología | Versión | Propósito |
|-----------|---------|-----------|
| **Docker** | - | Contenedorización |
| **Docker Compose** | - | Orquestación |

---

## Características

### Backend
- ✅ **CRUD completo** de clientes (API REST)
- ✅ **Validaciones** de datos (Bean Validation)
- ✅ **Constraints únicos** (CUIT y Email)
- ✅ **Búsqueda** por CUIT y Email
- ✅ **Documentación interactiva** con Swagger UI
- ✅ **Migraciones automáticas** con Flyway
- ✅ **Tests unitarios** completos (JUnit + Mockito)
- ✅ **Manejo de excepciones** centralizado
- ✅ **DTOs** para entrada/salida
- ✅ **Logs estructurados** con SLF4J
- ✅ **Configuración CORS** para frontend
- ✅ **Timestamps automáticos** (creación/modificación)

### Frontend
- ✅ **Interfaz moderna** con React + Bootstrap 5
- ✅ **Listado de clientes** con búsqueda en tiempo real
- ✅ **Formularios validados** con Formik + Yup
- ✅ **Vista detallada** de cada cliente
- ✅ **Edición** de clientes existentes
- ✅ **Eliminación** con confirmación
- ✅ **Diseño responsive** mobile-first
- ✅ **Manejo de errores** con feedback visual
- ✅ **Loading states** para mejor UX
- ✅ **Navegación** con React Router

### Infraestructura
- ✅ **Docker Compose** con 3 servicios (PostgreSQL, Backend, Frontend)
- ✅ **Nginx** como reverse proxy
- ✅ **Comunicación** entre contenedores
- ✅ **Volúmenes persistentes** para la base de datos

---

## Estructura del Proyecto

```
yappa/
├── frontend/                                  # Frontend React
│   ├── src/
│   │   ├── components/
│   │   │   ├── common/                       # Componentes reutilizables
│   │   │   │   ├── Loading.jsx
│   │   │   │   ├── ErrorAlert.jsx
│   │   │   │   └── ConfirmDialog.jsx
│   │   │   └── layout/                       # Layout components
│   │   │       ├── Navbar.jsx
│   │   │       └── Footer.jsx
│   │   ├── pages/                            # Páginas principales
│   │   │   ├── Home.jsx
│   │   │   ├── ClienteList.jsx
│   │   │   ├── ClienteForm.jsx
│   │   │   ├── ClienteDetail.jsx
│   │   │   └── NotFound.jsx
│   │   ├── services/                         # Servicios API
│   │   │   ├── api.js
│   │   │   └── clienteService.js
│   │   ├── App.jsx
│   │   ├── main.jsx
│   │   └── index.css
│   ├── index.html
│   ├── package.json
│   ├── vite.config.js
│   ├── Dockerfile
│   ├── nginx.conf
│   └── README.md
├── src/                                       # Backend Spring Boot
│   ├── main/
│   │   ├── java/com/yappa/
│   │   │   ├── YappaApplication.java
│   │   │   ├── config/
│   │   │   │   ├── SwaggerConfig.java
│   │   │   │   └── CorsConfig.java           # Configuración CORS
│   │   │   ├── controller/
│   │   │   │   └── ClienteController.java
│   │   │   ├── dto/
│   │   │   │   ├── ClienteRequestDTO.java
│   │   │   │   └── ClienteResponseDTO.java
│   │   │   ├── entity/
│   │   │   │   └── Cliente.java
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   └── BusinessException.java
│   │   │   ├── repository/
│   │   │   │   └── ClienteRepository.java
│   │   │   └── service/
│   │   │       ├── ClienteService.java
│   │   │       └── impl/
│   │   │           └── ClienteServiceImpl.java
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       ├── application-prod.yml
│   │       └── db/migration/
│   │           └── V1__create_clientes_table.sql
│   └── test/
│       └── java/com/yappa/
│           ├── controller/
│           │   └── ClienteControllerTest.java
│           ├── service/
│           │   └── ClienteServiceTest.java
│           └── repository/
│               └── ClienteRepositoryTest.java
├── docker/
│   └── postgres/
│       └── init.sql
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── README.md
```

---

## Requisitos Previos

### Con Docker (Recomendado)
- **Docker** y **Docker Compose**

### Sin Docker
- **Java 17** o superior
- **Maven 3.8+**
- **Node.js 18+** y **npm**
- **PostgreSQL 15**

---

## Instalación y Ejecución

### **Opción 1: Con Docker Compose (Recomendado)**

```bash
# 1. Clonar el repositorio
git clone https://github.com/tomaslvidal/yappa.git
cd yappa

# 2. Construir y levantar los servicios
docker-compose up --build

# La aplicación estará disponible en:
# - Frontend: http://localhost:3000
# - API Backend: http://localhost:8080
# - Swagger UI: http://localhost:8080/swagger-ui.html
# - PostgreSQL: localhost:5433
```

### **Opción 2: Ejecución Local**

**Backend:**
```bash
# 1. Clonar el repositorio
git clone https://github.com/tomaslvidal/yappa.git
cd yappa

# 2. Configurar PostgreSQL
createdb yappa_db

# 3. Compilar y ejecutar el backend
./mvnw clean install
./mvnw spring-boot:run

# Backend disponible en http://localhost:8080
```

**Frontend:**
```bash
# En otra terminal
cd frontend

# Instalar dependencias
npm install

# Ejecutar servidor de desarrollo
npm run dev

# Frontend disponible en http://localhost:3000
```

### **Opción 3: Desde IDE**

1. Importar como proyecto Maven
2. Configurar base de datos PostgreSQL
3. Ejecutar `YappaApplication.java`

---

## Documentación API REST

### **Base URL**
```
http://localhost:8080/api/clientes
```

### **Swagger UI (Documentación Interactiva)**
```
http://localhost:8080/swagger-ui.html
```

### **OpenAPI JSON**
```
http://localhost:8080/api-docs
```

---

## Endpoints

### **1. Crear Cliente**

```http
POST /api/clientes
Content-Type: application/json
```

**Request Body:**
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "razonSocial": "JP Servicios SRL",
  "cuit": "20-12345678-9",
  "fechaNacimiento": "1985-06-15",
  "telefonoCelular": "1165874210",
  "email": "juan.perez@example.com"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "razonSocial": "JP Servicios SRL",
  "cuit": "20-12345678-9",
  "fechaNacimiento": "1985-06-15",
  "telefonoCelular": "1165874210",
  "email": "juan.perez@example.com",
  "fechaCreacion": "2024-11-21T10:30:00",
  "fechaModificacion": "2024-11-21T10:30:00"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan",
    "apellido": "Pérez",
    "razonSocial": "JP Servicios SRL",
    "cuit": "20-12345678-9",
    "fechaNacimiento": "1985-06-15",
    "telefonoCelular": "1165874210",
    "email": "juan.perez@example.com"
  }'
```

---

### **2. Obtener Cliente por ID**

```http
GET /api/clientes/{id}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "razonSocial": "JP Servicios SRL",
  "cuit": "20-12345678-9",
  "fechaNacimiento": "1985-06-15",
  "telefonoCelular": "1165874210",
  "email": "juan.perez@example.com",
  "fechaCreacion": "2024-11-21T10:30:00",
  "fechaModificacion": "2024-11-21T10:30:00"
}
```

**cURL:**
```bash
curl -X GET http://localhost:8080/api/clientes/1
```

---

### **3. Listar Todos los Clientes**

```http
GET /api/clientes
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "nombre": "Juan",
    "apellido": "Pérez",
    "razonSocial": "JP Servicios SRL",
    "cuit": "20-12345678-9",
    "fechaNacimiento": "1985-06-15",
    "telefonoCelular": "1165874210",
    "email": "juan.perez@example.com",
    "fechaCreacion": "2024-11-21T10:30:00",
    "fechaModificacion": "2024-11-21T10:30:00"
  },
  {
    "id": 2,
    "nombre": "María",
    "apellido": "Gómez",
    "razonSocial": "MG Soluciones",
    "cuit": "27-23456789-0",
    "fechaNacimiento": "1990-09-21",
    "telefonoCelular": "1165874221",
    "email": "maria.gomez@example.com",
    "fechaCreacion": "2024-11-21T10:35:00",
    "fechaModificacion": "2024-11-21T10:35:00"
  }
]
```

**cURL:**
```bash
curl -X GET http://localhost:8080/api/clientes
```

---

### **4. Actualizar Cliente**

```http
PUT /api/clientes/{id}
Content-Type: application/json
```

**Request Body:**
```json
{
  "nombre": "Juan Carlos",
  "apellido": "Pérez López",
  "razonSocial": "JP Servicios SRL",
  "cuit": "20-12345678-9",
  "fechaNacimiento": "1985-06-15",
  "telefonoCelular": "1165874210",
  "email": "juan.perez@example.com"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "nombre": "Juan Carlos",
  "apellido": "Pérez López",
  "razonSocial": "JP Servicios SRL",
  "cuit": "20-12345678-9",
  "fechaNacimiento": "1985-06-15",
  "telefonoCelular": "1165874210",
  "email": "juan.perez@example.com",
  "fechaCreacion": "2024-11-21T10:30:00",
  "fechaModificacion": "2024-11-21T11:45:00"
}
```

**cURL:**
```bash
curl -X PUT http://localhost:8080/api/clientes/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan Carlos",
    "apellido": "Pérez López",
    "razonSocial": "JP Servicios SRL",
    "cuit": "20-12345678-9",
    "fechaNacimiento": "1985-06-15",
    "telefonoCelular": "1165874210",
    "email": "juan.perez@example.com"
  }'
```

---

### **5. Eliminar Cliente**

```http
DELETE /api/clientes/{id}
```

**Response (204 No Content)**

**cURL:**
```bash
curl -X DELETE http://localhost:8080/api/clientes/1
```

---

### **6. Buscar Cliente por CUIT**

```http
GET /api/clientes/cuit/{cuit}
```

**Ejemplo:**
```http
GET /api/clientes/cuit/20-12345678-9
```

**Response (200 OK):**
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "razonSocial": "JP Servicios SRL",
  "cuit": "20-12345678-9",
  "fechaNacimiento": "1985-06-15",
  "telefonoCelular": "1165874210",
  "email": "juan.perez@example.com",
  "fechaCreacion": "2024-11-21T10:30:00",
  "fechaModificacion": "2024-11-21T10:30:00"
}
```

**cURL:**
```bash
curl -X GET http://localhost:8080/api/clientes/cuit/20-12345678-9
```

---

### **7. Buscar Cliente por Email**

```http
GET /api/clientes/email/{email}
```

**Ejemplo:**
```http
GET /api/clientes/email/juan.perez@example.com
```

**Response (200 OK):**
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "razonSocial": "JP Servicios SRL",
  "cuit": "20-12345678-9",
  "fechaNacimiento": "1985-06-15",
  "telefonoCelular": "1165874210",
  "email": "juan.perez@example.com",
  "fechaCreacion": "2024-11-21T10:30:00",
  "fechaModificacion": "2024-11-21T10:30:00"
}
```

**cURL:**
```bash
curl -X GET http://localhost:8080/api/clientes/email/juan.perez@example.com
```

---

## Respuestas de Error

### **404 Not Found**
```json
{
  "timestamp": "2024-11-21T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Cliente no encontrado con ID: 1"
}
```

### **400 Bad Request (Validación)**
```json
{
  "timestamp": "2024-11-21T10:30:00",
  "status": 400,
  "error": "Validation Error",
  "message": "Error de validación en los datos",
  "validationErrors": {
    "nombre": "El nombre es obligatorio",
    "cuit": "El CUIT debe tener formato XX-XXXXXXXX-X",
    "email": "El email debe ser válido"
  }
}
```

### **409 Conflict (Duplicado)**
```json
{
  "timestamp": "2024-11-21T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Ya existe un cliente con el CUIT: 20-12345678-9"
}
```

---

## Base de Datos

### **Esquema de la Tabla `clientes`**

| Campo | Tipo | Constraints | Descripción |
|-------|------|-------------|-------------|
| `id` | SERIAL | PRIMARY KEY | ID autoincremental |
| `nombre` | VARCHAR(100) | NOT NULL | Nombre del cliente |
| `apellido` | VARCHAR(100) | NOT NULL | Apellido del cliente |
| `razon_social` | VARCHAR(150) | NOT NULL | Razón social |
| `cuit` | VARCHAR(20) | NOT NULL, UNIQUE | CUIT formato XX-XXXXXXXX-X |
| `fecha_nacimiento` | DATE | NOT NULL | Fecha de nacimiento |
| `telefono_celular` | VARCHAR(30) | NOT NULL | Teléfono (10 dígitos) |
| `email` | VARCHAR(150) | NOT NULL, UNIQUE | Email válido |
| `fecha_creacion` | TIMESTAMP | DEFAULT NOW() | Timestamp de creación |
| `fecha_modificacion` | TIMESTAMP | DEFAULT NOW() | Timestamp de modificación |

### **Validaciones**

- **CUIT**: Formato `XX-XXXXXXXX-X` (ej: `20-12345678-9`)
- **Email**: Formato de email válido
- **Teléfono**: 10 dígitos numéricos (ej: `1165874210`)
- **Fecha Nacimiento**: Debe ser en el pasado
- **CUIT y Email**: Únicos en la base de datos

---

## Tests

### **Ejecutar Tests**

```bash
# Todos los tests
./mvnw test

# Tests con reporte de cobertura
./mvnw test jacoco:report

# Tests específicos
./mvnw test -Dtest=ClienteServiceTest
```

### **Cobertura de Tests**

- ✅ **Tests Unitarios de Servicio** (`ClienteServiceTest`)
  - Crear cliente
  - Obtener por ID
  - Listar todos
  - Actualizar cliente
  - Eliminar cliente
  - Buscar por CUIT/Email
  - Validaciones y excepciones

- ✅ **Tests de Controlador** (`ClienteControllerTest`)
  - Endpoints REST
  - Validaciones de entrada
  - Respuestas HTTP

- ✅ **Tests de Repositorio** (`ClienteRepositoryTest`)
  - Operaciones CRUD
  - Constraints únicos
  - Queries personalizadas

---

## Docker

### **Servicios Docker Compose**

```yaml
services:
  postgres:    # Base de datos PostgreSQL 15
  app:         # Backend Spring Boot
  frontend:    # Frontend React + Nginx
```

### **Comandos Docker**

```bash
# Iniciar servicios
docker-compose up

# Iniciar en background
docker-compose up -d

# Ver logs
docker-compose logs -f

# Detener servicios
docker-compose down

# Detener y eliminar volúmenes
docker-compose down -v

# Reconstruir imágenes
docker-compose up --build
```

### **Acceso a PostgreSQL**

```bash
# Conectar al contenedor de PostgreSQL
docker exec -it yappa-postgres psql -U yappa_user -d yappa_db

# Ver tablas
\dt

# Ver datos de clientes
SELECT * FROM clientes;
```

---

## Variables de Entorno

### **Desarrollo Local**

```bash
export SPRING_PROFILES_ACTIVE=dev
```

### **Producción (Docker)**

```bash
export SPRING_PROFILES_ACTIVE=prod
export SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/yappa_db
export SPRING_DATASOURCE_USERNAME=yappa_user
export SPRING_DATASOURCE_PASSWORD=yappa_password
```

---

## Datos de Prueba

La aplicación incluye 5 clientes de ejemplo al ejecutar la migración:

1. **Juan Pérez** - JP Servicios SRL
2. **María Gómez** - MG Soluciones
3. **Carlos López** - CL Construcciones
4. **Lucía Martínez** - LM Consultora
5. **Diego Fernández** - DF Diseño

---

## Autor

**Tomás Vidal**
- GitHub: [@tomaslvidal](https://github.com/tomaslvidal)

---

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE.md` para más detalles.

---

## Agradecimientos

Intuit / Yappa
