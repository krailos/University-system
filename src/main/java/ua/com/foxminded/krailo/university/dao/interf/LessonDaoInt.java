package ua.com.foxminded.krailo.university.dao.interf;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.model.Teacher;

public interface LessonDaoInt extends GenericDao<Lesson> {

    List<Lesson> getAll();

    List<Lesson> getByPage(Pageable pageable);

    List<Lesson> getByDate(LocalDate date);

    List<Lesson> getByTeacherBetweenDates(Teacher teacher, LocalDate startDate, LocalDate finishDate);

    List<Lesson> getByTeacherAndDate(Teacher teacher, LocalDate date);

    List<Lesson> getByStudentBetweenDates(Student student, LocalDate startDate, LocalDate finishDate);

    List<Lesson> getByStudentAndDate(Student student, LocalDate date);

    Optional<Lesson> getByDateAndTeacherAndLessonTime(LocalDate date, Teacher teacher, LessonTime lessonTime);

    Optional<Lesson> getByDateAndAudienceAndLessonTime(LocalDate date, Audience audience, LessonTime lessonTime);

    Optional<Lesson> getByDateAndLessonTimeAndGroup(LocalDate date, LessonTime lessonTime, Group group);

    int count();

}
