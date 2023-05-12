package com.example.microserviceGestionCedit;

import java.math.BigDecimal;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.microserviceGestionCedit.entity.Credits;
import com.example.microserviceGestionCedit.entity.Credits.TypeCredit;
import com.example.microserviceGestionCedit.repository.CreditRepository;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.microserviceGestionCedit.Repository.*")
@EntityScan("com.example.microserviceGestionCedit.entity.*")
@SpringBootApplication
@ComponentScan(basePackages = "com.example.microserviceGestionCedit.Repository.*")
public class MicroserviceGestionCeditApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceGestionCeditApplication.class, args);
	}
//	@Bean
//	ApplicationRunner start(CreditRepository repo) {
//	    return args -> {
	 //       Stream.of(
	//            new Credits(10000.0, new Date(), 500.0, TypeCredit.IMMOBILIER, 12L, 10000L, 0L, new BigDecimal("0.05")),
	//            new Credits(5000.0, new Date(), 250.0, TypeCredit.CONSOMMATION, 6L, 5000L, 0L, new BigDecimal("0.03"))
	 //       ).forEach(credit -> repo.save(credit));
	  //      repo.findAll().forEach(System.out::println);
	   // };
	//}

}
