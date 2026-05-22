package com.example.ms_producto.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.auth.BasicAuthRequestInterceptor;

@Configuration
public class FeignClientConfig {

    @Value("${ms.categoria.user}")
    private String categoriaUser;

    @Value("${ms.categoria.password}")
    private String categoriaPassword;

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(categoriaUser, categoriaPassword);
    }
}
