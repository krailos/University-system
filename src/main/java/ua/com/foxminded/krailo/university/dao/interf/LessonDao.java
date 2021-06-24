package ua.com.foxminded.krailo.university.dao.interf;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.model.Teacher;

public interface LessonDao extends GenericDao<Lesson> {

    List<Lesson> getByDate(LocalDate date);

    List<Lesson> getByTeacherBetweenDates(Teacher teacher, LocalDate startDate, LocalDate finishDate);

    List<Lesson> getByTeacherAndDate(Teacher teacher, LocalDate date);

    List<Lesson> getByGroupBetweenDates(Group group, LocalDate startDate, LocalDate finishDate);

    List<Lesson> getByGroupAndDate(Group group, LocalDate date);

    Optional<Lesson> getByDateAndTeacherAndLessonTime(LocalDate date, Teacher teacher, LessonTime lessonTime);

    Optional<Lesson> getByDateAndAudienceAndLessonTime(LocalDate date, Audience audience, LessonTime lessonTime);

    Optional<Lesson> getByDateAndLessonTimeAndGroup(LocalDate date, LessonTime lessonTime, Group group);

}
