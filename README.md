# IntegraObra API (integraobra-api-rest)

Hola — soy Gustavo Alexander Vásquez More. Este repositorio contiene la API REST que implementé para la gestión de productos y categorías del proyecto IntegraObra. Aquí explico, de forma directa y práctica, cómo correrla localmente y los puntos técnicos relevantes que me gustaría que revises.

[Ver documentación detallada de la API (endpoints, ejemplos)](./API_DOCUMENTATION.md)

---

## Requisitos
- Java 17+ (ajusta según tu `pom.xml` si usas otra versión)
- Maven (o usar el wrapper `./mvnw` incluido)
- Docker (opcional)

---

## Quick start (desarrollo)
Clona el repositorio y ejecuta la aplicación localmente con Maven (usa el wrapper incluido):

```bash
git clone <tu-repo-url>
cd integraobra-api-rest
./mvnw clean package
./mvnw spring-boot:run
```

La aplicación arranca en el puerto configurado en `src/main/resources/application.properties` (por defecto `8080`).

---

## Build y ejecución de producción (jar)
Para generar el jar y ejecutarlo:

```bash
./mvnw clean package -DskipTests
java -jar target/integraobra-api-rest-0.0.1-SNAPSHOT.jar
```

---

## Docker
Instrucciones básicas para construir y ejecutar la imagen Docker:

```bash
# construir la imagen
docker build -t integraobra-api-rest:latest .

# ejecutar el contenedor (mapear puerto 8080)
docker run -d --name integraobra-api -p 8080:8080 integraobra-api-rest:latest
```

Si necesitas pasar configuración, usa variables de entorno locales o un archivo `.env` en tu entorno privado.

---

## Run con profile o configuración externa
Puedes pasar un profile de Spring o variables de entorno para apuntar a otra BD o cambiar el puerto:

```bash
# usar un profile llamado 'prod'
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod

# pasar propiedades directamente
java -jar target/integraobra-api-rest-0.0.1-SNAPSHOT.jar --server.port=8085
```

---

## Tests
Ejecutar la suite de pruebas con Maven:

```bash
./mvnw test
```

---

## API Documentation
La documentación completa de los endpoints está en `API_DOCUMENTATION.md` en la raíz del repo. Incluye ejemplos de peticiones (curl), cuerpos de request y formatos de respuesta.

---

## Buenas prácticas y decisiones técnicas que apliqué
A continuación resumo las decisiones de diseño más relevantes en el código — útiles si estás revisando el repositorio como recruiter o para una entrevista técnica:

- Arquitectura por capas: controllers → services → repositories → models/DTOs. Mantengo los controladores ligeros y delego la lógica de negocio a los servicios.
- Modularización del servicio de productos: la paginación, validaciones y mapeo a DTOs se realizan dentro de `ProductService`. Esto permite reutilizar la lógica y mantener los endpoints simples.
- Paginación y mapeo a DTOs: devuelvo `Page<T>` (Spring Data) con mapeo a `ProductResponseDTO` mediante métodos como `fromEntity(...)`. Evita exponer las entidades JPA directamente.
- Búsqueda eficiente con JPQL: para consultas que combinan filtro por categoría y término de búsqueda implementé consultas JPQL personalizadas en el repositorio. Delega el trabajo a la base de datos y evita traer objetos innecesarios a memoria.
- Manejo de excepciones: uso excepciones específicas donde corresponde (`NotFoundException`, `CategoryExistException`, etc.) y un handler central para normalizar respuestas de error.

Beneficios: más rendimiento (filtrado en BD), mejor mantenibilidad y código más fácil de auditar.

---

## Qué puedes revisar rápidamente (si eres recruiter o evaluador técnico)
- `src/main/java/.../controllers` — endpoints y contratos HTTP.
- `src/main/java/.../services` — reglas de negocio, paginación y mapeo a DTOs.
- `src/main/java/.../repositories` — consultas JPQL y queries personalizadas.
- `src/main/java/.../dto` — modelos de respuesta (qué verá el frontend).
- `src/main/resources/application.properties` — configuración local por defecto.

---

## Licencia y autor
- Autor: Gustavo Alexander Vásquez More
