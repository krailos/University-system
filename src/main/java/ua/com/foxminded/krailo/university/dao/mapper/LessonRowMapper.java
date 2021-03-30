package ua.com.foxminded.krailo.university.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.dao.AudienceDao;
import ua.com.foxminded.krailo.university.dao.GroupDao;
import ua.com.foxminded.krailo.university.dao.LessonTimeDao;
import ua.com.foxminded.krailo.university.dao.SubjectDao;
import ua.com.foxminded.krailo.university.dao.TeacherDao;
import ua.com.foxminded.krailo.university.model.Lesson;

@Component
public class LessonRowMapper implements RowMapper<Lesson> {

    private LessonTimeDao lessonTimeDao;
    private SubjectDao subjectDao;
    private TeacherDao teacherDao;
    private AudienceDao audienceDao;
    private GroupDao groupDao;

    public LessonRowMapper(LessonTimeDao lessonTimeDao, SubjectDao subjectDao, TeacherDao teacherDao,
	    AudienceDao audienceDao, GroupDao groupDao) {
	this.lessonTimeDao = lessonTimeDao;
	this.subjectDao = subjectDao;
	this.teacherDao = teacherDao;
	this.audienceDao = audienceDao;
	this.groupDao = groupDao;
    }

    @Override
    public Lesson mapRow(ResultSet rs, int rowNum) throws SQLException {
	Lesson lesson = new Lesson();
	lesson.setId(rs.getInt("id"));
	lesson.setDate(rs.getObject("date", LocalDate.class));
	lessonTimeDao.findById(rs.getInt("lesson_time_id")).ifPresent(lesson::setLessonTime);
	subjectDao.findById(rs.getInt("subject_id")).ifPresent(lesson::setSubject);
	teacherDao.findById(rs.getInt("teacher_id")).ifPresent(lesson::setTeacher);
	audienceDao.findById(rs.getInt("audience_id")).ifPresent(lesson::setAudience);
	lesson.setGroups(groupDao.findByLessonId(lesson.getId()));
	return lesson;
    }

}