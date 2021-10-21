package ua.com.foxminded.krailo.university.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.model.Teacher;

public interface LessonDao extends JpaRepository<Lesson, Integer> {

    List<Lesson> getByTeacherAndDateBetween(Teacher teacher, LocalDate start, LocalDate end);

    Optional<Lesson> getByDateAndTeacherAndLessonTime(LocalDate date, Teacher teacher, LessonTime lessonTime);

    List<Lesson> getByTeacherAndDate(Teacher teacher, LocalDate date);

    Optional<Lesson> getByDateAndAudienceAndLessonTime(LocalDate date, Audience audience, LessonTime lessonTimeId);

    List<Lesson> findByDate(LocalDate date);

    List<Lesson> findByGroupsAndDate(Group group, LocalDate date);

    List<Lesson> findByGroupsAndDateBetween(Group group, LocalDate start, LocalDate end);

    Optional<Lesson> findByDateAndLessonTimeAndGroups(LocalDate date, LessonTime lessonTime, Group group);

}
