package com.dh.msusers;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class UsersMsApplication {

	public static void main(String[] args) {

		SpringApplication.run(UsersMsApplication.class, args);
	}

	@Value("${dh.keycloak.auth-server-url}")
	private String authServerUrl;


	@Bean
	public Keycloak keycloak() {
		return KeycloakBuilder.builder()
		.serverUrl(authServerUrl)
		.realm("master")
		.username("admin")
		.password("admin")
		.clientId("admin-cli")
		.build();

	}





}
