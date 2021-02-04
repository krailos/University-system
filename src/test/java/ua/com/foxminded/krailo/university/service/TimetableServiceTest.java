package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class TimetableServiceTest {

    @Autowired
    private TimetableService timetableService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Test
    void givenDateTimetableIdSubjectIdAudienceIdLessonTimeIdTeachher_whenCreateLesson_thenCreated() {
	
	timetableService.addLesson(LocalDate.of(2021, 01, 01), 1, 1, 3, 2, 1);
	
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lessons");
	assertEquals(3, actual);
    }

}
