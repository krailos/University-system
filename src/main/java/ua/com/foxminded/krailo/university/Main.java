package ua.com.foxminded.krailo.university;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ua.com.foxminded.krailo.university.config.UniversityConfigProperties;

@SpringBootApplication()
@EnableJpaRepositories(basePackages = "ua.com.foxminded.krailo.university")
public class Main {

    public static void main(String[] args) {
	SpringApplication.run(Main.class, args);
    }
}	
		