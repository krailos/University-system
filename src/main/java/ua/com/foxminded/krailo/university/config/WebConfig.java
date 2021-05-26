package ua.com.foxminded.krailo.university.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import ua.com.foxminded.krailo.university.formatters.GroupFormatter;
import ua.com.foxminded.krailo.university.formatters.SubjectFormatter;
import ua.com.foxminded.krailo.university.formatters.TeacherFormatter;

@Configuration
@ComponentScan("ua.com.foxminded.krailo.university")
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public SpringResourceTemplateResolver templateResolver(ApplicationContext applicationContext) {
	SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
	templateResolver.setApplicationContext(applicationContext);
	templateResolver.setPrefix("classpath:/templates/");
	templateResolver.setSuffix(".html");
	templateResolver.setCacheable(false);
	templateResolver.setCharacterEncoding("UTF-8");
	return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(SpringResourceTemplateResolver templateResolver) {
	SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	templateEngine.setTemplateResolver(templateResolver);
	templateEngine.setEnableSpringELCompiler(true);
	return templateEngine;
    }

    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
	ThymeleafViewResolver resolver = new ThymeleafViewResolver();
	resolver.setTemplateEngine(templateEngine);
	templateEngine.addDialect(new Java8TimeDialect());
	resolver.setCharacterEncoding("UTF-8");
	resolver.setContentType("text/html; charset=UTF-8");
	return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
	registry.addFormatter(new GroupFormatter());
	registry.addFormatter(new SubjectFormatter());
	registry.addFormatter(new TeacherFormatter());
    }
    
  

}