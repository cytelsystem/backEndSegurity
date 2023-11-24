package com.dh.runner.configuracion;



import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.*;
import org.keycloak.representations.idm.authorization.ScopeRepresentation;
import org.keycloak.util.JsonSerialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.stereotype.Component;



import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

import static org.keycloak.OAuth2Constants.CLIENT_ID;

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
    public void run(String... args) throws IOException {

        createRealm(); /*Crear realm mastrEvents*/

//        createRealmRole();

        createUser(); /*Crear usuario intadmin*/

//        createClientScope(); /*Crear un client Scope*/
//        createClientScopeMapper(); /*Crear un client Scope y adiconarle un mapper*/

        createGroup("USUARIOS"); /*Crear un grupo personal llamado USUARIOS*/
        createGroup("CREAREVENTOS"); /*Crear un grupo personal llamado CREAREVENTOS*/
        addClientScopeMapper(); /*Adicionar el grupo USUARIOS al scope roles*/
        addUserToGroup(); /*Adicionar el usuario intadmin al grupo USUARIOS*/

        createClientIDGateway(
        "api-gateway-client",
        "api-gateway-client",
        true,
        "client-secret",
        "toDBMTqzwxJsCd14cYL883YphnTcdgcb",
        true,
        false,
        true,
        false,
        "openid-connect"

        );


        createClientIDUsers(
                "clientUsers",
                "clientUsers",
                true,
                "client-secret",
                "CaZ4vUhbOAUuCKBV2rmVF4aUzlDpngz3",
                true,
                false,
                false,
                false,
                "openid-connect"

        );

        createClientIDReact(
                "react-client",
                "react-client",
                true,
                "none",
                "EaKWN0BKPfDdSD6LXdVsU3g3dIZDkjaL",
                false,
                false,
                false,
                true,
                "openid-connect"

        );


        createClientIDEvents(
                "clientEvents",
                "clientEvents",
                true,
                "client-secret",
                "D5XMwh0Se35XdFxI839xqdLTMxQ8JtEG",
                true,
                false,
                false,
                false,
                "openid-connect"

        );


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
        mapper.getConfig().put("claim.name", "group");
        mapper.getConfig().put("full.path", "false");
        mapper.getConfig().put("id.token.claim", "true");
        mapper.getConfig().put("access.token.claim", "true");
        mapper.getConfig().put("userinfo.token.claim", "false");


        clientScopeRepresentation.setProtocolMappers(Collections.singletonList(mapper));


        Response clientScopeResource = realmResource.clientScopes().create(clientScopeRepresentation);

        System.out.println("Client Scope " + "clienteScopeMapperHJM" + " creado exitosamente.");


    }




    private void addClientScopeMapper() {

        ClientScopeRepresentation clientScopeResource = keycloak.realm(realm).clientScopes().findAll().stream().filter(clientScope -> clientScope.getName().equals("roles")).findFirst().get();

        keycloak.realm(realm).clientScopes().get(clientScopeResource.getId()).getProtocolMappers().createMapper(createProtocolMapper());


    }



    private void createGroup(String grupo){
        RealmResource realmResource = keycloak.realm(realm);
        GroupsResource groupsResource = realmResource.groups();
        GroupRepresentation groupRepresentation = new GroupRepresentation();
        groupRepresentation.setName(grupo);
        groupsResource.add(groupRepresentation);

    }




    private void addUserToGroup() {


        GroupRepresentation group = keycloak.realm(realm).groups().groups().stream().filter(g -> g.getName().equals("USUARIOS")).findFirst().orElse(null);

        UserRepresentation user = keycloak.realm(realm).users().search("intadmin").get(0);

        keycloak.realm(realm).users().get(user.getId()).joinGroup(group.getId());


    }




    private ProtocolMapperRepresentation createProtocolMapper() {
        ProtocolMapperRepresentation mapper = new ProtocolMapperRepresentation();
        mapper.setName(mapperName);
        mapper.setProtocol("openid-connect");
        mapper.setProtocolMapper(mapperType);

        Map<String, String> config = new HashMap<>();
        config.put("claim.name", "group");
        config.put("full.path", "false");
        config.put("id.token.claim", "true");
        config.put("access.token.claim", "true");
        config.put("userinfo.token.claim", "false");
        mapper.setConfig(config);

        return mapper;
    }

    //***************************************************ClientGateWay************************************************************//

    private void createClientIDGateway(
            String setClientId,
            String setName,
            boolean setEnabled,
            String setClientAuthenticatorType,
            String setSecret,
            boolean setServiceAccountsEnabled,
            boolean setAuthorizationServicesEnabled,
            boolean setDirectAccessGrantsEnabled,
            boolean setPublicClient,
            String setProtocol



    )
    {
        RealmResource realmResource = keycloak.realm(realm);
        ClientsResource clientsResource = realmResource.clients();
        ClientRepresentation ClientRepresentation = new ClientRepresentation();

        //***********************************************************************************//


        ClientRepresentation.setClientId(setClientId);
        ClientRepresentation.setName(setName);
        ClientRepresentation.setEnabled(setEnabled);
        ClientRepresentation.setClientAuthenticatorType(setClientAuthenticatorType);
        ClientRepresentation.setSecret(setSecret);
        ClientRepresentation.setServiceAccountsEnabled(setServiceAccountsEnabled);
        ClientRepresentation.setAuthorizationServicesEnabled(setAuthorizationServicesEnabled);
        ClientRepresentation.setDirectAccessGrantsEnabled(setDirectAccessGrantsEnabled);
        ClientRepresentation.setPublicClient(setPublicClient);
        ClientRepresentation.setProtocol(setProtocol);
        ClientRepresentation.setRootUrl("http://localhost:9090"); /*Root URL */
        ClientRepresentation.setBaseUrl(""); /*Home URL*/
        ClientRepresentation.setAdminUrl(""); /*Admin URL*/

        List<String> urisOrigin = List.of("*");
        ClientRepresentation.setWebOrigins(urisOrigin); /*Admin URL*/

        List<String> uris = List.of("http://localhost:9090/*", "https://oauth.pstmn.io/v1/browser-callback");
        ClientRepresentation.setRedirectUris(uris);

        clientsResource.create(ClientRepresentation);

    }


    //***************************************************ClientReact************************************************************//

private void createClientIDReact(
        String setClientId,
        String setName,
        boolean setEnabled,
        String setClientAuthenticatorType,
        String setSecret,
        boolean setServiceAccountsEnabled,
        boolean setAuthorizationServicesEnabled,
        boolean setDirectAccessGrantsEnabled,
        boolean setPublicClient,
        String setProtocol
)
{
    RealmResource realmResource = keycloak.realm(realm);
    ClientsResource clientsResource = realmResource.clients();
    ClientRepresentation clientRepresentation = new ClientRepresentation();

    //***********************************************************************************//

    clientRepresentation.setClientId(setClientId);
    clientRepresentation.setName(setName);
    clientRepresentation.setEnabled(setEnabled);

    // Desactivar Client Authentication
    clientRepresentation.setClientAuthenticatorType(setClientAuthenticatorType); // "none" significa desactivado

    clientRepresentation.setSecret(setSecret);
    clientRepresentation.setServiceAccountsEnabled(setServiceAccountsEnabled);
    clientRepresentation.setAuthorizationServicesEnabled(setAuthorizationServicesEnabled);
    clientRepresentation.setDirectAccessGrantsEnabled(setDirectAccessGrantsEnabled);
    clientRepresentation.setPublicClient(setPublicClient); //setClientAuthenticatorType = none y setPublicClient = true esto desabilita Client authentication
    clientRepresentation.setProtocol(setProtocol);
    clientRepresentation.setRootUrl(""); /*Root URL */
    clientRepresentation.setBaseUrl(""); /*Home URL*/
    clientRepresentation.setAdminUrl(""); /*Admin URL*/


    List<String> urisOrigin = List.of("*");
    clientRepresentation.setWebOrigins(urisOrigin); /*Admin URL*/

    List<String> uris = List.of("http://localhost:3000/*");
    clientRepresentation.setRedirectUris(uris);

    clientsResource.create(clientRepresentation);
}


    //***************************************************ClientBackEnd************************************************************//

    private void createClientIDUsers(
            String setClientId,
            String setName,
            boolean setEnabled,
            String setClientAuthenticatorType,
            String setSecret,
            boolean setServiceAccountsEnabled,
            boolean setAuthorizationServicesEnabled,
            boolean setDirectAccessGrantsEnabled,
            boolean setPublicClient,
            String setProtocol



    )
    {
        RealmResource realmResource = keycloak.realm(realm);
        ClientsResource clientsResource = realmResource.clients();
        ClientRepresentation ClientRepresentation = new ClientRepresentation();

        //***********************************************************************************//


        ClientRepresentation.setClientId(setClientId);
        ClientRepresentation.setName(setName);
        ClientRepresentation.setEnabled(setEnabled);
        ClientRepresentation.setClientAuthenticatorType(setClientAuthenticatorType);
        ClientRepresentation.setSecret(setSecret);
        ClientRepresentation.setServiceAccountsEnabled(setServiceAccountsEnabled);
        ClientRepresentation.setAuthorizationServicesEnabled(setAuthorizationServicesEnabled);
        ClientRepresentation.setDirectAccessGrantsEnabled(setDirectAccessGrantsEnabled);
        ClientRepresentation.setPublicClient(setPublicClient);
        ClientRepresentation.setProtocol(setProtocol);
        ClientRepresentation.setRootUrl(""); /*Root URL */
        ClientRepresentation.setBaseUrl(""); /*Home URL*/
        ClientRepresentation.setAdminUrl(""); /*Admin URL*/

        List<String> urisOrigin = List.of("/*");
        ClientRepresentation.setWebOrigins(urisOrigin); /*Admin URL*/

        List<String> uris = List.of("/*");
        ClientRepresentation.setRedirectUris(uris);

        clientsResource.create(ClientRepresentation);

    }



    //****************************************************ClientBills************************************************************//

    private void createClientIDEvents(
            String setClientId,
            String setName,
            boolean setEnabled,
            String setClientAuthenticatorType,
            String setSecret,
            boolean setServiceAccountsEnabled,
            boolean setAuthorizationServicesEnabled,
            boolean setDirectAccessGrantsEnabled,
            boolean setPublicClient,
            String setProtocol



    )
    {
        RealmResource realmResource = keycloak.realm(realm);
        ClientsResource clientsResource = realmResource.clients();
        ClientRepresentation ClientRepresentation = new ClientRepresentation();

        //***********************************************************************************//


        ClientRepresentation.setClientId(setClientId);
        ClientRepresentation.setName(setName);
        ClientRepresentation.setEnabled(setEnabled);
        ClientRepresentation.setClientAuthenticatorType(setClientAuthenticatorType);
        ClientRepresentation.setSecret(setSecret);
        ClientRepresentation.setServiceAccountsEnabled(setServiceAccountsEnabled);
        ClientRepresentation.setAuthorizationServicesEnabled(setAuthorizationServicesEnabled);
        ClientRepresentation.setDirectAccessGrantsEnabled(setDirectAccessGrantsEnabled);
        ClientRepresentation.setPublicClient(setPublicClient);
        ClientRepresentation.setProtocol(setProtocol);
        ClientRepresentation.setRootUrl(""); /*Root URL */
        ClientRepresentation.setBaseUrl(""); /*Home URL*/
        ClientRepresentation.setAdminUrl(""); /*Admin URL*/

        List<String> urisOrigin = List.of("/*");
        ClientRepresentation.setWebOrigins(urisOrigin); /*Admin URL*/

        List<String> uris = List.of("/*");
        ClientRepresentation.setRedirectUris(uris);

        clientsResource.create(ClientRepresentation);

    }


    //********************************************************************************************************************************//



    private void clienteGet() throws IOException {


//        ClientRepresentation clientxxx = keycloak.realm(realm).clients().findByClientId("backend").get(0);

//        keycloak.realm(realm).clients().get("backend").toRepresentation().setAuthorizationServicesEnabled(true);

//        keycloak.realm(realm).clients().findByClientId("backend").get(0).setDirectAccessGrantsEnabled(false);







//        keycloak.realm(realm) // obtener el objeto del reino Keycloak
//                .clients() // obtener la lista de clientes en el reino
//                .get("backend") // obtener la lista de clientes en el reino
//                .toRepresentation() // obtener el cliente como una representación
//                .setAuthorizationServicesEnabled(true); // habilitar los servicios de autorización para el cliente


        // Get the realm object for the client
        RealmResource realmResource = keycloak.realm(realm);

        // Find the client by ID
        ClientRepresentation clientRepresentation = realmResource.clients().findByClientId("backend").get(0);

        // Set the direct access grant enabled value to false
//        clientRepresentation.setDirectAccessGrantsEnabled(false);
        clientRepresentation.setAuthorizationServicesEnabled(false);

        // Update the client using the ClientResource
        ClientResource clientResource = realmResource.clients().get(clientRepresentation.getId());
        clientResource.update(clientRepresentation);

        ClientRepresentation clientxxx = keycloak.realm(realm).clients().findByClientId("backend").get(0);


        String json = JsonSerialization.writeValueAsString(clientxxx);
        System.out.println(json);

    }









}




//******************************************************************************************//
