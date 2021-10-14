package ua.com.foxminded.krailo.university.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Student;

public interface StudentDao extends PagingAndSortingRepository<Student, Integer> {

    List<Student> getByGroup(Group group);

}
