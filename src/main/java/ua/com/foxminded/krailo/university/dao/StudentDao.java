package ua.com.foxminded.krailo.university.dao;

import java.util.List;

import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Student;

public interface StudentDao extends GenericDao<Student> {

    List<Student> getByGroup(Group group);

}
