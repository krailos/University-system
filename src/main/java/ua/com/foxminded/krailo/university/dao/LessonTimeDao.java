package ua.com.foxminded.krailo.university.dao;

import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ua.com.foxminded.krailo.university.model.LessonTime;

public interface LessonTimeDao extends JpaRepository<LessonTime, Integer> {

    @Query("from LessonTime l where :start between l.startTime and l.endTime or :end between l.startTime and l.endTime order by l.startTime")
    Optional<LessonTime> findByStartTimeAndEndTimeBetween(LocalTime start, LocalTime end);

}
