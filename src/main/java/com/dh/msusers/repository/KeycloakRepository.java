package com.dh.msusers.repository;

import com.dh.msusers.modelDTO.User;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class KeycloakRepository {
    @Autowired
    private Keycloak keycloak;
    @Value("\${keycloak.realm}")
    private String realm;
    public List<User> findByUsername(String username) {
        List<UserRepresentation> userRepresentation = keycloak
                .realm(realm)
                .users()
                .search(username);
        return userRepresentation.stream().map(user->
                fromRepresentation(user)).collect(Collectors.toList());
    }
    public Optional<User> findById(String id) {
        UserRepresentation userRepresentation = keycloak
                .realm(realm)
                .users()
                .get(id)
                .toRepresentation()
        return Optional.of(fromRepresentation(userRepresentation));
    }
    private User fromRepresentation(UserRepresentation userRepresentation) {
        return
                User(userRepresentation.getId(),userRepresentation.getFirstName(),userRepresentation.getLast
                        Name(),userRepresentation.getEmail())
    }

}