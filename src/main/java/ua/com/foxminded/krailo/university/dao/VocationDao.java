package ua.com.foxminded.krailo.university.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Vocation;

public interface VocationDao extends JpaRepository<Vocation, Integer> {

    List<Vocation> findByTeacher(Teacher teacher);

    List<Vocation> findByTeacherAndApplyingDateBetween(Teacher teacher, LocalDate firstDayOfYear,
	    LocalDate lsastDayOfYear);

    @Query("from Vocation v where v.teacher.id = :teacherId and :date between v.start and v.end")
    Optional<Vocation> findByTeacherAndDate(int teacherId, LocalDate date);

}
