package com.oauth2.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SocialApplication {

    @Bean
    public SecurityFilterChain defaultSecurityChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/secure").authenticated() // Secured endpoint
                        .requestMatchers("/unsecure").access((authentication, context) -> {
                            String userAgent = context.getRequest().getHeader("User-Agent");
                            if (userAgent != null && isBrowser(userAgent)) {
                                throw new AccessDeniedException("Browser access is not allowed");
                            }
                            return null; // Grant access for non-browser clients
                        })
                        .anyRequest().permitAll() // Permit all other requests
                )
                .oauth2Login(oauth -> oauth
                        .defaultSuccessUrl("/", true) // Redirect to /secure after successful login
                )                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Access Denied");
                        })
                        .accessDeniedHandler(customAccessDeniedHandler()) // Custom AccessDeniedHandler
                );
        return httpSecurity.build();
    }

    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return (HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Access Denied: This endpoint cannot be accessed from a browser.");
        };
    }

    @Bean
    ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration github = gitHubClientRegistration();
        return new InMemoryClientRegistrationRepository(github);
    }

    private ClientRegistration gitHubClientRegistration() {
        return CommonOAuth2Provider.GITHUB.getBuilder("github")
                .clientId("Ov23liRJkr98au2SdblD")
                .clientSecret("0242e5f71dd20ba02f3f9dbea378ecf930db8976")
                .build();
    }

    // Helper method to detect browsers based on User-Agent
    private boolean isBrowser(String userAgent) {
        return userAgent.contains("Mozilla") || userAgent.contains("Chrome")
                || userAgent.contains("Safari") || userAgent.contains("Edge")
                || userAgent.contains("Firefox");
    }
}
