package ua.com.foxminded.krailo.university.model;

import java.time.LocalTime;

public class LessonTime {

    private int id;
    private String orderNumber;
    private LocalTime startTime;
    private LocalTime endTime;
    private LessonsTimeSchedule lessonsTimeSchedule;

    public LessonTime() {
    }

    public LessonTime(int id) {
	this.id = id;
    }

    public LessonTime(int id, String orderNumber, LocalTime startTime, LocalTime endTime,
	    LessonsTimeSchedule lessonsTimeSchedule) {
	super();
	this.id = id;
	this.orderNumber = orderNumber;
	this.startTime = startTime;
	this.endTime = endTime;
	this.lessonsTimeSchedule = lessonsTimeSchedule;
    }

    public LessonTime(String orderNumber, LocalTime startTime, LocalTime endTime,
	    LessonsTimeSchedule lessonsTimeSchedule) {
	super();
	this.orderNumber = orderNumber;
	this.startTime = startTime;
	this.endTime = endTime;
	this.lessonsTimeSchedule = lessonsTimeSchedule;
    }

    public int getId() {
	return id;
    }

    public String getOrderNumber() {
	return orderNumber;
    }

    public void setOrderNumber(String lessonOrder) {
	this.orderNumber = lessonOrder;
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
