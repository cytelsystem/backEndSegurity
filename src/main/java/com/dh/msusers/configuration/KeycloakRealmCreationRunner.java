package com.dh.msusers.configuration;


import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmsResource;
import org.keycloak.representations.idm.RealmRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class KeycloakRealmCreationRunner implements CommandLineRunner {

    @Value("${dh.keycloak.auth-server-url}")
    private String authServerUrl;

//    @Value("${keycloak.realm}")
//    private String realm;
//
//    @Value("${keycloak.username}")
//    private String username;
//
//    @Value("${keycloak.password}")
//    private String password;
//
//    @Value("${keycloak.client-id}")
//    private String clientId;
//
//    @Value("${keycloak.client-secret}")
//    private String clientSecret;

    @Override
    public void run(String... args)  {
//         Crear instancia de Keycloak Admin Client
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(authServerUrl)
                .realm("master")
                .username("admin")
                .password("admin")
                .clientId("admin-cli")
                .clientSecret("EjxBmhxn665tsv0nchzOzoKfS9VhRQDc")
                .build();
//
//        // Crear el reino "cytelsystem"
        RealmsResource realmsResource = keycloak.realms();
        RealmRepresentation realmRepresentation = new RealmRepresentation();
        realmRepresentation.setRealm("cytelsystem");
        realmsResource.create(realmRepresentation);

        System.out.println("Reino 'cytelsystem' creado exitosamente.");
    }
}
