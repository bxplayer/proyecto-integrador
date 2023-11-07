package com.integrador.evently.users.keycloakClient;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.*;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class KeycloakClient {

    private final Keycloak keycloak;

    public KeycloakClient(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public boolean isNecessary(String realmName) {
        return keycloak.realms().findAll().stream().noneMatch(realm -> realm.getRealm().equals(realmName));
    }

    public void createRealm(String realmName){
        RealmsResource realmsResource = keycloak.realms();
        RealmRepresentation realm = new RealmRepresentation();
        realm.setRealm(realmName);
        realm.setEnabled(true);
        keycloak.realms().create(realm);

        RolesResource rolesResource = realmsResource.realm(realmName).roles();
        RoleRepresentation roleAdmin = new RoleRepresentation();
        roleAdmin.setName("ADMIN");
        RoleRepresentation roleUser = new RoleRepresentation();
        roleUser.setName("USER");
        RoleRepresentation roleProvider = new RoleRepresentation();
        roleProvider.setName("PROVIDER");
        Arrays.asList(roleAdmin, roleUser, roleProvider).forEach(role -> {
            RoleRepresentation roleRepresentation = new RoleRepresentation();
            roleRepresentation.setName(role.getName());
            rolesResource.create(roleRepresentation);
        });
    }

    public void createClient(String realm, String clientId, String clientSecret, List<String> roles) {
        RealmsResource realmsResource =  keycloak.realms();
        RealmRepresentation realmRepresentation = realmsResource.realm(realm).toRepresentation();
        String realmName = realmRepresentation.getRealm();
        if(Objects.equals(realmName, realmName)) {
            RealmResource realmResource = keycloak.realm(realmName);
            ClientsResource clientsResource = realmResource.clients();

            ClientRepresentation client = new ClientRepresentation();
            client.setClientId(clientId);
            client.setSecret(clientSecret);
            client.setServiceAccountsEnabled(true);
            client.setDirectAccessGrantsEnabled(true);
            client.setEnabled(true);

            Response response = clientsResource.create(client);
            if (response.getStatus() == 201) {
                String createdClientId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

                ClientResource clientResource = clientsResource.get(createdClientId);

                roles.forEach(role -> {
                    RoleRepresentation roleRepresentation = new RoleRepresentation();
                    roleRepresentation.setName(role);
                    roleRepresentation.setClientRole(true);
                    roleRepresentation.setContainerId(createdClientId);

                    clientResource.roles().create(roleRepresentation);
                });
            }
        }
    }

//    public void createGatewayClient(String realm, String clientId, String clientSecret, List<String> roles) {
//        RealmsResource realmsResource =  keycloak.realms();
//        RealmRepresentation realmRepresentation = realmsResource.realm(realm).toRepresentation();
//        String realmName = realmRepresentation.getRealm();
//        if(Objects.equals(realmName, realm)) {
//            RealmResource realmResource = keycloak.realm(realm);
//            ClientsResource gwResource = realmResource.clients();
//
//            ClientRepresentation gateway = new ClientRepresentation();
//            String url = "http://localhost:9090";
//            gateway.setClientId(clientId);
//            gateway.setSecret(clientSecret);
//            gateway.setRootUrl(url);
//            gateway.setWebOrigins(List.of("/*"));
//            gateway.setRedirectUris(List.of(url+"/*"));
//            gateway.setAdminUrl(url);
//            gateway.setEnabled(true);
//            gateway.setServiceAccountsEnabled(true);
//            gateway.setDirectAccessGrantsEnabled(true);
//
//            Response response = gwResource.create(gateway);
//            if (response.getStatus() == 201) {
//                String createdClientIdGW = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
//
//                ClientResource clientResourceGW = gwResource.get(createdClientIdGW);
//
//                roles.forEach(role -> {
//                    RoleRepresentation roleRepresentation = new RoleRepresentation();
//                    roleRepresentation.setName(role);
//                    roleRepresentation.setClientRole(true);
//                    roleRepresentation.setContainerId(createdClientIdGW);
//
//                    clientResourceGW.roles().create(roleRepresentation);
//                });
//            }
//        }
//    }

    public void createGroup(String realmName, String group) {
        RealmsResource realmsResource = keycloak.realms();
        RealmResource realm = realmsResource.realm(realmName);

        GroupRepresentation groupRepresentation = new GroupRepresentation();
        groupRepresentation.setName(group);
        groupRepresentation.setRealmRoles(List.of(group));

        realm.groups().add(groupRepresentation);
    }

    public void addGroupsToToken(String realm, String scope) {
        RealmResource realmResource = keycloak.realm(realm);
        List<ClientScopeRepresentation> scopes = realmResource.clientScopes().findAll();
        ClientScopeRepresentation clientScope = scopes.stream()
                .filter(cs -> cs.getName().equals(scope))
                .findFirst()
                .orElse(null);

        String id = clientScope.getId();

        ProtocolMapperRepresentation groupMembership = new ProtocolMapperRepresentation();
        groupMembership.setName("group");
        groupMembership.setProtocol("openid-connect");
        groupMembership.setProtocolMapper("oidc-group-membership-mapper");
        groupMembership.getConfig().put("full.path", "false");
        groupMembership.getConfig().put("access.token.claim", "true");
        groupMembership.getConfig().put("id.token.claim", "true");
        groupMembership.getConfig().put("userinfo.token.claim", "true");
        groupMembership.getConfig().put("claim.name", "groups");

        ClientScopeResource clientScopeResource = realmResource.clientScopes().get(id);
        clientScopeResource.getProtocolMappers().createMapper(groupMembership);

        ClientScopeRepresentation updatedClientScope = clientScopeResource.toRepresentation();
        clientScopeResource.update(updatedClientScope);
    }

    public void addAdminUser(String realm){
        RealmResource realmResource = keycloak.realm(realm);
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();

        UserRepresentation user = new UserRepresentation();
        user.setUsername("admin");
        user.setFirstName("admin");
        user.setEmail("admin@admin.com");
//        user.setGroups(List.of(group));
        user.setRealmRoles(List.of("ADMIN"));
        user.setEnabled(true);

        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue("root");
        credentialRepresentation.setTemporary(false);

        user.setCredentials(List.of(credentialRepresentation));

        Response response = realmResource.users().create(user);
        String createdUserId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");


        System.out.println("USER ID: " + createdUserId);
    }
}
