package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

public class LessonsTimeSchedule {

    private int id;
    private String name;
    private List<LessonTime> lessonTimes = new ArrayList<>();

    public LessonsTimeSchedule() {
    }

    public LessonsTimeSchedule(int id, String name) {
	this.id = id;
	this.name = name;
    }

    public LessonsTimeSchedule(String name) {
	this.name = name;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
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
