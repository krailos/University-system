package ua.com.foxminded.krailo.university.dao.interf;

import java.util.List;

import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Year;

public interface SubjectDao extends GenericDao<Subject> {

    List<Subject> getByTeacher(Teacher teacher);

    List<Subject> getByYear(Year year);

}
