package ua.com.foxminded.krailo.university.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.dao.AudienceDao;
import ua.com.foxminded.krailo.university.dao.LessonTimeDao;
import ua.com.foxminded.krailo.university.dao.SubjectDao;
import ua.com.foxminded.krailo.university.dao.TeacherDao;
import ua.com.foxminded.krailo.university.dao.TimetableDao;
import ua.com.foxminded.krailo.university.model.Lesson;

@Component
public class LessonRowMapper implements RowMapper<Lesson> {

    private LessonTimeDao lessonTimeDao;
    private SubjectDao subjectDao;
    private TeacherDao teacherDao;
    private AudienceDao audienceDao;
    private TimetableDao timetableDao;

    public LessonRowMapper(LessonTimeDao lessonTimeDao, SubjectDao subjectDao, TeacherDao teacherDao,
	    AudienceDao audienceDao, TimetableDao timetableDao) {
	this.lessonTimeDao = lessonTimeDao;
	this.subjectDao = subjectDao;
	this.teacherDao = teacherDao;
	this.audienceDao = audienceDao;
	this.timetableDao = timetableDao;
    }

    @Override
    public Lesson mapRow(ResultSet rs, int rowNum) throws SQLException {
	Lesson lesson = new Lesson();
	lesson.setId(rs.getInt("id"));
	lesson.setDate(rs.getObject("date", LocalDate.class));
	lesson.setLessonTime(lessonTimeDao.findById(rs.getInt("lesson_time_id")));
	lesson.setSubject(subjectDao.findById(rs.getInt("subject_id")));
	lesson.setTeacher(teacherDao.findById(rs.getInt("teacher_id")));
	lesson.setAudience(audienceDao.findById(rs.getInt("audience_id")));
	lesson.setTimetable(timetableDao.findById(rs.getInt("timetable_id")));
	return lesson;
    }

}
