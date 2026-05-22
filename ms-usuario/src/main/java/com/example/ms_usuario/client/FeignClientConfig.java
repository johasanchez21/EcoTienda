package com.example.ms_usuario.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.auth.BasicAuthRequestInterceptor;
@Configuration
public class FeignClientConfig {

    @Value("${ms.rol.user}")
    private String rolUser;

    @Value("${ms.rol.password}")
    private String rolPassword;

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(rolUser, rolPassword);
    }
}
