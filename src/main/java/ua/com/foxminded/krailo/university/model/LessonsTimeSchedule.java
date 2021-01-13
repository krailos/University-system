package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

public class LessonsTimeSchedule {
  
    private String name;
    private List<LessonTime> lessonTimes = new ArrayList<>();

    public LessonsTimeSchedule() {
    }

    public LessonsTimeSchedule(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public List<LessonTime> getLessonTimes() {
	return lessonTimes;
    }

    public void setLessonTimes(List<LessonTime> lessonTimes) {
	this.lessonTimes = lessonTimes;
    }
    
    @Override
    public String toString() {
        return name;
    }

}
