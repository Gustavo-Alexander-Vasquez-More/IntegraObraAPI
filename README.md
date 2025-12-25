# IntegraObra API (integraobra-api-rest)

API REST para la gestión de productos y categorías del proyecto IntegraObra.

[Ver documentación detallada de la API (endpoints, ejemplos)](./API_DOCUMENTATION.md)

---

## Estado / Deploy
- URL (producción): https://integraobraapi-production.up.railway.app

> Nota: esta documentación asume que la API expone los endpoints descritos en `API_DOCUMENTATION.md`.

---

## Badges (opcionales)
Puedes añadir badges aquí (CI, build, cobertura) si integras pipelines de GitHub Actions, GitLab CI, etc.

---

## Requisitos
- Java 17+ (ajusta según tu `pom.xml` si usas otra versión)
- Maven (o usar el wrapper `./mvnw` incluido)
- Docker (opcional, para contenedores)

---

## Quick start (desarrollo)
Clona el repositorio y ejecuta la aplicación localmente con Maven (usa el wrapper incluido):

```bash
git clone <tu-repo-url>
cd integraobra-api-rest
./mvnw clean package
./mvnw spring-boot:run
```

Por defecto la aplicación arranca en el puerto configurado en `src/main/resources/application.properties` (normalmente `8080`). Accede como: `http://localhost:8080`.

---

## Build y ejecución de producción (jar)
Para generar el jar y ejecutarlo:

```bash
./mvnw clean package -DskipTests
java -jar target/integraobra-api-rest-0.0.1-SNAPSHOT.jar
```

---

## Docker
El repositorio incluye un `Dockerfile` en la raíz. Instrucciones básicas para construir y ejecutar la imagen Docker:

1) Construir la imagen (desde la raíz del proyecto):

```bash
docker build -t integraobra-api-rest:latest .
```

2) Ejecutar el contenedor (mapear puerto 8080):

```bash
docker run -d --name integraobra-api -p 8080:8080 integraobra-api-rest:latest
```

3) Si necesitas pasar variables de entorno (por ejemplo configuración de BD), usa `-e` o un archivo `.env` con `--env-file`:

```bash
docker run -d --name integraobra-api --env-file .env -p 8080:8080 integraobra-api-rest:latest
```

Notas:
- Ajusta los valores de la base de datos y otros parámetros en `src/main/resources/application.properties` o a través de variables de entorno.
- Si usas Docker Compose, crea un `docker-compose.yml` que configure la base de datos y la API.

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

Enlace rápido: `./API_DOCUMENTATION.md`

---

## Buenas prácticas y recomendaciones
- La API devuelve errores de validación (400) como un map campo->mensaje; otros errores usan `ErrorResponse` con `{ title, message, status }`.
- Para crear un producto y asignarle categorías desde el frontend: primero `POST /api/products` y luego crear las relaciones con `POST /api/category-details` por cada categoría seleccionada.
- Para PATCH utiliza `PATCH /api/products/{id}` enviando solo los campos a modificar.

---

## Contribuir
Si quieres contribuir:
- Abre un issue describiendo el problema o la mejora.
- Crea un branch con prefijo `feature/` o `fix/` y envía un pull request con una descripción clara.

---

## Troubleshooting
- Si ves `500 Internal Server Error` al eliminar o crear recursos, revisa los logs del backend. En caso de conflictos por integridad referencial el `GlobalExceptionHandler` puede devolver 409 o 500 dependiendo de la excepción lanzada.
- Si no recibes el código de error esperado (p. ej. 409 al crear una categoría existente), añade handlers específicos para `CategoryExistException` o `ProductExistException` en `GlobalExceptionHandler`.

---

## Licencia y autor
- Autor: IntegraObra (ajusta según corresponda)
- Licencia: añade un `LICENSE` si quieres publicar el proyecto públicamente.

---

Si quieres, puedo:
- añadir badges (CI/build) y un `docker-compose.yml` de ejemplo,
- o crear un `Makefile` con comandos comunes (`make build`, `make run`, `make docker-build`).

Dime qué prefieres y lo hago.
