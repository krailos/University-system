package ua.com.foxminded.krailo.university.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Year;

public interface SubjectDao extends PagingAndSortingRepository<Subject, Integer>, JpaRepository<Subject, Integer> {

    @Query("select s from Subject s inner join s.teachers as t where t.id = :teacherId order by s.name")
    List<Subject> findByTeacher(int teacherId);
    
    @Query("select s from Subject s inner join s.years as y where y.id = :yearId order by s.name")
    List<Subject> findByYear(int yearId);

}
