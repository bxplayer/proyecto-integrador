package com.integrador.evently.users.service;

import com.integrador.evently.users.keycloakClient.KeycloakClient;
import com.integrador.evently.users.model.UserType;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

//@Service
public class PostConstructService {
    private final KeycloakClient keycloakClient;

    public PostConstructService(KeycloakClient keycloakClient) {
        this.keycloakClient = keycloakClient;
    }

    //@PostConstruct
    public void initialize() {
        String realmName = "eventos";

        if (keycloakClient.isNecessary(realmName)) {
            String usersClientName = "users-client";
            String usersClientSecret = "users-secret";
            List<String> roles = Arrays.stream(UserType.values()).map(UserType::toString).toList();
            String scope = "roles";

            keycloakClient.createRealm(realmName);
            keycloakClient.createClient(realmName, usersClientName, usersClientSecret, roles);
            keycloakClient.createGroup(realmName, "PROVIDERS");
            keycloakClient.createGroup(realmName, "USERS");
            keycloakClient.addGroupsToToken(realmName, scope);
            keycloakClient.addAdminUser(realmName);
        }
    }
}
