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
		&& isAudienceFree(lesson) && isLessonDateOutoffHoliday(lesson) && isLessonDateWeekday(lesson)) {
	    lessonDao.create(lesson);
	}
    }

    public void update(Lesson lesson) {
	if (isTeacherFree(lesson) && isEnoughAudienceCapacity(lesson) && !isTeacherOnVocation(lesson)
		&& isAudienceFree(lesson) && isLessonDateOutoffHoliday(lesson) && isLessonDateWeekday(lesson)) {
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
	return lessonDao.findByDateByTeacherByLessonTime(lesson) == null;
    }

    private boolean isAudienceFree(Lesson lesson) {
	return lessonDao.findByDateByAudienceByLessonTime(lesson) == null;
    }

    private boolean isEnoughAudienceCapacity(Lesson lesson) {
	return (lesson.getAudience().getCapacity() >= lesson.getGroups().stream().map(Group::getStudents)
		.mapToInt(List::size).sum());
    }

    private boolean isTeacherOnVocation(Lesson lesson) {
	return vocationDao.findByTeacherIdAndDate(lesson.getTeacher().getId(), lesson.getDate()) != null;
    }

    private boolean isLessonDateOutoffHoliday(Lesson lesson) {
	return holidayDao.findByDate(lesson.getDate()) == null;
    }

    private boolean isLessonDateWeekday(Lesson lesson) {
	return !(lesson.getDate().getDayOfWeek() == DayOfWeek.SATURDAY
		|| lesson.getDate().getDayOfWeek() == DayOfWeek.SUNDAY);
    }

}
