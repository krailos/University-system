package ua.com.foxminded.krailo.domain;

import java.sql.Time;
import java.time.LocalTime;

public class LessonTime {

    private String lessonNumber;
    private LocalTime startTime;
    private LocalTime endTime;

    public LessonTime() {
    }

    public LessonTime(String lessonNumber, LocalTime startTime, LocalTime endTime) {
	super();
	this.lessonNumber = lessonNumber;
	this.startTime = startTime;
	this.endTime = endTime;
    }

    public String getLessonNumber() {
	return lessonNumber;
    }

    public void setLessonNumber(String lessonNumber) {
	this.lessonNumber = lessonNumber;
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

    @Override
    public String toString() {
	return "from: "+startTime+" - "+"to: "+endTime;
    }

}
