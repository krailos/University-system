package ua.com.foxminded.krailo.university.dao.interf;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.model.Teacher;

public interface TeacherDaoInt extends GenericDao<Teacher> {

    List<Teacher> getAllByPage(Pageable pageable);

    List<Teacher> getAll();
    
    int count();

}
