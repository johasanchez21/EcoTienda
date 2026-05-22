package com.example.ms_envio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsEnvioApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsEnvioApplication.class, args);
	}

}
