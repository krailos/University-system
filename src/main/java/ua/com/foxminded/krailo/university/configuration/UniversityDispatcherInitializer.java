package ua.com.foxminded.krailo.university.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class UniversityDispatcherInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
	return new Class[] { UniversityConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
	return new Class[] { WebUniversityConfig.class };
    }
 
    @Override
    protected String[] getServletMappings() {
	return new String[] { "/" };
    }

    
}
