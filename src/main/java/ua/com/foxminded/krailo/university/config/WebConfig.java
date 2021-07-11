package ua.com.foxminded.krailo.university.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
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

@EnableSpringDataWebSupport
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Value("${spring.data.pageable.defaultPageSize}")
    private int pagableDefaultPageSize;

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

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
	PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
	resolver.setOneIndexedParameters(true);
	resolver.setFallbackPageable(PageRequest.of(0, pagableDefaultPageSize));
	resolvers.add(resolver);
    }

}