package com.dh.msusers.repository;

import com.dh.msusers.modelDTO.UserDTO;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class KeyCloakUserRepository implements IUserRepository{

    @Autowired
    private Keycloak keycloakClient;

    @Value("${dh.keycloak.realm}")
    private String realm;

    //******************Metodos Implementados de la interface***********************//

    @Override
    public UserDTO createUser(UserDTO user) {
        UserRepresentation newUser = new UserRepresentation();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());


        // Establecer otros atributos necesarios para el usuario

        keycloakClient.realm(realm).users().create(newUser);


        return toUser(newUser);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<UserRepresentation> users = keycloakClient.realm(realm).users().list();
        return users.stream().map(this::toUser).collect(Collectors.toList());


    }


    @Override
    public List<UserDTO> findByFirName(String name) {
        List<UserRepresentation> users = keycloakClient.realm(realm).users().search(name);
        return users.stream().map(UserRepresentation -> toUser(UserRepresentation)).collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(String id) {

        UserRepresentation user = keycloakClient.realm(realm).users().get(id).toRepresentation();

        return toUser(user);
    }

    @Override
    public UserDTO updateNationality(String id, String nationality) {
        UserResource userResource = keycloakClient.realm(realm).users().get(id);
        UserRepresentation user = userResource.toRepresentation();
        Map<String,List<String>> attributes = new HashMap<>();
        attributes.put("nacionalidad", List.of(nationality));
        user.setAttributes(attributes);
        userResource.update(user);

        return toUser(user);
    }

    //**********************************************************************************

    private UserDTO toUser(UserRepresentation UserRepresentation){
        String nationality = null;
        try {
            nationality = UserRepresentation.getAttributes().get("nacionalidad").get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new UserDTO(
                UserRepresentation.getId(),
                UserRepresentation.getUsername(),
                UserRepresentation.getEmail(),
                UserRepresentation.getFirstName(),
                nationality);
    }


}
