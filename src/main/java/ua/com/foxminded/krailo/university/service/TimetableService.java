package ua.com.foxminded.krailo.university.service;

import java.time.LocalDate;

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
import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
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
	lesson.setDate(date);
	Timetable timetable = timetableDao.findById(timeTableId);
	lesson.setTimetable(timetable);
	Subject subject = subjectDao.findById(subjectId);
	if (timetable.getYear().getSubjects().contains(subject)) {
	    lesson.setSubject(subject);
	} else {
	    throw new RuntimeException("year doesn't cintain subject that you want to add");
	}
	Audience audience = audienceDao.findById(audienceId);
	lesson.setAudience(audienceDao.findById(audienceId));
	LessonTime lessonTime = lessonTimeDao.findById(lessonTimeId);
	if (lessonDao.findAll().stream().filter(t -> date.equals(t.getDate()))
		.filter(t -> audience.equals(t.getAudience())).filter(l -> lessonTime.equals(l.getLessonTime()))
		.count() > 0) {
	    throw new RuntimeException("this lesson time is already booked");
	} else {
	    lesson.setLessonTime(lessonTime);
	}
	Teacher teacher = teacherDao.findById(teacherId);
	if (lessonDao.findAll().stream().filter(t -> date.equals(t.getDate()))
		.filter(l -> lessonTime.equals(l.getLessonTime())).filter(l -> teacher.equals(l.getTeacher()))
		.count() > 0) {
	    throw new RuntimeException("this teacher has a lesson at this time");
	}
	lesson.setTeacher(teacher);
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
    
    private setDate
    
}
