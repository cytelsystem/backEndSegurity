package com.dh.usersService.configuracion.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeyCloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
  private final JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

  // Extrae los roles de recursos del token JWT
  private static Collection<? extends GrantedAuthority> extractResourceRoles(final Jwt jwt) throws JsonProcessingException {
    Set<GrantedAuthority> resourcesRoles = new HashSet<>();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());

    // Extrae los roles de los recursos
    resourcesRoles.addAll(extractRoles("resource_access", objectMapper.readTree(objectMapper.writeValueAsString(jwt)).get("claims")));
    // Extrae los roles del acceso al reino (realm_access)
    resourcesRoles.addAll(extractRolesRealmAccess("realm_access", objectMapper.readTree(objectMapper.writeValueAsString(jwt)).get("claims")));
    // Extrae los valores de aud (audiencia)
    resourcesRoles.addAll(extractAud("aud", objectMapper.readTree(objectMapper.writeValueAsString(jwt)).get("claims")));
    // Extrae los grupos
    resourcesRoles.addAll(extractGroups("group", objectMapper.readTree(objectMapper.writeValueAsString(jwt)).get("claims")));


    System.out.println("------------------------------------------------------");
    resourcesRoles.forEach(grantedAuthority -> {
      System.out.println(grantedAuthority.getAuthority());
    });


    return resourcesRoles;
  }

  private static List<GrantedAuthority> extractRoles(String route, JsonNode jwt) {
    Set<String> rolesWithPrefix = new HashSet<>();

    jwt.path(route)
            .elements()
            .forEachRemaining(e -> e.path("roles")
                    .elements()
                    .forEachRemaining(r -> rolesWithPrefix.add("ROLE_" + r.asText())));

    return AuthorityUtils.createAuthorityList(rolesWithPrefix.toArray(new String[0]));
  }

  // Extrae los roles del acceso al reino (realm_access)
  private static List<GrantedAuthority> extractRolesRealmAccess(String route, JsonNode jwt) {
    Set<String> rolesWithPrefix = new HashSet<>();

    jwt.path(route)
            .path("roles")
            .elements()
            .forEachRemaining(r -> rolesWithPrefix.add("ROLE_" + r.asText()));

    return AuthorityUtils.createAuthorityList(rolesWithPrefix.toArray(new String[0]));
  }

  private static List<GrantedAuthority> extractGroups(String route, JsonNode jwt) {
    Set<String> groupsWithPrefix = new HashSet<>();

    jwt.path(route)
            .elements()
            .forEachRemaining(e -> groupsWithPrefix.add("GROUP_" + e.asText()));

    return AuthorityUtils.createAuthorityList(groupsWithPrefix.toArray(new String[0]));
  }

  private static List<GrantedAuthority> extractAud(String route, JsonNode jwt) {
    Set<String> audWithPrefix = new HashSet<>();

    jwt.path(route)
            .elements()
            .forEachRemaining(e -> audWithPrefix.add("AUD_" + e.asText()));

    return AuthorityUtils.createAuthorityList(audWithPrefix.toArray(new String[0]));
  }

  public KeyCloakJwtAuthenticationConverter() {
  }

  public AbstractAuthenticationToken convert(final Jwt source) {
    Collection<GrantedAuthority> authorities = null;
    try {
      authorities = this.getGrantedAuthorities(source);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return new JwtAuthenticationToken(source, authorities);
  }

  public Collection<GrantedAuthority> getGrantedAuthorities(Jwt source) throws JsonProcessingException {
    return Stream.concat(
            this.defaultGrantedAuthoritiesConverter.convert(source).stream(),
            extractResourceRoles(source).stream()
    ).collect(Collectors.toSet());
  }
}
