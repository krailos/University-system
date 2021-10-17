package ua.com.foxminded.krailo.university.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.model.Teacher;

public interface LessonDao extends PagingAndSortingRepository<Lesson, Integer>, JpaRepository<Lesson, Integer> {

    List<Lesson> getByTeacherAndDateBetween(Teacher teacher, LocalDate start, LocalDate end);

    Optional<Lesson> getByDateAndTeacherAndLessonTime(LocalDate date, Teacher teacher, LessonTime lessonTime);

    List<Lesson> getByTeacherAndDate(Teacher teacher, LocalDate date);

    Optional<Lesson> getByDateAndAudienceAndLessonTime(LocalDate date, Audience audience, LessonTime lessonTimeId);

    List<Lesson> findByDate(LocalDate date);

    @Query("select l from Lesson l join l.groups as g  where g.id = :groupId and l.date = :date")
    List<Lesson> findByGroupAndDate(int groupId, LocalDate date);

    @Query("select l from Lesson l join l.groups as g  where g.id = :groupId and l.date between :start and :end")
    List<Lesson> findByGroupAndDateBetween(int groupId, LocalDate start, LocalDate end);

    @Query("select l from Lesson l inner join l.groups as g where l.date = :date and l.lessonTime.id = :lessonTimeId and g.id = :groupId")
    Optional<Lesson> findByDateAndLessonTimeAndGroup(LocalDate date, int lessonTimeId, int groupId);

}
