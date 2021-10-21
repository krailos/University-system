package ua.com.foxminded.krailo.university.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Year;

public interface SubjectDao extends JpaRepository<Subject, Integer> {

    List<Subject> findByTeachers(Teacher teacher);

    List<Subject> findByYears(Year year);

}
