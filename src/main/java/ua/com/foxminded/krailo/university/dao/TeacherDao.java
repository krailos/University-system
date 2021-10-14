package ua.com.foxminded.krailo.university.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.foxminded.krailo.university.model.Teacher;

public interface TeacherDao extends PagingAndSortingRepository<Teacher, Integer> {

    @Query("select t from Teacher t join fetch t.subjects as s where s.id = ?1 order by t.lastName")
    List<Teacher> getBySubject(int subjectId);

}
