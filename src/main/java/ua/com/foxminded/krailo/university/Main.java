package ua.com.foxminded.krailo.university;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
@EnableJpaRepositories(basePackages = "ua.com.foxminded.krailo.university")
public class Main {

    public static void main(String[] args) {
	SpringApplication.run(Main.class, args);
    }
}	
	