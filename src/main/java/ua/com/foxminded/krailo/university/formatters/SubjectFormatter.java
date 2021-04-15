package ua.com.foxminded.krailo.university.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import ua.com.foxminded.krailo.university.model.Subject;

public class SubjectFormatter implements Formatter<Subject> {

    @Override
    public String print(Subject subject, Locale locale) {
	return String.valueOf(subject.getId());
    }

    @Override
    public Subject parse(String subjectId, Locale locale) throws ParseException {
	return Subject.builder().id(Integer.valueOf(subjectId)).build();
    }

}
