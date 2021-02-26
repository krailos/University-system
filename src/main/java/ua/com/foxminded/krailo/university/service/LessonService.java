package ua.com.foxminded.krailo.university.service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.HolidayDao;
import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.TeacherDao;
import ua.com.foxminded.krailo.university.dao.VocationDao;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Lesson;

@Service
public class LessonService {

    private LessonDao lessonDao;
    private VocationDao vocationDao;
    private HolidayDao holidayDao;
    private TeacherDao teacherDao;

    public LessonService(LessonDao lessonDao, VocationDao vocationDao, HolidayDao holidayDao, TeacherDao teacherDao) {
	this.lessonDao = lessonDao;
	this.vocationDao = vocationDao;
	this.holidayDao = holidayDao;
	this.teacherDao = teacherDao;
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
	return Optional.ofNullable(lessonDao.findByDateAndTeacherAndLessonTime(lesson)).isEmpty();
    }

    private boolean isAudienceFree(Lesson lesson) {
	return Optional.ofNullable(lessonDao.findByDateAndAudienceAndLessonTime(lesson)).isEmpty();
    }

    private boolean isEnoughAudienceCapacity(Lesson lesson) {
	return (lesson.getAudience().getCapacity() >= lesson.getGroups().stream().map(Group::getStudents)
		.mapToInt(List::size).sum());
    }

    private boolean isTeacherOnVocation(Lesson lesson) {
	return Optional.ofNullable(vocationDao.findByTeacherIdAndDate(lesson.getTeacher().getId(), lesson.getDate()))
		.isPresent();
    }

    private boolean isLessonDateOutoffHoliday(Lesson lesson) {
	return Optional.ofNullable(holidayDao.findByDate(lesson.getDate())).isEmpty();
    }

    private boolean isLessonDateWeekday(Lesson lesson) {
	return !(lesson.getDate().getDayOfWeek() == DayOfWeek.SATURDAY
		|| lesson.getDate().getDayOfWeek() == DayOfWeek.SUNDAY);
    }

    private boolean isTeacherTeachesLessonSubject(Lesson lesson) {
	return Optional.ofNullable(
		teacherDao.findByTeacherIdAndSubjectId(lesson.getTeacher().getId(), lesson.getSubject().getId()))
		.isPresent();
    }

    private boolean isLessonGroupsFree(Lesson lesson) {
	return lesson.getGroups().stream()
		.map(g -> lessonDao.findByDateAndLessonTimeIdAndGroupId(lesson.getDate(),
			lesson.getLessonTime().getId(), g.getId()))
		.filter(Objects::nonNull).collect(Collectors.toList()).isEmpty();
    }

}
