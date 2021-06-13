package ua.com.foxminded.krailo.university.dao.interf;

import java.util.List;
import java.util.Optional;

import ua.com.foxminded.krailo.university.model.LessonTime;

public interface LessonTimeDaoInt extends GenericDao<LessonTime> {

    List<LessonTime> getAll();
    
    Optional<LessonTime> getByStartOrEndLessonTime(LessonTime lessonTime);
    
}
