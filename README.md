# 🍔 API de Franquicias - Prueba Técnica

[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.11-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-✓-blue.svg)](https://www.docker.com/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-orange.svg)](https://www.mysql.com/)
[![Redis](https://img.shields.io/badge/Redis-7-red.svg)](https://redis.com/)

## 📋 Descripción del Proyecto

API RESTful para la gestión de franquicias, sucursales y productos. La aplicación permite administrar el stock de productos en diferentes sucursales de una franquicia.

### 🏗️ Arquitectura
- **Backend**: Spring Boot 3.5.11 con controladores REST tradicionales
- **Base de Datos**: MySQL 8.0 (contenedor Docker)
- **Caché**: Redis 7 (contenedor Docker) para optimización de rendimiento
- **Contenerización**: Docker y Docker Compose para orquestación completa

---

## 🚀 Características Implementadas

| Característica | Estado | Descripción |
|----------------|--------|-------------|
| **CRUD de Franquicias** | ✅ Completo | Crear, actualizar franquicias |
| **CRUD de Sucursales** | ✅ Completo | Agregar, actualizar sucursales |
| **CRUD de Productos** | ✅ Completo | Agregar, actualizar, eliminar productos |
| **Control de Stock** | ✅ Completo | Actualizar stock individualmente |
| **Reporte Top Productos** | ✅ Completo | Producto con mayor stock por sucursal |
| **Caché con Redis** | ✅ Implementado | Mejora de rendimiento en consultas frecuentes |
| **Dockerización** | ✅ Completo | API, MySQL y Redis en contenedores |
| **Swagger UI** | ✅ Activo | Documentación interactiva |
| **Colección Postman** | ✅ Incluida | Pruebas listas para usar |

---

## 📂 Estructura del Proyecto

```
franchise-api/
├── src/                           # Código fuente
│   ├── main/
│   │   ├── java/
│   │   │   └── com.accenture.franchise_api/
│   │   │       ├── controller/    # Controladores REST
│   │   │       ├── service/       # Lógica de negocio
│   │   │       ├── repository/    # Acceso a datos
│   │   │       ├── entity/        # Entidades JPA
│   │   │       └── dto/           # Objetos de transferencia
│   │   └── resources/
│   │       └── application.properties
├── init-db/
│   └── init.sql                    # Datos iniciales
├── docker-compose.yml               # Orquestación de contenedores
├── Dockerfile                       # Imagen de la API
├── Franchise-API-Local.postman_collection.json  # Colección Postman
└── README.md                        # Este archivo
```

---

## 🐳 Despliegue Local con Docker

### 📋 Prerrequisitos
- [Docker](https://www.docker.com/products/docker-desktop/) (v20.10+)
- [Docker Compose](https://docs.docker.com/compose/install/) (v2.0+)
- [Git](https://git-scm.com/)

### 🚀 Pasos para levantar la aplicación

#### 1. Clonar el repositorio
```bash
git clone https://github.com/JAristi/DevTestBackend.git
cd DevTestBackend
```

#### 2. Levantar los contenedores
```bash
docker-compose up --build
```

Este comando levantará tres servicios:
- **`franchise_api`** : La API Spring Boot (puerto `8080`)
- **`franchise_mysql`** : Base de datos MySQL (puerto `3306`)
- **`franchise_redis`** : Redis para caché (puerto `6379`)

#### 3. Verificar que los contenedores están corriendo
```bash
docker ps
```

Deberías ver:
```
CONTAINER ID   IMAGE                       PORTS                    NAMES
xxxxxxxxxxxx   franchise-api:latest        0.0.0.0:8080->8080/tcp   franchise_api
xxxxxxxxxxxx   mysql:8                     0.0.0.0:3306->3306/tcp   franchise_mysql
xxxxxxxxxxxx   redis:7                      0.0.0.0:6379->6379/tcp   franchise_redis
```

#### 4. Probar que la API responde
```bash
curl http://localhost:8080/api/franchises/1/top-products
```

---

## 📊 Endpoints Disponibles

### Colección Postman
Para facilitar las pruebas, se incluye una colección de Postman lista para usar:
- **Archivo:** `Franchise-API-Local.postman_collection.json`
- **Cómo importar:** Postman → File → Import → Seleccionar el archivo

### Endpoints Detallados

| Método | Endpoint | Descripción | Ejemplo |
|--------|----------|-------------|---------|
| **POST** | `/api/franchises?name=` | Crear nueva franquicia | `POST /api/franchises?name=Franquicia Sur` |
| **PUT** | `/api/franchises/{id}?name=` | Actualizar nombre de franquicia | `PUT /api/franchises/1?name=Nuevo Nombre` |
| **POST** | `/api/franchises/{franchiseId}/branches?name=` | Agregar sucursal | `POST /api/franchises/1/branches?name=Sucursal Nueva` |
| **PUT** | `/api/franchises/branches/{id}?name=` | Actualizar sucursal | `PUT /api/franchises/branches/1?name=Sucursal Modificada` |
| **POST** | `/api/franchises/branches/{branchId}/products?name=&stock=` | Agregar producto | `POST /api/franchises/branches/1/products?name=Malteada&stock=25` |
| **PUT** | `/api/franchises/products/{id}?name=` | Actualizar nombre producto | `PUT /api/franchises/products/1?name=Hamburguesa Especial` |
| **PUT** | `/api/franchises/products/{productId}/stock?stock=` | Actualizar stock | `PUT /api/franchises/products/1/stock?stock=75` |
| **DELETE** | `/api/franchises/franchises/{fid}/branches/{bid}/products/{pid}` | Eliminar producto | `DELETE /api/franchises/franchises/1/branches/1/products/1` |
| **GET** | `/api/franchises/{franchiseId}/top-products` | Top productos por sucursal | `GET /api/franchises/1/top-products` |

---

## 📖 Swagger UI

La API cuenta con documentación interactiva generada con Swagger. Una vez que la aplicación esté corriendo:

- **URL:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **Funcionalidades:**
    - Explorar todos los endpoints
    - Probar las peticiones directamente desde el navegador
    - Ver los modelos de datos (DTOs)

---

## 🗄️ Datos Iniciales Cargados

La base de datos se inicializa automáticamente con el archivo `init-db/init.sql` cuando el contenedor de MySQL se levanta por primera vez.

### Franquicias
| ID | Nombre |
|----|--------|
| 1 | Franchise Norte |
| 2 | Franchise Centro |

### Sucursales
| ID | Nombre | Franquicia |
|----|--------|------------|
| 1 | Sucursal Medellin | 1 (Norte) |
| 2 | Sucursal Bogota | 1 (Norte) |
| 3 | Sucursal Cali | 2 (Centro) |

### Productos
| ID | Nombre | Stock | Sucursal |
|----|--------|-------|----------|
| 1 | Hamburguesa | 50 | 1 (Medellin) |
| 2 | Pizza | 30 | 1 (Medellin) |
| 3 | Hot Dog | 20 | 2 (Bogota) |
| 4 | Papas Fritas | 40 | 3 (Cali) |

---

## 🧪 Pruebas de Funcionamiento

### Prueba 1: Verificar datos iniciales
```bash
curl http://localhost:8080/api/franchises/1/top-products
```
**Respuesta esperada:**
```json
[
  {
    "branchName": "Sucursal Medellin",
    "productName": "Hamburguesa",
    "stock": 50
  },
  {
    "branchName": "Sucursal Bogota",
    "productName": "Hot Dog",
    "stock": 20
  }
]
```

### Prueba 2: Crear nueva franquicia
```bash
curl -X POST "http://localhost:8080/api/franchises?name=Franquicia%20Test"
```

### Prueba 3: Agregar producto
```bash
curl -X POST "http://localhost:8080/api/franchises/branches/1/products?name=Malteada&stock=25"
```

---

## ⚙️ Configuración con Docker Compose

El archivo `docker-compose.yml` define tres servicios:

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8
    container_name: franchise_mysql
    command: --port=3306
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: franchise_db
    ports:
      - "3306:3306"
    volumes:
      - ./init-db:/docker-entrypoint-initdb.d
    restart: always
    healthcheck:
      test: [ "CMD", "mysql", "-uroot", "-proot", "-e", "SELECT 1" ]
      interval: 10s
      timeout: 5s
      retries: 10

  redis:
    image: redis:7
    container_name: franchise_redis
    ports:
      - "6379:6379"
    restart: always

  api:
    build: .
    container_name: franchise_api
    ports:
      - "8080:8080"
    depends_on:
      - mysql
      - redis
    restart: always
```

---

## 🧹 Limpieza

### Detener contenedores
```bash
docker-compose down
```

### Eliminar volúmenes (reiniciar desde cero)
```bash
docker-compose down -v
```
---

## 🌩️ Plus: Infraestructura en la Nube (AWS)

Como parte del valor agregado de la prueba técnica, se realizó un trabajo adicional de **Infraestructura como Código (IaC)** utilizando **Terraform** para desplegar los recursos en AWS.

### 🏗️ Trabajo Realizado

| Componente | Descripción | Estado |
|------------|-------------|--------|
| **Terraform** | Configuración completa de IaC | ✅ 100% implementado |
| **RDS MySQL** | Base de datos en AWS (db.t3.micro) | ✅ Creada y funcional |
| **Security Group** | Configuración de red y firewall | ✅ Configurado |
| **Datos iniciales** | Carga automática con init.sql | ✅ Verificada |
| **Aplicación Spring Boot** | Despliegue en Render | 🟡 Parcial (pendiente Redis) |

### 📁 ¿Dónde está el código?
Todo el código de Terraform se encuentra en la carpeta [`/terraform`](/terraform) del repositorio, incluyendo:
- `main.tf` - Configuración principal de recursos
- `variables.tf` - Variables parametrizadas
- `outputs.tf` - Outputs (endpoint, puerto)
- `README.md` - Documentación específica con capturas

### 📸 Evidencia de Implementación
En la carpeta [`/terraform/screenshots`](/terraform/screenshots) se incluyen capturas que verifican:
- ✅ Instancia RDS creada en AWS Console
- ✅ Detalles de configuración (endpoint, clase db.t3.micro, motor MySQL)
- ✅ Conexión exitosa y datos cargados
- ✅ Outputs de Terraform

### ⚠️ Nota sobre el despliegue completo
El despliegue completo de la aplicación en la nube (API + Redis) quedó **parcialmente completado** debido a:
- **Límites de la capa gratuita** de Render (una sola instancia Redis gratuita por cuenta)
- **Tiempo de resolución** de problemas de conectividad entre servicios

Sin embargo, **la base de datos RDS está 100% operativa en AWS** y puede ser verificada mediante:
```bash
# Conexión directa (usando cualquier cliente MySQL)
mysql -h franchise-database.ca9c2sq4agic.us-east-1.rds.amazonaws.com -P 3306 -u admin -p
# Contraseña: Admin123!
```

### 🔗 Enlaces Relacionados
- [Documentación específica de Terraform](./terraform/README.md)
- [Capturas de evidencia](./terraform/screenshots)

**Desarrollado por:** JAristi
**Tecnologías:** Spring Boot, Redis, MySQL, Docker