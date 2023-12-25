package br.com.psf.personalsystemfinance;

import br.com.psf.personalsystemfinance.rest.EarningController;
import br.com.psf.personalsystemfinance.service.EntityService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {"rest","service","entity", "repository"})
@ComponentScan(basePackageClasses = {EarningController.class, EntityService.class})
@EnableJpaRepositories
public class PersonalsystemfinanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalsystemfinanceApplication.class, args);
	}

}
