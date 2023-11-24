package com.dh.runner.configuracion;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConexion {

    @Value("${dh.keycloak.auth-server-url}")
    private String serverUrl;
    @Value("${dh.master.realm}")
    private String realm;
    @Value("${dh.master.username}")
    private String username;
    @Value("${dh.master.password}")
    private String password;
    @Value("${dh.master.clientId}")
    private String clientId;



    @Bean
    public Keycloak buildClient() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .username(username)
                .password(password)
                .clientId(clientId)
                .build();

    }
}