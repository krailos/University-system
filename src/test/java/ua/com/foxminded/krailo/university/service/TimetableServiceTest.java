package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Timetable;
import ua.com.foxminded.krailo.university.model.Year;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class TimetableServiceTest {

    @Autowired
    private TimetableService timetableService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenTimetableWithYearWithCorrectSubjects_whenUpdate_thenUpdated() {
	List<Subject> subjects = new ArrayList<>(
		Arrays.asList(new Subject(1, "subject 1"), new Subject(2, "subject 2")));
	Year year = new Year(1, "new", null);
	year.setSubjects(subjects);
	Timetable timetable = new Timetable(1, "new", year);

	timetableService.update(timetable);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "timetables");
	assertEquals(2, actual);
    }

    @Test
    void givenTimetableWithYearWithWrongSubjects_whenUpdate_thenThrowException() {
	List<Subject> subjects = new ArrayList<>(Arrays.asList(new Subject(1, "subject 1")));
	Year year = new Year(1, "new", null);
	year.setSubjects(subjects);
	Timetable timetable = new Timetable(1, "new", year);
	assertThrows(RuntimeException.class, () -> {

	    timetableService.update(timetable);

	});
    }

}
