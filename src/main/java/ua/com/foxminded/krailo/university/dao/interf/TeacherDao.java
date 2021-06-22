package ua.com.foxminded.krailo.university.dao.interf;

import java.util.List;

import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;

public interface TeacherDao extends GenericDao<Teacher> {

    List<Teacher> getAll();

    List<Teacher> getBySubject(Subject subject);

    int count();

}
