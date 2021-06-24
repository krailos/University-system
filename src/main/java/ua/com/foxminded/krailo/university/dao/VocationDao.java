package ua.com.foxminded.krailo.university.dao;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Vocation;

public interface VocationDao extends GenericDao<Vocation> {

    List<Vocation> getByTeacher(Teacher teacher);

    List<Vocation> getByTeacherAndYear(Teacher teacher, Year year);

    Optional<Vocation> getByTeacherAndDate(Teacher teacher, LocalDate date);

}
