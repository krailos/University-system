package ua.com.foxminded.krailo.university.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.AudienceDao;
import ua.com.foxminded.krailo.university.dao.BuildingDao;
import ua.com.foxminded.krailo.university.dao.GroupDao;
import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.LessonTimeDao;
import ua.com.foxminded.krailo.university.dao.SpecialityDao;
import ua.com.foxminded.krailo.university.dao.SubjectDao;
import ua.com.foxminded.krailo.university.dao.TeacherDao;
import ua.com.foxminded.krailo.university.dao.TimetableDao;
import ua.com.foxminded.krailo.university.dao.YearDao;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.Timetable;
import ua.com.foxminded.krailo.university.model.Year;

@Service
public class TimetableService {

    private TimetableDao timetableDao;
    private LessonDao lessonDao;
    private YearDao yearDao;
    private SpecialityDao specialityDao;
    private LessonTimeDao lessonTimeDao;
    private SubjectDao subjectDao;
    private TeacherDao teacherDao;
    private AudienceDao audienceDao;
    private BuildingDao buildingDao;
    private GroupDao groupDao;

    public TimetableService(TimetableDao timetableDao, LessonDao lessonDao, YearDao yearDao,
	    SpecialityDao specialityDao, LessonTimeDao lessonTimeDao, SubjectDao subjectDao, TeacherDao teacherDao,
	    AudienceDao audienceDao, BuildingDao buildingDao, GroupDao groupDao) {
	super();
	this.timetableDao = timetableDao;
	this.lessonDao = lessonDao;
	this.yearDao = yearDao;
	this.specialityDao = specialityDao;
	this.lessonTimeDao = lessonTimeDao;
	this.subjectDao = subjectDao;
	this.teacherDao = teacherDao;
	this.audienceDao = audienceDao;
	this.buildingDao = buildingDao;
	this.groupDao = groupDao;
    }

    public void addTimetable(String name, int yearId) {
	Timetable timetable = new Timetable();
	timetable.setName(name);
	Year year = yearDao.findById(yearId);
	timetable.setYear(year);
	timetable.setYear(year);
	timetable.setSpeciality(year.getSpeciality());
	timetableDao.create(timetable);
    }

    public void addLesson(LocalDate date, int timeTableId, int subjectId, int audienceId, int lessonTimeId,
	    int teacherId) {
	Lesson lesson = new Lesson();
	List<Lesson> lessons = lessonDao.findAll();
	lesson.setDate(date);
	lesson.setTimetable(timetableDao.findById(timeTableId));
	lesson.setSubject(subjectDao.findById(subjectId));
	checkSubject(lesson);
	lesson.setAudience(audienceDao.findById(audienceId));
	lesson.setLessonTime(lessonTimeDao.findById(lessonTimeId));
	checkLessonTime(lessons, lesson);
	lesson.setTeacher(teacherDao.findById(teacherId));
	checkTeacher(lessons, lesson);
	lessonDao.create(lesson);
    }

    public void addGroupToLesson(int lessonId, int groupId) {
	Lesson lesson = lessonDao.findById(lessonId);
	lesson.getGroups().add(groupDao.findById(groupId));
	lessonDao.update(lesson);
    }

    public void updateLesson(Lesson lesson) {
	lessonDao.update(lesson);
    }

    private void checkSubject(Lesson lesson) {
	if (!lesson.getTimetable().getYear().getSubjects().contains(lesson.getSubject())) {
	    throw new RuntimeException("year doesn't cintain subject that you want to add");
	}
    }

    private void checkLessonTime(List<Lesson> lessons, Lesson lesson) {
	if (lessons.stream().filter(l -> lesson.getDate().equals(l.getDate()))
		.filter(l -> lesson.getAudience().equals(l.getAudience()))
		.filter(l -> lesson.getLessonTime().equals(l.getLessonTime())).count() > 0) {
	    throw new RuntimeException("this lesson time is already booked");
	}
    }

    private void checkTeacher(List<Lesson> lessons, Lesson lesson) {
	if (lessons.stream().filter(l -> lesson.getDate().equals(l.getDate()))
		.filter(l -> lesson.getLessonTime().equals(l.getLessonTime())).filter(l -> lesson.getTeacher().equals(l.getTeacher()))
		.count() > 0) {
	    throw new RuntimeException("this teacher has a lesson at this time");
	}
    }

}
