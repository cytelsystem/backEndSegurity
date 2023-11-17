package com.dh.msgateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.session.WebSessionManager;
import reactor.core.publisher.Mono;

import static org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder.withJwkSetUri;


//@Configuration
//public class SecurityConfig {
//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        http
//                .csrf().disable()
//                .authorizeExchange()
//                .anyExchange()
//                .authenticated()
//                .and()
//                .oauth2Login(); // to redirect to oauth2 login page.
//        return http.build();
//    }
//}


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec.anyExchange().authenticated())
                .cors(ServerHttpSecurity.CorsSpec::disable).csrf(ServerHttpSecurity.CsrfSpec::disable)
                .oauth2ResourceServer(OAuth2ResourceServerSpec -> OAuth2ResourceServerSpec.jwt(jwtSpec -> jwtSpec.jwtDecoder(jwtDecoder())
                ))
                .build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return withJwkSetUri("http://localhost:8082/realms/dh/protocol/openid-connect/certs").build();
    }

    @Bean
    public WebSessionManager webSessionManager() {
// Emulate SessionCreationPolicy. STATELESS
        return exchange -> Mono.empty();
    }
}

