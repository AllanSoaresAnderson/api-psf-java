package br.com.psf.personalsystemfinance;

import br.com.psf.personalsystemfinance.rest.FixedTransactionsController;
import br.com.psf.personalsystemfinance.service.EventualTransactionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication(scanBasePackages = {"rest","service","entity", "repository"})
@ComponentScan(basePackageClasses = {FixedTransactionsController.class, EventualTransactionService.class})
@EnableJpaRepositories
public class PersonalsystemfinanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalsystemfinanceApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer(){
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry corsRegistry){
				corsRegistry.addMapping("*").allowedOrigins("http://localhost:4200");
			}
		};
	}

}
