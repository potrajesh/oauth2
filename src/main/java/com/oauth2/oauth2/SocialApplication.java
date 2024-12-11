package com.oauth2.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
/*References
* https://spring.io/guides/tutorials/spring-boot-oauth2
*https://www.udemy.com/course/spring-security-zero-to-master/learn/lecture/23708972#overview
* */
@Configuration
public class SocialApplication {


    @Bean
    public SecurityFilterChain defaultSecurityChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/secure")
                        .authenticated()
                        .anyRequest().permitAll()
                ).oauth2Login(org.springframework.security.config.Customizer.withDefaults())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(401, "Unauthorized: Access Denied"); // Custom error for unauthorized access
                        })
                );
        return httpSecurity.build();
    }


    @Bean
    ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration github = gitHubClientRegistration();
      //  ClientRegistration facebook = faceBookClientRegistration();
        return new InMemoryClientRegistrationRepository(github);
    }

    private ClientRegistration gitHubClientRegistration(){
      return  CommonOAuth2Provider.GITHUB.getBuilder("github").clientId("Ov23liRJkr98au2SdblD")
                .clientSecret("0242e5f71dd20ba02f3f9dbea378ecf930db8976").build();
    }
    /*private ClientRegistration faceBookClientRegistration(){
        return  CommonOAuth2Provider.GITHUB.getBuilder("facebook").clientId("")
                .clientSecret("").build();
    }*/
}