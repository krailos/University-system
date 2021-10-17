package ua.com.foxminded.krailo.university.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Vocation;

public interface VocationDao extends PagingAndSortingRepository<Vocation, Integer>, JpaRepository<Vocation, Integer> {

    List<Vocation> findByTeacher(Teacher teacher);

    @Query("from Vocation v where v.teacher.id = :teacherId and :date between v.start and v.end")
    Optional<Vocation> findByTeacherAndApplyingDateBetween(int teacherId, LocalDate date);

    @Query("from Vocation v where v.teacher.id = ?1 and" + " extract (year from v.start) = ?2 order by v.start ")
    List<Vocation> getByTeacherAndYear(int teacherId, int year);

    @Query("from Vocation v where v.teacher.id = ?1 and ?2 between v.start and v.end")
    Optional<Vocation> getByTeacherAndDate(int teacherId, LocalDate date);

    @Query("from Vocation v where v.teacher.id = :teacherId and extract (year from v.start) = :year order by v.start")
    List<Vocation> findByTeacherAndYear(int teacherId, int year);

}
