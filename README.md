# Franchise API

Para evitar dependencias externas en el entorno de ejecución,
los tests se omiten durante el empaquetado utilizando:

mvn clean package -DskipTests

## Stack utilizado

Java 17  
Spring Boot 3  
Spring Data JPA  
MySQL  
Docker  
Swagger

## Ejecutar docker

docker compose up --build

## Endpoints

POST /api/franchises

POST /api/franchises/{id}/branches

POST /api/franchises/branches/{id}/products

DELETE /api/franchises/products/{id}

PUT /api/franchises/products/{id}/stock

GET /api/franchises/{id}/top-products