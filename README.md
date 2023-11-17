

# Configuración del Entorno

## Instalar Docker

Asegúrate de tener Docker instalado en tu máquina.

## Instalar Keycloak con Docker

```bash
docker run -p 8082:8080 -d --name keycloak -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:21.0.1 start-dev
```

Inicia el contenedor de Keycloak si no está iniciado:

```bash
docker start keycloak
```

Verifica la instalación accediendo a [localhost:8082](http://localhost:8082). El usuario de administrador de Keycloak es "admin" y la contraseña es "admin".

## Instalar MySQL con Docker Compose

Crea un archivo `docker-compose.yml` con el siguiente contenido:

```yaml
version: "3.1"
services:
  db:
    image: mysql:8.0.32
    container_name: mysql
    restart: always
    environment:
      MYSQL_DATABASE: db
      MYSQL_USER: intadmin
      MYSQL_PASSWORD: 5086
      MYSQL_ROOT_PASSWORD: root
    ports:
      - 3306:3306
    volumes:
      - my-db:/var/lib/mysql
volumes:
  my-db:
```

Ejecuta el siguiente comando para levantar MySQL:

```bash
docker-compose up
```

## Descargar el Proyecto

Clona el repositorio:

```bash
git clone https://github.com/cytelsystem/backEndSegurity.git
```

Cambiate a la rama `feign-Final-SQL`:

```bash
git checkout feign-Final-SQL
```

# Levantar los Microservicios en el siguiente orden

1. **ms-discovery:** `mvn spring-boot:run`
2. **runner:** `mvn spring-boot:run` **runner solo se levanta la primera vez para crear el reino y configuracion inicial**
3. **ms-gateway:** `mvn spring-boot:run`
4. **users-service:** `mvn spring-boot:run`
5. **ms-bills:** `mvn spring-boot:run`

# Configuración en Postman

## Autorización

- Tipo: OAuth 2.0
- "Header Prefix": Bearer

### Configuracion para obtener un token Nuevo

- Token Name: msbills
- Grant type: Authorization Code
- Callback URL: [http://localhost:9090/*](http://localhost:9090/*)
- Auth URL: [http://localhost:8082/realms/dh/protocol/openid-connect/auth](http://localhost:8082/realms/dh/protocol/openid-connect/auth)
- Access Token URL: [http://localhost:8082/realms/dh/protocol/openid-connect/token](http://localhost:8082/realms/dh/protocol/openid-connect/token)
- Client ID: api-gateway-client
- Client Secret: toDBMTqzwxJsCd14cYL883YphnTcdgcb
- Scope: openid
- Client Authentication: Send as Basic Auth header

### Auth Request

- `redirect_url`: [http://localhost:9090/*](http://localhost:9090/*)

# Crear una factura
## En el Body (raw y json)

URL: `POST http://localhost:9090/bills/crear`

El campo `customerBill` es el ID del usuario creado en Keycloak.

```json
{
  "customerBill": "99ce7a6f-a87a-4325-835e-7fb31c753b52",
  "productBill": "vehiculo",
  "totalPrice": 1520000
}
```

¡Listo! Ahora deberías tener todo configurado correctamente.

# imagen de postman
![image](https://github.com/cytelsystem/backEndSegurity/assets/41965648/a6881fc6-edfa-48cf-ab8f-cb4c7729dafc)
```







