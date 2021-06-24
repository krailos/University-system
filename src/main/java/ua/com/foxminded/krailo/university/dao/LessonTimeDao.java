package ua.com.foxminded.krailo.university.dao;

import java.util.Optional;

import ua.com.foxminded.krailo.university.model.LessonTime;

public interface LessonTimeDao extends GenericDao<LessonTime> {

    Optional<LessonTime> getByStartOrEndLessonTime(LessonTime lessonTime);

}
