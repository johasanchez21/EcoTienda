package com.example.ms_rol.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.auth.BasicAuthRequestInterceptor;

@Configuration
public class FeignClientConfig {
    
    @Value("${ms.permiso.user}")
    private String permisoUser;

    @Value("${ms.permiso.password}")
    private String permisoPassword;

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {

        return new BasicAuthRequestInterceptor(
                permisoUser,
                permisoPassword
        );
    }
}
