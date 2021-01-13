package ua.com.foxminded.krailo.university.model;

import java.time.LocalTime;

public class LessonTime {

    private int id;
    private String lesson;
    private LocalTime startTime;
    private LocalTime endTime;
    private LessonsTimeSchedule lessonsTimeSchedule;

    public LessonTime() {
    }

    public LessonTime(String lesson, LocalTime startTime, LocalTime endTime) {
	super();
	this.lesson = lesson;
	this.startTime = startTime;
	this.endTime = endTime;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getLesson() {
	return lesson;
    }

    public void setLesson(String lesson) {
	this.lesson = lesson;
    }

    public LocalTime getStartTime() {
	return startTime;
    }

    public void setStartTime(LocalTime startTime) {
	this.startTime = startTime;
    }

    public LocalTime getEndTime() {
	return endTime;
    }

    public void setEndTime(LocalTime endTime) {
	this.endTime = endTime;
    }

    public LessonsTimeSchedule getLessonsTimeSchedule() {
	return lessonsTimeSchedule;
    }

    public void setLessonsTimeSchedule(LessonsTimeSchedule lessonsTimeSchedule) {
	this.lessonsTimeSchedule = lessonsTimeSchedule;
    }

    @Override
    public String toString() {
	return "from: " + startTime + " - " + "to: " + endTime;
    }

}
