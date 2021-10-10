package ua.com.foxminded.krailo.university.dao.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.foxminded.krailo.university.model.Teacher;

public interface TeacherDaoJpa extends PagingAndSortingRepository<Teacher, Integer> {

    Optional<Teacher> findById(int id);

    List<Teacher> findAll();

    Teacher save(Teacher teacher);

    void delete(Teacher teacher);

    @Query("select t from Teacher t inner join t.subjects as s where s.id = ?1 order by t.lastName")
    List<Teacher> getBySubject(int subjectId);

}
