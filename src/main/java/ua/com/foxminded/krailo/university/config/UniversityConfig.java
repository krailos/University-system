package ua.com.foxminded.krailo.university.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@EnableConfigurationProperties(UniversityConfigProperties.class)
public class UniversityConfig {

}
