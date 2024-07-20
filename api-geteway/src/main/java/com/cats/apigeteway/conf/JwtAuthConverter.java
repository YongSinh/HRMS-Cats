package com.cats.apigeteway.conf;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class JwtAuthConverter implements Converter<Jwt, Mono<? extends AbstractAuthenticationToken>> {

    private static final Logger LOG = LoggerFactory.getLogger(JwtAuthConverter.class);
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    private final JwtAuthConverterProperties properties;

    @Override
    public Mono<? extends AbstractAuthenticationToken> convert(@NotNull Jwt jwt) {
        LOG.debug("Converting JWT to AuthenticationToken: {}", jwt);
        return Mono.justOrEmpty(buildAuthenticationToken(jwt));
    }

    private AbstractAuthenticationToken buildAuthenticationToken(Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()).collect(Collectors.toSet());
        return new JwtAuthenticationToken(jwt, authorities, getPrincipalClaimName(jwt));
    }

    private String getPrincipalClaimName(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        if (properties.getPrincipalAttribute() != null) {
            claimName = properties.getPrincipalAttribute();
        }
        String principal = jwt.getClaim(claimName);
        LOG.debug("Extracted principal claim: {}", principal);
        return principal;
    }

    @SuppressWarnings("unchecked")
    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        final Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess == null) {
            LOG.warn("No realm_access claim in JWT");
            return Collections.emptySet();
        }
        final List<String> realmRoles = (List<String>) realmAccess.get("roles");
        if (realmRoles == null || realmRoles.isEmpty()) {
            LOG.warn("No roles found in realm_access claim");
            return Collections.emptySet();
        }
        LOG.debug("Extracted roles from JWT: {}", realmRoles);
        return realmRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toSet());
    }
}
