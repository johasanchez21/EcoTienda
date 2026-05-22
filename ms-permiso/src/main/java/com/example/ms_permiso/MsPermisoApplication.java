package com.example.ms_permiso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsPermisoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPermisoApplication.class, args);
	}

}
