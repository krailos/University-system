package ua.com.foxminded.krailo.university.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Lesson {

    private int id;
    private LocalDate date;
    private Subject subject;
    private Audience audience;
    private LessonTime lessonTime;
    private Teacher teacher;
    private Timetable timetable;
    private List<Group> groups = new ArrayList<>();

    public Lesson() {
    }

    public Lesson(LocalDate date, Subject subject, Audience audience, LessonTime lessonTime, Teacher teacher) {
	this.date = date;
	this.subject = subject;
	this.audience = audience;
	this.lessonTime = lessonTime;
	this.teacher = teacher;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public LocalDate getDate() {
	return date;
    }

    public void setDate(LocalDate date) {
	this.date = date;
    }

    public Subject getSubject() {
	return subject;
    }

    public void setSubject(Subject subject) {
	this.subject = subject;
    }

    public Audience getAudience() {
	return audience;
    }

    public void setAudience(Audience audience) {
	this.audience = audience;
    }

    public LessonTime getLessonTime() {
	return lessonTime;
    }

    public void setLessonTime(LessonTime lessonTime) {
	this.lessonTime = lessonTime;
    }

    public Teacher getTeacher() {
	return teacher;
    }

    public void setTeacher(Teacher teacher) {
	this.teacher = teacher;
    }

    public Timetable getTimetable() {
	return timetable;
    }

    public void setTimetable(Timetable timetable) {
	this.timetable = timetable;
    }

    public List<Group> getGroups() {
	return groups;
    }

    public void setGroups(List<Group> groups) {
	this.groups = groups;
    }

    @Override
    public String toString() {
	return lessonTime.getOrderNumber() + " " + subject + " " + lessonTime + " " + audience + " " + teacher;
    }

}
