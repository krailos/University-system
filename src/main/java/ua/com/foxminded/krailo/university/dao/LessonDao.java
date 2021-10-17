package ua.com.foxminded.krailo.university.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.model.Teacher;

public interface LessonDao extends PagingAndSortingRepository<Lesson, Integer>, JpaRepository<Lesson, Integer> {

    List<Lesson> getByTeacherAndDateBetween(Teacher teacher, LocalDate start, LocalDate end);

    Optional<Lesson> getByDateAndTeacherAndLessonTime(LocalDate date, Teacher teacher, LessonTime lessonTime);

    List<Lesson> getByTeacherAndDate(Teacher teacher, LocalDate date);

    Optional<Lesson> getByDateAndAudienceAndLessonTime(LocalDate date, Audience audience, LessonTime lessonTimeId);

    List<Lesson> findByDate(LocalDate date);

    @Query("select l from Lesson l join l.groups as g  where g.id = ?1 and l.date = ?2")
    List<Lesson> findByGroupAndDate(int groupId, LocalDate date);

    @Query("select l from Lesson l join l.groups as g  where g.id = ?1 and l.date between ?2 and ?3")
    List<Lesson> findByGroupAndDateBetween(int groupId, LocalDate start, LocalDate end);

    @Query("select l from Lesson l inner join l.groups as g where l.date = ?1 and l.lessonTime.id = ?2 and g.id = ?3")
    Optional<Lesson> findByDateAndLessonTimeAndGroup(LocalDate date, int lessonTimeId, int groupId);

}
