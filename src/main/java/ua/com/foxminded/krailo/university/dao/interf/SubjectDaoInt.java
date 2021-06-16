package ua.com.foxminded.krailo.university.dao.interf;

import java.util.List;

import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Year;

public interface SubjectDaoInt extends GenericDao<Subject> {

    List<Subject> getAll();

    List<Subject> getByTeacher(Teacher teacher);

    List<Subject> getByYear(Year year);

    int count();

}
