package ua.com.foxminded.krailo.university.service;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.HolidayDao;
import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.VocationDao;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Lesson;

@Service
public class LessonService {

    private LessonDao lessonDao;
    private VocationDao vocationDao;
    private HolidayDao holidayDao;

    public LessonService(LessonDao lessonDao, VocationDao vocationDao, HolidayDao holidayDao) {
	this.lessonDao = lessonDao;
	this.vocationDao = vocationDao;
	this.holidayDao = holidayDao;
    }

    public void create(Lesson lesson) {
	if (isTeacherFree(lesson) && isEnoughAudienceCapacity(lesson) && !isTeacherOnVocation(lesson)
		&& isAudienceFree(lesson) && isLessonDateOutoffHoliday(lesson) && isLessonDateWeekday(lesson)
		&& isTeacherTeachesLessonSubject(lesson) && isLessonGroupsFree(lesson)) {
	    lessonDao.create(lesson);
	}
    }

    public void update(Lesson lesson) {
	if (isTeacherFree(lesson) && isEnoughAudienceCapacity(lesson) && !isTeacherOnVocation(lesson)
		&& isAudienceFree(lesson) && isLessonDateOutoffHoliday(lesson) && isLessonDateWeekday(lesson)
		&& isTeacherTeachesLessonSubject(lesson) && isLessonGroupsFree(lesson)) {
	    lessonDao.update(lesson);
	}
    }

    public Lesson getById(int id) {
	return lessonDao.findById(id);
    }

    public List<Lesson> getAll() {
	return lessonDao.findAll();
    }

    public void delete(Lesson lesson) {
	lessonDao.deleteById(lesson.getId());
    }

    private boolean isTeacherFree(Lesson lesson) {
	return lessonDao.findByDateAndTeacherAndLessonTime(lesson).isEmpty();
    }

    private boolean isAudienceFree(Lesson lesson) {
	return lessonDao.findByDateAndAudienceAndLessonTime(lesson).isEmpty();
    }

    private boolean isEnoughAudienceCapacity(Lesson lesson) {
	return (lesson.getAudience().getCapacity() >= lesson.getGroups().stream().map(Group::getStudents)
		.mapToInt(List::size).sum());
    }

    private boolean isTeacherOnVocation(Lesson lesson) {
	return vocationDao.findByTeacherIdAndDate(lesson.getTeacher().getId(), lesson.getDate()).isPresent();
    }

    private boolean isLessonDateOutoffHoliday(Lesson lesson) {
	return holidayDao.findByDate(lesson.getDate()).isEmpty();
    }

    private boolean isLessonDateWeekday(Lesson lesson) {
	return !(lesson.getDate().getDayOfWeek() == DayOfWeek.SATURDAY
		|| lesson.getDate().getDayOfWeek() == DayOfWeek.SUNDAY);
    }

    private boolean isTeacherTeachesLessonSubject(Lesson lesson) {
	return lesson.getTeacher().getSubjects().contains(lesson.getSubject());
    }

    private boolean isLessonGroupsFree(Lesson lesson) {
	return lesson.getGroups().stream().noneMatch(g -> lessonDao
		.findByDateAndLessonTimeIdAndGroupId(lesson.getDate(), lesson.getLessonTime().getId(), g.getId())
		.isPresent());
    }

}
