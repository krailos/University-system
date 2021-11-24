package ua.com.foxminded.krailo.university.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.FormatterRegistry;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
	PageableHandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver();
	pageableResolver.setOneIndexedParameters(true);
	pageableResolver.setFallbackPageable(PageRequest.of(0, pagableDefaultPageSize));
	resolvers.add(pageableResolver);
    }
    
    @Bean
    public MessageSource messageSource() {
	ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	messageSource.setBasename("classpath:messages");
	messageSource.setDefaultEncoding("UTF-8");
	return messageSource;
    }
    
    @Override
    public Validator getValidator() {
	LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	    bean.setValidationMessageSource(messageSource());
	    return bean;
    }
}