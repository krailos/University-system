package ua.com.foxminded.krailo.university.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import ua.com.foxminded.krailo.university.configuration.UniversityConfig;
import ua.com.foxminded.krailo.university.dao.LessonDao;

@Configuration
@Import(UniversityConfig.class)
public class ConfigTestMock {

    @Bean()
    public LessonDao lessonDao() {
	return Mockito.mock(LessonDao.class);
    }

}
