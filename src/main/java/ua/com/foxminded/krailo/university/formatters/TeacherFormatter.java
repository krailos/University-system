package ua.com.foxminded.krailo.university.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import ua.com.foxminded.krailo.university.model.Teacher;

public class TeacherFormatter implements Formatter<Teacher> {

    @Override
    public String print(Teacher teacher, Locale locale) {
	return String.valueOf(teacher.getId());
    }

    @Override
    public Teacher parse(String teachertId, Locale locale) throws ParseException {
	return Teacher.builder().id(Integer.valueOf(teachertId)).build();
    }

}
