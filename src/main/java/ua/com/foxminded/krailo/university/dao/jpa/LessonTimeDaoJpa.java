package ua.com.foxminded.krailo.university.dao.jpa;

import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.foxminded.krailo.university.model.LessonTime;

public interface LessonTimeDaoJpa extends PagingAndSortingRepository<LessonTime, Integer> {

    Optional<LessonTime> findById(int id);

    Iterable<LessonTime> findAll();

    LessonTime save(LessonTime lessonTime);

    void delete(LessonTime lessonTime);

    @Query("from LessonTime l where ?1 between l.startTime and l.endTime or ?2 between l.startTime and l.endTime order by l.startTime")
    Optional<LessonTime> getByStartOrEndLessonTime(LocalTime start, LocalTime end);

}
