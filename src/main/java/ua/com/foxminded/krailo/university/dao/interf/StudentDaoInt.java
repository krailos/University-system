package ua.com.foxminded.krailo.university.dao.interf;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Student;

public interface StudentDaoInt extends GenericDao<Student> {

    List<Student> getByPage(Pageable pageable);

    List<Student> getAll();

    List<Student> getByGroup(Group group);

    int count();

}
