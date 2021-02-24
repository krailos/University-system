package ua.com.foxminded.krailo.university.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.dao.mapper.LessonRowMapper;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.model.Teacher;

@Component
public class LessonDao {

    private static final String SQL_SELECT_ALL = "SELECT * FROM lessons ORDER BY id";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM lessons WHERE id = ?";
    private static final String SQL_UPDATE_BY_ID = "UPDATE lessons SET date = ?, lesson_time_id = ?, subject_id = ?, teacher_id = ?, audience_id = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM lessons WHERE id = ?";
    private static final String SQL_INSERT_INTO_LESSONS = "INSERT INTO lessons (date, lesson_time_id, subject_id, teacher_id, audience_id) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_INSERT_INTO_LESSONS_GROUPS = "INSERT INTO lessons_groups (lesson_id, group_id) VALUES (?, ?)";
    private static final String SQL_DELETE_LESSONS_GROUPS_BY_LESSON_ID_GROUP_ID = "DELETE FROM lessons_groups WHERE lesson_id = ? AND group_id = ?";
    private static final String SQL_SELECT_BY_DATE = "SELECT * FROM lessons WHERE date = ?";
    private static final String SQL_SELECT_BY_TEACHER_BETWEEN_DATES = "SELECT * FROM lessons WHERE teacher_id = ? AND date BETWEEN ? AND ?";
    private static final String SQL_SELECT_BY_TEACHER_BY_DATE = "SELECT * FROM lessons WHERE teacher_id = ? AND date = ?";
    private static final String SQL_SELECT_BY_STUDENT_BETWEEN_DATES = "SELECT lessons.id, date, lesson_time_id, subject_id, teacher_id, audience_id, lesson_id, lessons_groups.group_id,"
	    + "students.id  FROM lessons JOIN lessons_groups ON (lessons.id = lessons_groups.lesson_id) JOIN students ON (lessons_groups.group_id = students.group_id)"
	    + " WHERE students.id = ? AND date BETWEEN ? AND ?";
    private static final String SQL_SELECT_BY_STUDENT_BY_DATE = "SELECT lessons.id, date, lesson_time_id, subject_id, teacher_id, audience_id, lesson_id, lessons_groups.group_id,"
	    + "students.id  FROM lessons JOIN lessons_groups ON (lessons.id = lessons_groups.lesson_id) JOIN students ON (lessons_groups.group_id = students.group_id)"
	    + " WHERE students.id = ? AND date = ?";
    private static final String SQL_SELECT_BY_DATE_BY_TEACHER_BY_LESSON_TIME = "SELECT * FROM lessons WHERE date = ? AND teacher_id = ? AND lesson_time_id = ?";
    private static final String SQL_SELECT_BY_DATE_BY_AUDIENCE_BY_LESSON_TIME = "SELECT * FROM lessons WHERE date = ? AND audience_id = ? AND lesson_time_id = ?";

    private JdbcTemplate jdbcTemplate;
    private LessonRowMapper lessonRowMapper;

    public LessonDao(JdbcTemplate jdbcTemplate, LessonRowMapper lessonRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.lessonRowMapper = lessonRowMapper;
    }

    public Lesson findById(int id) {
	return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, lessonRowMapper, id);
    }

    public List<Lesson> findAll() {
	return jdbcTemplate.query(SQL_SELECT_ALL, lessonRowMapper);
    }

    public void create(Lesson lesson) {
	KeyHolder keyHolder = new GeneratedKeyHolder();
	jdbcTemplate.update(connection -> {
	    PreparedStatement ps = connection.prepareStatement(SQL_INSERT_INTO_LESSONS, new String[] { "id" });
	    ps.setObject(1, lesson.getDate());
	    ps.setInt(2, lesson.getLessonTime().getId());
	    ps.setInt(3, lesson.getSubject().getId());
	    ps.setInt(4, lesson.getTeacher().getId());
	    ps.setInt(5, lesson.getAudience().getId());
	    return ps;
	}, keyHolder);
	lesson.setId(keyHolder.getKey().intValue());
	for (Group group : lesson.getGroups()) {
	    jdbcTemplate.update(SQL_INSERT_INTO_LESSONS_GROUPS, lesson.getId(), group.getId());
	}
    }

    public void update(Lesson lesson) {
	jdbcTemplate.update(SQL_UPDATE_BY_ID, lesson.getDate(), lesson.getLessonTime().getId(),
		lesson.getSubject().getId(), lesson.getTeacher().getId(), lesson.getAudience().getId(), lesson.getId());
	List<Group> groupsOld = findById(lesson.getId()).getGroups();
	groupsOld.stream().filter(g -> !lesson.getGroups().contains(g)).forEach(
		g -> jdbcTemplate.update(SQL_DELETE_LESSONS_GROUPS_BY_LESSON_ID_GROUP_ID, lesson.getId(), g.getId()));
	lesson.getGroups().stream().filter(g -> !groupsOld.contains(g))
		.forEach(g -> jdbcTemplate.update(SQL_INSERT_INTO_LESSONS_GROUPS, lesson.getId(), g.getId()));
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

    public List<Lesson> findByDate(Lesson lesson) {
	return jdbcTemplate.query(SQL_SELECT_BY_DATE, lessonRowMapper, Date.valueOf(lesson.getDate()));
    }

    public List<Lesson> findByTeacherByMonth(Teacher teacher, LocalDate date) {
	return jdbcTemplate.query(SQL_SELECT_BY_TEACHER_BETWEEN_DATES, lessonRowMapper, teacher.getId(), date,
		date.plusMonths(1));
    }

    public List<Lesson> findByTeacherBetweenDates(Teacher teacher, LocalDate start, LocalDate end) {
	return jdbcTemplate.query(SQL_SELECT_BY_TEACHER_BETWEEN_DATES, lessonRowMapper, teacher.getId(), start, end);
    }

    public List<Lesson> findByTeacherByDate(Teacher teacher, LocalDate date) {
	return jdbcTemplate.query(SQL_SELECT_BY_TEACHER_BY_DATE, lessonRowMapper, teacher.getId(), date);
    }

    public List<Lesson> findByStudentByMonth(Student student, LocalDate date) {
	return jdbcTemplate.query(SQL_SELECT_BY_STUDENT_BETWEEN_DATES, lessonRowMapper, student.getId(), date,
		date.plusMonths(1));
    }

    public List<Lesson> findByStudentBetweenDates(Student student, LocalDate start, LocalDate end) {
	return jdbcTemplate.query(SQL_SELECT_BY_STUDENT_BETWEEN_DATES, lessonRowMapper, student.getId(), start, end);
    }

    public List<Lesson> findByStudentByDate(Student student, LocalDate date) {
	return jdbcTemplate.query(SQL_SELECT_BY_STUDENT_BY_DATE, lessonRowMapper, student.getId(), date);
    }

    public Lesson findByDateByTeacherByLessonTime(Lesson lesson) {
	try {
	    return jdbcTemplate.queryForObject(SQL_SELECT_BY_DATE_BY_TEACHER_BY_LESSON_TIME, lessonRowMapper,
		    lesson.getDate(), lesson.getTeacher().getId(), lesson.getLessonTime().getId());
	} catch (EmptyResultDataAccessException e) {
	    return null;
	}
    }

    public Lesson findByDateByAudienceByLessonTime(Lesson lesson) {
	try {
	    return jdbcTemplate.queryForObject(SQL_SELECT_BY_DATE_BY_AUDIENCE_BY_LESSON_TIME, lessonRowMapper,
		    lesson.getDate(), lesson.getAudience().getId(), lesson.getLessonTime().getId());
	} catch (EmptyResultDataAccessException e) {
	    return null;
	}
    }

}
