package com.dh.runner.configuracion;



import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.*;
import org.keycloak.representations.idm.authorization.ScopeRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class KeycloakRealmCreationRunner implements CommandLineRunner {

    //**********************************************************************************//

    @Autowired
    private Keycloak keycloak;


    //**********************************************************************************//

    @Value("${dh.keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${dh.nuevoReino.realm}")
    private String realm;

    @Value("${dh.nuevoReino.new_realm_roles}")
    private String roles;
    @Value("${dh.nuevoReino.new_username1}")
    private String new_username1;
    @Value("${dh.nuevoReino.new_password1}")
    private String new_password1;

    @Value("${dh.nuevoReino.new_clientScopeName}")
    private String clientScopeName;


    //**********************************************************************************//

    @Override
    public void run(String... args) {

        createRealm();
        createRealmRole();
        createUser();
        createClientScope();


    }

    private void createRealm() {
        RealmsResource realmsResource = keycloak.realms();
        RealmRepresentation realmRepresentation = new RealmRepresentation();
        realmRepresentation.setRealm(realm);
        realmRepresentation.setEnabled(true);
        realmRepresentation.setRegistrationAllowed(false);
        realmsResource.create(realmRepresentation);
        System.out.println("Reino" +  realm + "creado exitosamente.");
    }

    private void createRealmRole() {
        RealmResource realmResource = keycloak.realm(realm);
        RolesResource rolesResource = realmResource.roles();
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(roles);
        rolesResource.create(roleRepresentation);
        System.out.println("Roll" +  roles + "creado exitosamente.");
    }

    private void createUser() {


        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        UserRepresentation user = new UserRepresentation();
        user.setUsername(new_username1);
        user.setEnabled(true);
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(new_password1);
        credential.setTemporary(false);
        user.setCredentials(Arrays.asList(credential));
        usersResource.create(user);

        System.out.println("Usuario" +  roles + "creado exitosamente.");


    }

    private void createClientScope() {
        RealmResource realmResource = keycloak.realm(realm);
        ClientScopeRepresentation clientScopeRepresentation = new ClientScopeRepresentation();
        clientScopeRepresentation.setName(clientScopeName);
        clientScopeRepresentation.setProtocol("openid-connect");
        clientScopeRepresentation.setAttributes(Collections.emptyMap());
        Response clientScopeResource = realmResource.clientScopes().create(clientScopeRepresentation);
        System.out.println("Client Scope " + clientScopeName + " creado exitosamente.");

        Map<String, String> attributes = new HashMap<>();
        attributes.put("type", "default");
        clientScopeRepresentation.setAttributes(attributes);

//        ScopeRepresentation scopeRepresentation = new ScopeRepresentation();
//        scopeRepresentation.setName("grupos");
//        scopeRepresentation.setDisplayName("Grupos");
//        clientScopeResource.addScope(scopeRepresentation);
//        System.out.println("Scope 'grupos' agregado exitosamente al Client Scope.");
    }



}
