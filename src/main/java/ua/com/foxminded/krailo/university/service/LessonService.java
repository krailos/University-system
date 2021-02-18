package ua.com.foxminded.krailo.university.service;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.HolidayDao;
import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.VocationDao;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Holiday;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.Vocation;

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
	List<Lesson> lessons = lessonDao.findByDate(lesson);
	List<Vocation> vocations = vocationDao.findByTeacherId(lesson.getTeacher().getId());
	List<Holiday> holidays = holidayDao.findAll();
	if (isSubjectExistIntoYear(lesson) && isLessonTimeFree(lessons, lesson) && isTeacherFree(lessons, lesson)
		&& isGroupExistIntoYear(lesson) && isCapacityBigEnough(lesson) && isTeacherAtWork(vocations, lesson)
		&& isAudienceFree(lessons, lesson) && isLessonDateOutoffHoliday(holidays, lesson)
		&& isLessonDateOutoffWeekend(lesson)) {
	    lessonDao.create(lesson);
	}
    }

    public void update(Lesson lesson) {
	List<Lesson> lessons = lessonDao.findByDate(lesson);
	List<Vocation> vocations = vocationDao.findByTeacherId(lesson.getTeacher().getId());
	List<Holiday> holidays = holidayDao.findAll();
	if (isSubjectExistIntoYear(lesson) && isLessonTimeFree(lessons, lesson) && isTeacherFree(lessons, lesson)
		&& isGroupExistIntoYear(lesson) && isCapacityBigEnough(lesson) && isTeacherAtWork(vocations, lesson)
		&& isAudienceFree(lessons, lesson) && isLessonDateOutoffHoliday(holidays, lesson)
		&& isLessonDateOutoffWeekend(lesson)) {
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

    private boolean isSubjectExistIntoYear(Lesson lesson) {
	return lesson.getTimetable().getYear().getSubjects().contains(lesson.getSubject());
    }

    private boolean isLessonTimeFree(List<Lesson> lessons, Lesson lesson) {
	return lessons.stream().filter(l -> lesson.getAudience().equals(l.getAudience())).map(Lesson::getLessonTime)
		.noneMatch(t -> t.equals(lesson.getLessonTime()));
    }

    private boolean isTeacherFree(List<Lesson> lessons, Lesson lesson) {
	return lessons.stream().filter(l -> lesson.getLessonTime().equals(l.getLessonTime())).map(Lesson::getTeacher)
		.noneMatch(t -> t.equals(lesson.getTeacher()));
    }

    private boolean isTeacherAtWork(List<Vocation> vocations, Lesson lesson) {
	return vocations.stream().noneMatch(v -> (v.getStart().isAfter(lesson.getDate().minusDays(1))
		&& v.getEnd().isBefore(lesson.getDate().plusDays(1))));
    }

    private boolean isAudienceFree(List<Lesson> lessons, Lesson lesson) {
	return lessons.stream().filter(l -> l.getLessonTime().equals(lesson.getLessonTime())).map(Lesson::getAudience)
		.noneMatch(a -> a.equals(lesson.getAudience()));
    }

    private boolean isLessonDateOutoffHoliday(List<Holiday> holidays, Lesson lesson) {
	return holidays.stream().noneMatch(h -> h.getDate().equals(lesson.getDate()));
    }

    private boolean isLessonDateOutoffWeekend(Lesson lesson) {
	return (lesson.getDate().getDayOfWeek().equals(DayOfWeek.SATURDAY)
		&& lesson.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY));
    }

    private boolean isGroupExistIntoYear(Lesson lesson) {
	return lesson.getGroups().stream().allMatch(g -> lesson.getTimetable().getYear().getGroups().contains(g));
    }

    private boolean isCapacityBigEnough(Lesson lesson) {
	return (lesson.getAudience().getCapacity() >= lesson.getGroups().stream().map(Group::getStudents)
		.mapToInt(List::size).sum());
    }

}
