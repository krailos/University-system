package ua.com.foxminded.krailo.university.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class UniversityDispatcherInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
	return new Class[] { UniversityConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
	return new Class[] { WebConfig.class };
    }
 
    @Override
    protected String[] getServletMappings() {
	return new String[] { "/" };
    }

    
}
