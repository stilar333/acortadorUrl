
# ShortLink API

Esta API utiliza JWT para la autorización del uso de endpoints privados. Utiliza MongoDB y los links se generan por usuario con su respectivo token de acceso.

## Response del Backend

La respuesta del backend siempre será un `MessageModel`:

```json
{
    "ok": boolean,
    "message": "String",
    "value": object | null
}
```

## Endpoints Públicos

### Dar de Alta a un Usuario

**Endpoint:** `POST /auth/register`

**Request Body:**

```json
{
    "email": "stilar.sj@gmail.com",
    "password": "123456",
    "username": "Stilar"
}
```

**Posibles Respuestas:**

- **Code 400:** "Request body mal estructurado"
- **Code 409:** "Hay un elemento duplicado en la Base de Datos"
- **Code 200:** "Creado Correctamente"

### Loguear Usuario

**Endpoint:** `POST /auth/login`

**Request Body:**

```json
{
    "username": "stilar.sj@gmail.com | Stilar",
    "password": "123456"
}
```

**Posibles Respuestas:**

- **Code 400:** "Request body mal estructurado o credenciales Incorrectas"
- **Code 401:** "No se encontró el Usuario"
- **Code 200:** "Inicio correctamente sesión y en el value de msgModel devuelve el token"

### Acceder a Link Acortado

**Endpoint:** `GET /{shortUrl}` (ejemplo: `localhost:8080/qWAs71`)

Aquí solo se manda el short URL extraído del `LinkModel.shortUrl` y te redirecciona a la página original.

**Posibles Respuestas:**

- **Code 404:** "No se encontró el link"
- **Code 302:** "Se encontró el link"

## Endpoints Privados

### Dar de Alta un Link

**Endpoint:** `POST /api/v1/links/create`

**Request Body:**

```json
{
    "originalUrl": "https://github.com/stilar333",
    "linkName": "nombreLink"
}
```

> Este `LinkModel` tiene más atributos como `idUser`, pero el servicio se encarga de extraerlo y agregarlo así como los demás atributos.

**Posibles Respuestas:**

- **Code 400:** "Request body mal estructurado"
- **Code 401:** "No está autorizado"
- **Code 200:** "El link se guardó correctamente"

### Actualizar un Link

**Endpoint:** `PUT /api/v1/links/update`

**Request Body:**

```json
{
    "id": "12312837sdas098f9812",
    "originalUrl": "https://github.com/stilar333",
    "linkName": "nombreLink"
}
```

> Aquí se debe mandar el objeto `LinkModel` con solo los atributos a actualizar, pero incluyendo sí o sí su `id` para efectuar la actualización.

**Posibles Respuestas:**

- **Code 400:** "Request body mal estructurado"
- **Code 401:** "No está autorizado"
- **Code 404:** "No se encontró el link"
- **Code 200:** "El link se actualizó correctamente"

## Otros Endpoints



---

Para más información sobre la API y su uso, consulta el codigo completo.

```
