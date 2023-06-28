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
import java.util.*;

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


    private final String mapperName = "GroupPersonales";
    private final String mapperType = "oidc-group-membership-mapper";
    private final String scopete = "roles";
    private final boolean addToIdToken = true;
    private final boolean addToAccessToken = true;


    //**********************************************************************************//

    @Override
    public void run(String... args) {

        createRealm();
//        createRealmRole();
        createUser();
//        createClientScope();
//        createClientScopeMapper();
        createClientScopeMapperxxxx();


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


    }


    private void createClientScopeMapper() {
        RealmResource realmResource = keycloak.realm(realm);
        ClientScopeRepresentation clientScopeRepresentation = new ClientScopeRepresentation();
        clientScopeRepresentation.setName("clienteScopeMapperHJM");
        clientScopeRepresentation.setProtocol("openid-connect");
        clientScopeRepresentation.setAttributes(Collections.emptyMap());

        Map<String, String> attributes = new HashMap<>();
        attributes.put("type", "default");
        clientScopeRepresentation.setAttributes(attributes);

        ProtocolMapperRepresentation mapper = new ProtocolMapperRepresentation();
        mapper.setName(mapperName);
        mapper.setProtocol("openid-connect");
        mapper.setProtocolMapper(mapperType);


        mapper.setConfig(new HashMap<>());
        mapper.getConfig().put("claim.name", "testgroups");
        mapper.getConfig().put("full.path", "false");
        mapper.getConfig().put("id.token.claim", "true");
        mapper.getConfig().put("access.token.claim", "true");
        mapper.getConfig().put("userinfo.token.claim", "false");


        clientScopeRepresentation.setProtocolMappers(Collections.singletonList(mapper));


        Response clientScopeResource = realmResource.clientScopes().create(clientScopeRepresentation);

        System.out.println("Client Scope " + "clienteScopeMapperHJM" + " creado exitosamente.");


    }







    private ProtocolMapperRepresentation createProtocolMapper() {
        ProtocolMapperRepresentation mapper = new ProtocolMapperRepresentation();
        mapper.setName(mapperName);
        mapper.setProtocol("openid-connect");
        mapper.setProtocolMapper(mapperType);

        Map<String, String> config = new HashMap<>();
        config.put("claim.name", "testgroups");
        config.put("full.path", "false");
        config.put("id.token.claim", "true");
        config.put("access.token.claim", "true");
        config.put("userinfo.token.claim", "false");
        mapper.setConfig(config);

        return mapper;
    }




    private void createClientScopeMapperxxxx() {

        ClientScopeRepresentation clientScopeResource = keycloak.realm(realm).clientScopes().findAll().stream().filter(clientScope -> clientScope.getName().equals("roles")).findFirst().get();

        if (clientScopeResource != null) {
            // Crear el mapper
            ProtocolMapperRepresentation mapper = new ProtocolMapperRepresentation();
            mapper.setName(mapperName);
            mapper.setProtocol("openid-connect");
            mapper.setProtocolMapper(mapperType);

            mapper.setConfig(new HashMap<>());
            mapper.getConfig().put("claim.name", "testgroups");
            mapper.getConfig().put("full.path", "false");
            mapper.getConfig().put("id.token.claim", "true");
            mapper.getConfig().put("access.token.claim", "true");
            mapper.getConfig().put("userinfo.token.claim", "false");

            // Agregar el mapper al scope "roles"
            keycloak.realm(realm).clientScopes().get(clientScopeResource.getId()).getProtocolMappers().createMapper(mapper);
        } else {
            System.out.println("No se encontró el scope 'roles'.");
        }


    }







}
