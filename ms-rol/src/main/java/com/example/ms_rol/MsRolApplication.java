package com.example.ms_rol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsRolApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsRolApplication.class, args);
	}

}
