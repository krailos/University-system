package ua.com.foxminded.krailo.university.dao;

import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.foxminded.krailo.university.model.LessonTime;

public interface LessonTimeDao extends PagingAndSortingRepository<LessonTime, Integer> {

    @Query("from LessonTime l where ?1 between l.startTime and l.endTime or ?2 between l.startTime and l.endTime order by l.startTime")
    Optional<LessonTime> getByStartOrEndLessonTime(LocalTime start, LocalTime end);

}
