package io.javabrains.moviecatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MovieCatalogServiceApplication {
	
	@Bean
	public RestTemplate getRestTemplate() {
		System.out.println("I am GETTING CREATED HERE! WOHOOO!");
		return new RestTemplate();
	}

	public static void main(String[] args) {
		System.out.println("OK, I am getting called."+MovieCatalogServiceApplication.class.getName());
		
		SpringApplication.run(MovieCatalogServiceApplication.class, args);
		
		System.out.println("OK, I am getting called- END."+MovieCatalogServiceApplication.class.getName());

	}

}

