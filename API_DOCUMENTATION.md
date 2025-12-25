# API Documentation — integraobra-api-rest

Base URL (ejemplos)
- Placeholder (usar en peticiones): `{{API_BASE_URL}}`  (reemplaza por tu URL: p. ej. `http://localhost:8080` o la URL de staging que uses internamente)
- Local (dev): `http://localhost:8080`  (ajusta según tu servidor)

Formato de errores
- Validación (400): devuelve un objeto JSON con los campos y mensajes de validación, por ejemplo:

```json
{
  "name": "El nombre no puede estar vacío",
  "sku": "El SKU no puede estar vacío"
}
```

- ErrorResponse (404, 409, 500): objeto con { title, message, status }

```json
{
  "title": "Not Found",
  "message": "Producto no encontrado con el ID proporcionado.",
  "status": 404
}
```

Headers comunes
- Accept: application/json
- Content-Type: application/json (cuando envías JSON)
- Authorization: Bearer <token> (si aplicas autenticación más adelante)

---

## Endpoints

### 1) Productos
Base: `/api/products`

1.1 Crear producto
- Método: POST
- Ruta: `/api/products`
- Body: `CreateProductRequestDTO` (JSON)

Ejemplo de body:
```json
{
  "name": "REVOLVEDORA DE 1 SACO.",
  "cardImageUrl": "https://.../img.jpg",
  "sku": "REV1828",
  "stock": 0,
  "description": "Revolvedora de cemento de 1 Saco ha gasolina...",
  "tags": ["renta revolvedora","revolvedora"],
  "salePrice": null,
  "rentPrice": 600.00,
  "isForSale": false,
  "priceVisibleForRent": true,
  "priceVisibleForSale": false
}
```
- Respuestas:
  - 201 Created: retorna la entidad `Product` creada (entidad completa)
  - 400 Bad Request: errores de validación (map campo -> mensaje)
  - 409 Conflict: si el SKU existe (ver nota sobre handlers)

curl:
```
curl -X POST "{{API_BASE_URL}}/api/products" \
  -H "Accept: application/json" -H "Content-Type: application/json" \
  -d '<JSON body aquí>'
```

---

1.2 Obtener producto por ID
- Método: GET
- Ruta: `/api/products/{productId}`
- Respuesta 200: `ProductResponseDTO` (campos: name, cardImageUrl, sku, stock, description, tags, salePrice, rentPrice, isForSale, isForRent, priceVisibleForRent, priceVisibleForSale)

Ejemplo:
```
curl -X GET "{{API_BASE_URL}}/api/products/123" -H "Accept: application/json"
```

---

1.3 Eliminar producto
- Método: DELETE
- Ruta: `/api/products/{productId}`
- Respuestas:
  - 200 OK: devuelve `Boolean` (true si eliminado)
  - 404 Not Found: si no existe
  - 500 Internal Server Error: problemas al eliminar (integridad referencial u otro error interno)

Ejemplo:
```
curl -X DELETE "{{API_BASE_URL}}/api/products/2" -H "Accept: application/json"
```

---

1.4 Actualizar producto (PATCH parcial)
- Método: PATCH
- Ruta: `/api/products/{productId}`
- Body: `UpdateRequestProductDTO` — todos los campos opcionales; envía solo los que quieres cambiar.

Ejemplo (solo cambiar nombre y rentPrice):
```json
{
  "name": "REVOLVEDORA 1 SACO MOD",
  "rentPrice": 650.00
}
```
- Respuestas:
  - 200 OK: `ProductResponseDTO` actualizado
  - 400 Bad Request: validación fallida
  - 404 Not Found: si no existe

curl:
```
curl -X PATCH "{{API_BASE_URL}}/api/products/123" \
  -H "Accept: application/json" -H "Content-Type: application/json" \
  -d '{"name":"NUEVO NOMBRE"}'
```

---

1.5 Búsqueda paginada para panel de gestión
- Método: GET
- Ruta: `/api/products/search`
- Query params:
  - `page` (int, 0-based)
  - `size` (int)
  - `sort` (ej.: `sort=name,asc`)
  - `searchTerm` (string, opcional) — busca en `sku` o `name`, case-insensitive
  - `categoryId` (Long, opcional) — id numérico de la categoría

Comportamiento:
- Si `searchTerm` y `categoryId` son omitidos → devuelve todos los productos paginados.
- Si solo `searchTerm` → filtra por `sku` o `name` (ignore case).
- Si solo `categoryId` → devuelve productos asociados a esa categoría.
- Si ambos → devuelve productos de la categoría que además cumplen el `searchTerm`.

Ejemplos:
```
curl -X GET "{{API_BASE_URL}}/api/products/search?page=0&size=20&sort=name,asc" -H "Accept: application/json"

curl -X GET "{{API_BASE_URL}}/api/products/search?page=0&size=20&searchTerm=martillo" -H "Accept: application/json"

curl -X GET "{{API_BASE_URL}}/api/products/search?page=0&size=20&categoryId=3&searchTerm=martillo" -H "Accept: application/json"
```

Respuesta: objeto `Page` con `content` (lista de `ProductCardPanelResponseDTO`), `totalElements`, `totalPages`, `number`, `size`, etc.

---

### 2) Categorías
Base: `/api/categories`

2.1 Crear categoría
- Método: POST
- Ruta: `/api/categories`
- Body: `CreateCategoryRequestDTO`

Ejemplo:
```json
{ "name": "HERRAMIENTAS" }
```
- Respuestas:
  - 200 OK: devuelve la entidad `Category` creada
  - 400 Bad Request: validación
  - 409 Conflict: si ya existe la categoría (ver nota sobre handlers)

curl:
```
curl -X POST "{{API_BASE_URL}}/api/categories" \
  -H "Accept: application/json" -H "Content-Type: application/json" \
  -d '{"name":"HERRAMIENTAS"}'
```

---

2.2 Eliminar categoría
- Método: DELETE
- Ruta: `/api/categories/{id}`
- Respuestas:
  - 200 OK: devuelve boolean (true si eliminado)
  - 409 Conflict: si existen dependencias y no puede eliminar (DeletionConflictException)
  - 404 Not Found: si no existe

curl:
```
curl -X DELETE "{{API_BASE_URL}}/api/categories/2" -H "Accept: application/json"
```

---

2.3 Obtener todas las categorías
- Método: GET
- Ruta: `/api/categories`
- Respuesta: 200 OK con `List<Category>`

curl:
```
curl -X GET "{{API_BASE_URL}}/api/categories" -H "Accept: application/json"
```

---

2.4 Actualizar categoría
- Método: PUT
- Ruta: `/api/categories/{id}`
- Body: `UpdateCategoryRequestDTO` (name requerido)

Ejemplo:
```json
{ "name": "HERRAMIENTAS Y ACCESORIOS" }
```

curl:
```
curl -X PUT "{{API_BASE_URL}}/api/categories/2" \
  -H "Accept: application/json" -H "Content-Type: application/json" \
  -d '{"name":"HERRAMIENTAS Y ACCESORIOS"}'
```

---

### 3) Category Details (tabla intermedia)
Base: `/api/category-details`

3.1 Crear asociación producto-categoría
- Método: POST
- Ruta: `/api/category-details`
- Body: `CreateCategoryDetailRequestDTO`

Ejemplo:
```json
{ "categoryId": 2, "productId": 1 }
```
- Respuestas:
  - 201 Created: devuelve `CategoryDetail` creada
  - 400 Bad Request: si faltan campos
  - 404 Not Found: si categoryId o productId no existen

curl:
```
curl -X POST "{{API_BASE_URL}}/api/category-details" \
  -H "Accept: application/json" -H "Content-Type: application/json" \
  -d '{"categoryId":2,"productId":1}'
```

---

## Notas y recomendaciones para frontend
- `categoryId` en consultas/peticiones debe enviarse como número (Long). No envíes el nombre cuando se espera id.
- Para crear un producto y asociarlo a categorías desde el frontend:
  1) POST `/api/products` con `CreateProductRequestDTO`.
  2) Con el `id` del producto devuelto, por cada categoría seleccionada hacer POST `/api/category-details` con `{ categoryId, productId }`.
  - Puedes hacer estas llamadas en paralelo (`Promise.all`) desde el frontend.
- PATCH `/api/products/{id}`: manda solo los campos que quieres actualizar (cualquier campo omitido no se modifica).
- Manejo de errores:
  - Validaciones -> 400 (map campo->mensaje).
  - NotFound/Conflict -> `ErrorResponse` con { title, message, status }.

### Nota importante sobre excepciones y códigos HTTP
- El proyecto define excepciones específicas: `NotFoundException`, `DeletionConflictException`, `InternalErrorException`, `ProductExistException`, `CategoryExistException`.
- El `GlobalExceptionHandler` maneja `MethodArgumentNotValidException`, `NotFoundException` (404), `DeletionConflictException` (409) e `InternalErrorException` (500 genérico).
- Si `ProductExistException` o `CategoryExistException` no están cubiertas por un handler explícito, podrían llegar como 500 Internal Server Error en lugar de 409. Si quieres que el frontend reciba 409 con un mensaje claro, añade handlers específicos para esas excepciones en el backend.

---

## Resumen rápido (copiar/pegar)
- GET /api/categories
- POST /api/categories { name }
- PUT /api/categories/{id} { name }
- DELETE /api/categories/{id}
- POST /api/products { CreateProductRequestDTO }
- GET /api/products/{id}
- PATCH /api/products/{id} { UpdateRequestProductDTO }
- DELETE /api/products/{id}
- POST /api/category-details { categoryId, productId }
- GET /api/products/search?searchTerm=&categoryId=&page=&size=&sort=

