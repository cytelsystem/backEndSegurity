package com.dh.msusers.configuration;


import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class KeycloakRealmCreationRunner implements CommandLineRunner {

    @Autowired
    private Keycloak keycloak;

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

        createRealm();
        createRealmRole();
        createUser();

    }

    private void createRealm() {
        RealmsResource realmsResource = keycloak.realms();
        RealmRepresentation realmRepresentation = new RealmRepresentation();
        realmRepresentation.setRealm("cytelsystem");
        realmRepresentation.setEnabled(true);
        realmRepresentation.setRegistrationAllowed(false);
        realmsResource.create(realmRepresentation);
        System.out.println("Reino 'cytelsystem' creado exitosamente.");
    }

    private void createRealmRole() {
        RealmResource realmResource = keycloak.realm("cytelsystem");
        RolesResource rolesResource = realmResource.roles();
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName("USER");
        rolesResource.create(roleRepresentation);
    }

    private void createUser() {

        String realmName = "cytelsystem";
        String username = "intadmin";
        String roleName = "USER";


        RealmResource realmResource = keycloak.realm("cytelsystem");
        UsersResource usersResource = realmResource.users();
        UserRepresentation user = new UserRepresentation();
        user.setUsername("intadmin");
        user.setEnabled(true);
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue("password");
        credential.setTemporary(false);
        user.setCredentials(Arrays.asList(credential));
        usersResource.create(user);


    }




}
