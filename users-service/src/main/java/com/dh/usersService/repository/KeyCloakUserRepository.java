package com.dh.usersService.repository;


import com.dh.usersService.model.UserDTO;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.ws.rs.core.Response;
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

//    @Override
//    public UserDTO createUser(UserDTO user) {
//        UserRepresentation newUser = new UserRepresentation();
//        newUser.setUsername(user.getUsername());
//        newUser.setEmail(user.getEmail());
//        newUser.setFirstName(user.getFirstName());
//        newUser.setEnabled(true);
//        CredentialRepresentation credential = new CredentialRepresentation();
//        credential.setType(CredentialRepresentation.PASSWORD);
//        credential.setValue("password");
//        credential.setTemporary(false);
//        newUser.setCredentials(Arrays.asList(credential));
//
//
//        keycloak.realm(realm).users().create(newUser);
//
//
//        return toUser(newUser);
//    }

    @Override
    public UserDTO createUser(UserDTO user) {
        // Crear usuario
        UserRepresentation newUser = new UserRepresentation();

        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());

        // Añadir el nuevo campo personalizado "titulo"
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("titulo", Arrays.asList("Pintor"));
        newUser.setAttributes(attributes);

        newUser.setEnabled(true);
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue("password");
        credential.setTemporary(false);
        newUser.setCredentials(Arrays.asList(credential));

        Response response = keycloak.realm(realm).users().create(newUser);

        // Verificar si la creación fue exitosa
        if (response.getStatus() != 201) {
            throw new RuntimeException("Error al crear el usuario en Keycloak. Código de estado: " + response.getStatus());
        }

        // Obtener el ID del nuevo usuario
        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

        // Adicionar al usuario al grupo PROVIDERS
        GroupRepresentation group = keycloak.realm(realm).groups().groups().stream().filter(g -> g.getName().equals("PROVIDERS")).findFirst().orElse(null);

        if (group != null) {
            keycloak.realm(realm).users().get(userId).joinGroup(group.getId());
        } else {
            throw new RuntimeException("No se encontró el grupo PROVIDERS en Keycloak.");
        }

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