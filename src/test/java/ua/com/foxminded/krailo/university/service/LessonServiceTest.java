package ua.com.foxminded.krailo.university.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.model.Lesson;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class LessonServiceTest {

    @Autowired
    @InjectMocks
    private LessonService lessonService;
    @Mock
    private LessonDao lessonDao;

    @Test
    void givenLessonId_whenGetById_thenGot() {
	when(lessonDao.findById(any(Integer.class))).thenReturn(new Lesson());

	lessonService.getById(1);

	verify(lessonDao).findById(any(Integer.class));
    }

}
