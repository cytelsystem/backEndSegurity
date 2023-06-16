package com.dh.msusers.repository;

import com.dh.msusers.modelDTO.UserDTO;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class KeyCloakUserRepository implements IUserRepository{

    @Autowired
    private Keycloak keycloak;

    @Value("${dh.nuevoReino.realm}")
    private String realm;

    //******************Metodos Implementados de la interface***********************//

    @Override
    public UserDTO createUser(UserDTO user) {
        UserRepresentation newUser = new UserRepresentation();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setEnabled(true);
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue("password");
        credential.setTemporary(false);
        newUser.setCredentials(Arrays.asList(credential));



        // Establecer otros atributos necesarios para el usuario

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

        keycloak.realm(realm).users().create(newUser);


        return toUser(newUser);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<UserRepresentation> users = keycloak.realm(realm).users().list();
        return users.stream().map(this::toUser).collect(Collectors.toList());


    }


    @Override
    public List<UserDTO> findByFirName(String name) {
        List<UserRepresentation> users = keycloak.realm(realm).users().search(name);
        return users.stream().map(UserRepresentation -> toUser(UserRepresentation)).collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(String id) {

        UserRepresentation user = keycloak.realm(realm).users().get(id).toRepresentation();

        return toUser(user);
    }

    @Override
    public UserDTO updateNationality(String id, String nationality) {
        UserResource userResource = keycloak.realm(realm).users().get(id);
        UserRepresentation user = userResource.toRepresentation();
        Map<String,List<String>> attributes = new HashMap<>();
        attributes.put("nacionalidad", List.of(nationality));
        user.setAttributes(attributes);
        userResource.update(user);

        return toUser(user);
    }

    //**********************************************************************************

    private UserDTO toUser(UserRepresentation userRepresentation) {
        String nationality = null;
        if (userRepresentation != null && userRepresentation.getAttributes() != null) {
            List<String> nationalityList = userRepresentation.getAttributes().get("nacionalidad");
            if (nationalityList != null && !nationalityList.isEmpty()) {
                nationality = nationalityList.get(0);
            }
        }

        return new UserDTO(
                userRepresentation.getId(),
                userRepresentation.getUsername(),
                userRepresentation.getEmail(),
                userRepresentation.getFirstName(),
                nationality);
    }



}


