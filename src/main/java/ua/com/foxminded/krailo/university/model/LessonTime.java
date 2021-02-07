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

    public LessonTime(String orderNumber, LocalTime startTime, LocalTime endTime,
	    LessonsTimeSchedule lessonsTimeSchedule) {
	this.orderNumber = orderNumber;
	this.startTime = startTime;
	this.endTime = endTime;
	this.lessonsTimeSchedule = lessonsTimeSchedule;
    }

    public LessonTime(int id, String orderNumber, LocalTime startTime, LocalTime endTime,
	    LessonsTimeSchedule lessonsTimeSchedule) {
	this.id = id;
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
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
	result = prime * result + id;
	result = prime * result + ((orderNumber == null) ? 0 : orderNumber.hashCode());
	result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	LessonTime other = (LessonTime) obj;
	if (endTime == null) {
	    if (other.endTime != null)
		return false;
	} else if (!endTime.equals(other.endTime))
	    return false;
	if (id != other.id)
	    return false;
	if (orderNumber == null) {
	    if (other.orderNumber != null)
		return false;
	} else if (!orderNumber.equals(other.orderNumber))
	    return false;
	if (startTime == null) {
	    if (other.startTime != null)
		return false;
	} else if (!startTime.equals(other.startTime))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "from: " + startTime + " - " + "to: " + endTime;
    }

}
