package ua.com.foxminded.krailo.university.entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.print.attribute.standard.MediaSize.NA;

public class Lesson {

    private LocalDate date;
    private Subject subject;
    private Audience audience;
    private LessonTime lessonTime;
    private Teacher teacher;
    private List<Group> groups;

    public Lesson() {
    }

    public Lesson(LocalDate date, Subject subject, Audience audience, LessonTime lessonTime, Teacher teacher) {
	this.date = date;
	this.subject = subject;
	this.audience = audience;
	this.lessonTime = lessonTime;
	this.teacher = teacher;
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

    public List<Group> getGroups() {
	return groups;
    }

    public void setGroups(List<Group> groups) {
	this.groups = groups;
    }

    @Override
    public String toString() {
	return lessonTime.getLessonNumber() + " " + subject + " " + lessonTime + " " + audience + " " + teacher;
    }

}
