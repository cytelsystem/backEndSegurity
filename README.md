
## en el pom la version de spring-boot debe se <version>2.6.7</version>


## Configuracion application.properties microservicio
### //********************************************************************************************************//
server.port=8086

spring.security.oauth2.client.provider.keycloak.issuer-uri=http://localhost:8080/realms/dh
spring.security.oauth2.client.registration.keycloak.client-id=backend
spring.security.oauth2.client.registration.keycloak.client-secret=CaZ4vUhbOAUuCKBV2rmVF4aUzlDpngz3
spring.security.oauth2.client.registration.keycloak.redirect-uri=http://localhost:8086/login/oauth2/code/google

## Configuracion application.yml microservicio
### //********************************************************************************************************//

server:
port: 8086

spring:
  security:
    oauth2:
      client:
        provider:
          keycloak:
            issuer-uri: http://localhost:8080/realms/dh
        registration:
          keycloak:
            client-id: backend
            client-secret: CaZ4vUhbOAUuCKBV2rmVF4aUzlDpngz3
            redirect-uri: http://localhost:8086/login/oauth2/code/google


![image](https://github.com/cytelsystem/backEndSegurity/assets/41965648/e136b159-62c2-4bf6-8d92-9a94c71351aa)

### //********************************************************************************************************//
