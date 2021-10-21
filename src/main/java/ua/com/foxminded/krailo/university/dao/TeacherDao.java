package ua.com.foxminded.krailo.university.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;

public interface TeacherDao extends JpaRepository<Teacher, Integer> {

    List<Teacher> getBySubjects(Subject subject);

}
