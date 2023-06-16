package com.dh.msusers.configuration;


import com.dh.msusers.services.RealmService;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class KeycloakRealmCreationRunner implements CommandLineRunner {

    @Autowired
    private Keycloak keycloak;

    final RealmService RealmService;



    @Value("${dh.keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${dh.nuevoReino.realm}")
    private String realm;

    public KeycloakRealmCreationRunner(com.dh.msusers.services.RealmService realmService) {
        RealmService = realmService;
    }


    @Override
    public void run(String... args)  {


        RealmService.createRealm(realm);

//        createRealm();
//        createRealmRole();
//        createUser();



    }

//    private void createRealm() {
//        RealmsResource realmsResource = keycloak.realms();
//        RealmRepresentation realmRepresentation = new RealmRepresentation();
//        realmRepresentation.setRealm("cytelsystem");
//        realmRepresentation.setEnabled(true);
//        realmRepresentation.setRegistrationAllowed(false);
//        realmsResource.create(realmRepresentation);
//        System.out.println("Reino 'cytelsystem' creado exitosamente.");
//    }

//    private void createRealmRole() {
//        RealmResource realmResource = keycloak.realm("cytelsystem");
//        RolesResource rolesResource = realmResource.roles();
//        RoleRepresentation roleRepresentation = new RoleRepresentation();
//        roleRepresentation.setName("USER");
//        rolesResource.create(roleRepresentation);
//    }

//    private void createUser() {
//
//        String realmName = "cytelsystem";
//        String username = "intadmin";
//        String roleName = "USER";
//
//
//        RealmResource realmResource = keycloak.realm("cytelsystem");
//        UsersResource usersResource = realmResource.users();
//        UserRepresentation user = new UserRepresentation();
//        user.setUsername("intadmin");
//        user.setEnabled(true);
//        CredentialRepresentation credential = new CredentialRepresentation();
//        credential.setType(CredentialRepresentation.PASSWORD);
//        credential.setValue("password");
//        credential.setTemporary(false);
//        user.setCredentials(Arrays.asList(credential));
//        usersResource.create(user);
//
//
//    }




}
