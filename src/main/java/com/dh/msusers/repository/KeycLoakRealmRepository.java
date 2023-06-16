package com.dh.msusers.repository;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmsResource;
import org.keycloak.representations.idm.RealmRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;


@Repository
public class KeycLoakRealmRepository implements IRealmRepository {

    @Autowired
    private Keycloak keycloak;

    @Override
    public String createRealm(String realm) {
        RealmsResource realmsResource = keycloak.realms();
        RealmRepresentation realmRepresentation = new RealmRepresentation();
        realmRepresentation.setRealm(realm);
        realmRepresentation.setEnabled(true);
        realmRepresentation.setRegistrationAllowed(false);
        realmsResource.create(realmRepresentation);
        System.out.println("Reino 'cytelsystem' creado exitosamente.");


        return realm;
    }
}
