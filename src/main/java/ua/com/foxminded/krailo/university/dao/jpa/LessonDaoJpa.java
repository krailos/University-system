package ua.com.foxminded.krailo.university.dao.jpa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.foxminded.krailo.university.model.Lesson;

public interface LessonDaoJpa extends PagingAndSortingRepository<Lesson, Integer> {

    Optional<Lesson> findById(int id);

    Page<Lesson> findAll(Pageable pageable);

    List<Lesson> findAll();

    Lesson save(Lesson lesson);

    void delete(Lesson lesson);

    @Query("from Lesson l where l.teacher.id = ?1 and l.date between ?2 and ?3")
    List<Lesson> getByTeacherBetweenDates(int teacherId, LocalDate start, LocalDate end);

    @Query("from Lesson l where l.date = ?1 and l.teacher.id = ?2 and l.lessonTime.id = ?3")
    Optional<Lesson> getByDateAndTeacherAndLessonTime(LocalDate date, int teacherId, int lessonTimeId);

    @Query("from Lesson l where l.teacher.id = ?1 and l.date = ?2")
    List<Lesson> getByTeacherAndDate(int teacherId, LocalDate date);

    @Query("select l from Lesson l join l.groups as g  where g.id = ?1 and l.date = ?2")
    List<Lesson> getByGroupByDate(int groupId, LocalDate date);

    @Query("select l from Lesson l join l.groups as g  where g.id = ?1 and l.date between ?2 and ?3")
    List<Lesson> getByGroupBetweenDates(int groupId, LocalDate start, LocalDate end);

    @Query("from Lesson l where l.date = ?1 and l.audience.id = ?2 and l.lessonTime.id = ?3")
    Optional<Lesson> getByDateAndAudienceAndLessonTime(LocalDate date, int audienceId, int lessonTimeId);

    @Query("select l from Lesson l inner join l.groups as g where l.date = ?1 and l.lessonTime.id = ?2 and g.id = ?3")
    Optional<Lesson> getByDateAndLessonTimeAndGroup(LocalDate date, int lessonTimeId, int groupId);

}
