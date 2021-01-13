package ua.com.foxminded.krailo.university.model;

import java.time.LocalTime;

public class LessonTime {

    private int id;
    private String lessonOrder;
    private LocalTime startTime;
    private LocalTime endTime;
    private LessonsTimeSchedule lessonsTimeSchedule;

    public LessonTime() {
    }

    public LessonTime(String lessonOrder, LocalTime startTime, LocalTime endTime) {
	this.lessonOrder = lessonOrder;
	this.startTime = startTime;
	this.endTime = endTime;
    }

    public int getId() {
	return id;
    }

    public String getLessonOrder() {
	return lessonOrder;
    }

    public void setLessonOrder(String lessonOrder) {
	this.lessonOrder = lessonOrder;
    }

    public void setId(int id) {
	this.id = id;
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
