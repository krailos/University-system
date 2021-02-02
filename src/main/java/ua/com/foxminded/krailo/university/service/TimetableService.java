package ua.com.foxminded.krailo.university.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.AudienceDao;
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

    public TimetableService(TimetableDao timetableDao, LessonDao lessonDao, YearDao yearDao,
	    SpecialityDao specialityDao, LessonTimeDao lessonTimeDao, SubjectDao subjectDao, TeacherDao teacherDao,
	    AudienceDao audienceDao) {
	super();
	this.timetableDao = timetableDao;
	this.lessonDao = lessonDao;
	this.yearDao = yearDao;
	this.specialityDao = specialityDao;
	this.lessonTimeDao = lessonTimeDao;
	this.subjectDao = subjectDao;
	this.teacherDao = teacherDao;
	this.audienceDao = audienceDao;
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

    public void addLessonToTimetable(LocalDate date, int lissonTimeId, int subjectId, int teacherId, int audienceId,
	    int timeTableId) {
	Lesson lesson = new Lesson();
	lesson.setDate(date);
	LessonTime lessonTime = lessonTimeDao.findById(lissonTimeId);
	lesson.setLessonTime(lessonTime);
	Subject subject = subjectDao.findById(subjectId);
	lesson.setSubject(subject);
	Teacher teacher = teacherDao.findById(teacherId);
	lesson.setTeacher(teacher);
	Audience audience = audienceDao.findById(audienceId);
	lesson.setAudience(audience);
	Timetable timetable = timetableDao.findById(timeTableId);
	lesson.setTimetable(timetable);
	lessonDao.create(lesson);
    }
}
