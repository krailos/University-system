package ua.com.foxminded.krailo.domain;

import java.util.Date;
import java.util.List;

public class Lesson {
    private Date date;
    private Subject subject; 
    private Audience audience;
    private LessonTime lessonTime;
    private Teacher teacher;
    private List<Group> groups;

}
