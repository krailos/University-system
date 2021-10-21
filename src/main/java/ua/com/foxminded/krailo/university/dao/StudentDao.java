package ua.com.foxminded.krailo.university.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Student;

public interface StudentDao extends JpaRepository<Student, Integer> {

    List<Student> getByGroup(Group group);

}
