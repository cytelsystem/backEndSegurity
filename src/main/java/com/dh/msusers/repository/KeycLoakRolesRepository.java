package com.dh.msusers.repository;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RealmsResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class KeycLoakRolesRepository implements IRolesRepository {

    @Autowired
    private Keycloak keycloak;

    @Override
    public String createRoles(String roles) {
        RealmResource realmResource = keycloak.realm("cytelsystem");
        RolesResource rolesResource = realmResource.roles();
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(roles);
        rolesResource.create(roleRepresentation);
        System.out.println("Roll  creado exitosamente.");

        return roles;
    }
}
