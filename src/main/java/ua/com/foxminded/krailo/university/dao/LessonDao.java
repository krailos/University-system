package ua.com.foxminded.krailo.university.dao;

import static java.lang.String.format;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.dao.mapper.LessonRowMapper;
import ua.com.foxminded.krailo.university.exception.DaoConstraintViolationException;
import ua.com.foxminded.krailo.university.exception.DaoException;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.model.Teacher;

@Component
public class LessonDao {

    private static final Logger log = LoggerFactory.getLogger(LessonDao.class);

    private static final String SQL_SELECT_ALL = "SELECT * FROM lessons ORDER BY id";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM lessons WHERE id = ?";
    private static final String SQL_UPDATE_BY_ID = "UPDATE lessons SET date = ?, lesson_time_id = ?, subject_id = ?, teacher_id = ?, audience_id = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM lessons WHERE id = ?";
    private static final String SQL_INSERT_INTO_LESSONS = "INSERT INTO lessons (date, lesson_time_id, subject_id, teacher_id, audience_id) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_INSERT_INTO_LESSONS_GROUPS = "INSERT INTO lessons_groups (lesson_id, group_id) VALUES (?, ?)";
    private static final String SQL_DELETE_LESSONS_GROUPS_BY_LESSON_ID_GROUP_ID = "DELETE FROM lessons_groups WHERE lesson_id = ? AND group_id = ?";
    private static final String SQL_SELECT_BY_DATE = "SELECT * FROM lessons WHERE date = ?";
    private static final String SQL_SELECT_BY_TEACHER_BETWEEN_DATES = "SELECT * FROM lessons WHERE teacher_id = ? AND date BETWEEN ? AND ?";
    private static final String SQL_SELECT_BY_TEACHER_AND_DATE = "SELECT * FROM lessons WHERE teacher_id = ? AND date = ?";
    private static final String SQL_SELECT_BY_STUDENT_BETWEEN_DATES = "SELECT lessons.id, date, lesson_time_id, subject_id, teacher_id, audience_id, lesson_id, lessons_groups.group_id,"
	    + "students.id  FROM lessons JOIN lessons_groups ON (lessons.id = lessons_groups.lesson_id) JOIN students ON (lessons_groups.group_id = students.group_id)"
	    + " WHERE students.id = ? AND date BETWEEN ? AND ?";
    private static final String SQL_SELECT_BY_STUDENT_AND_DATE = "SELECT lessons.id, date, lesson_time_id, subject_id, teacher_id, audience_id, lesson_id, lessons_groups.group_id,"
	    + "students.id  FROM lessons JOIN lessons_groups ON (lessons.id = lessons_groups.lesson_id) JOIN students ON (lessons_groups.group_id = students.group_id)"
	    + " WHERE students.id = ? AND date = ?";
    private static final String SQL_SELECT_BY_DATE_AND_TEACHER_AND_LESSON_TIME = "SELECT * FROM lessons WHERE date = ? AND teacher_id = ? AND lesson_time_id = ?";
    private static final String SQL_SELECT_BY_DATE_AND_AUDIENCE_AND_LESSON_TIME = "SELECT * FROM lessons WHERE date = ? AND audience_id = ? AND lesson_time_id = ?";
    private static final String SQL_SELECT_BY_DATE_AND_LESSON_TIME_ID_AND_GROUP_ID = "SELECT * FROM lessons JOIN lessons_groups ON (lessons.id = lessons_groups.lesson_id) WHERE date = ? AND lesson_time_id = ? AND group_id = ?";
    private static final String SQL_LESSONS_COUNT = "SELECT COUNT (*) AS count FROM lessons";
    private static final String SQL_SELECT_WITH_LIMIT = "SELECT * FROM lessons ORDER BY date LIMIT ? OFFSET ?";

    private JdbcTemplate jdbcTemplate;
    private LessonRowMapper lessonRowMapper;

    public LessonDao(JdbcTemplate jdbcTemplate, LessonRowMapper lessonRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.lessonRowMapper = lessonRowMapper;
    }

    public Optional<Lesson> findById(int id) {
	log.debug("Find Lesson by id={}", id);
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, lessonRowMapper, id));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("Lesson with id={} not found", id);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find Lesson by id=" + id, e);
	}

    }

    public List<Lesson> findAll() {
	log.debug("Find all Lesson");
	try {
	    return jdbcTemplate.query(SQL_SELECT_ALL, lessonRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find all Lesson", e);
	}
    }

    public void create(Lesson lesson) {
	log.debug("Create lesson={}", lesson);
	try {
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
		log.debug("insert lesson and group into lessons_groups lessonId={}, groupId={}", lesson.getId(),
			group.getId());
		jdbcTemplate.update(SQL_INSERT_INTO_LESSONS_GROUPS, lesson.getId(), group.getId());
	    }
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException("Not created lesson=" + lesson, e);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to create lesson=" + lesson, e);
	}
	log.info("Created lesson={}", lesson);
    }

    public void update(Lesson lesson) {
	log.debug("Update lesson={}", lesson);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_UPDATE_BY_ID, lesson.getDate(), lesson.getLessonTime().getId(),
		    lesson.getSubject().getId(), lesson.getTeacher().getId(), lesson.getAudience().getId(),
		    lesson.getId());
	    List<Group> groupsOld = findById(lesson.getId()).get().getGroups();
	    groupsOld.stream().filter(g -> !lesson.getGroups().contains(g)).forEach(g -> {
		log.debug("delete lesson and group from lessons_groups where lessonId={}, groupId={}", lesson.getId(),
			g.getId());
		jdbcTemplate.update(SQL_DELETE_LESSONS_GROUPS_BY_LESSON_ID_GROUP_ID, lesson.getId(), g.getId());
	    });
	    lesson.getGroups().stream().filter(g -> !groupsOld.contains(g)).forEach(g -> {
		log.debug("insert lesson and group into lessons_groups lessonId={}, groupId={}", lesson.getId(),
			g.getId());
		jdbcTemplate.update(SQL_INSERT_INTO_LESSONS_GROUPS, lesson.getId(), g.getId());
	    });
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException("Not created, lesson=" + lesson);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to update lesson=" + lesson, e);
	}
	if (rowsAffected > 0) {
	    log.info("Updated lesson={}", lesson);
	} else {
	    log.debug("Not updated lesson={}", lesson);
	}
    }

    public void deleteById(int id) {
	log.debug("Delete lesson by id={}", id);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_DELETE_BY_ID, id);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to delete lesson by id=" + id, e);
	}
	if (rowsAffected > 0) {
	    log.info("Deleted lesson  by id={}", id);
	} else {
	    log.debug("Not deleted lesson by id={}", id);
	}
    }

    public List<Lesson> findByDate(LocalDate date) {
	log.debug("Find lessons by date={}", date);
	try {
	    return jdbcTemplate.query(SQL_SELECT_BY_DATE, lessonRowMapper, date);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find lessons by date=" + date, e);
	}
    }

    public List<Lesson> findByTeacherBetweenDates(Teacher teacher, LocalDate start, LocalDate end) {
	log.debug("Find lessons by teacher={} and startDate={} and endDate={}", teacher, start, end);
	try {
	    return jdbcTemplate.query(SQL_SELECT_BY_TEACHER_BETWEEN_DATES, lessonRowMapper, teacher.getId(), start,
		    end);
	} catch (DataAccessException e) {
	    throw new DaoException(
		    format("Unable to find lessons by teacher=%s and startDate=%s and endDate=%s", teacher, start, end),
		    e);
	}
    }

    public List<Lesson> findByTeacherAndDate(Teacher teacher, LocalDate date) {
	log.debug("Find lessons by teacher={} and date={}", teacher, date);
	try {
	    return jdbcTemplate.query(SQL_SELECT_BY_TEACHER_AND_DATE, lessonRowMapper, teacher.getId(), date);
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to find lessons by teacher=%s and date=%s", teacher, date), e);
	}
    }

    public List<Lesson> findByStudentBetweenDates(Student student, LocalDate start, LocalDate end) {
	log.debug("Find lessons by student={} between startDate={} and endDate={}", student, start, end);
	try {
	    return jdbcTemplate.query(SQL_SELECT_BY_STUDENT_BETWEEN_DATES, lessonRowMapper, student.getId(), start,
		    end);
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to find lessons by student=%s between startDate=%s and endDate=%s",
		    student, start, end), e);
	}
    }

    public List<Lesson> findByStudentAndDate(Student student, LocalDate date) {
	log.debug("Find lessons by student={} and endDate={}", student, date);
	try {
	    return jdbcTemplate.query(SQL_SELECT_BY_STUDENT_AND_DATE, lessonRowMapper, student.getId(), date);
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to find lessons by student=%s and endDate=%s", student, date), e);
	}
    }

    public Optional<Lesson> findByDateAndTeacherIdAndLessonTimeId(LocalDate date, int teacherId, int lessonTimeId) {
	log.debug("Find Lesson by date={} and teacher={} and lessonTime id={}", date, teacherId, lessonTimeId);
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_DATE_AND_TEACHER_AND_LESSON_TIME,
		    lessonRowMapper, date, teacherId, lessonTimeId));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("Lesson not found by date={} and teacherId={} and lessonTimeid={}", date, teacherId,
		    lessonTimeId);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to find Lesson by date=%s and teacher=%s and lessonTime id=%s", date,
		    teacherId, lessonTimeId), e);
	}
    }

    public Optional<Lesson> findByDateAndAudienceIdAndLessonTimeId(LocalDate date, int audienceId, int lessonTimeId) {
	log.debug("Find Lesson by date={} and audience={} and lessonTime id={}", date, audienceId, lessonTimeId);
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_DATE_AND_AUDIENCE_AND_LESSON_TIME,
		    lessonRowMapper, date, audienceId, lessonTimeId));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("Lesson not found  by date={} and audienceId={} and lessonTimeid={}", date, audienceId,
		    lessonTimeId);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to find Lesson by date=%s and audience=%s and lessonTime id=%s", date,
		    audienceId, lessonTimeId), e);
	}
    }

    public Optional<Lesson> findByDateAndLessonTimeIdAndGroupId(LocalDate date, int lessonTimeId, int groupId) {
	log.debug("Find Lesson by date={} and lessonTimeId={}  and groupId={} ", date, lessonTimeId, groupId);
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_DATE_AND_LESSON_TIME_ID_AND_GROUP_ID,
		    lessonRowMapper, date, lessonTimeId, groupId));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("Lesson not found  by date={} and lessonTimeId={}  and groupId={}", date, lessonTimeId, groupId);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to find Lesson by date=%s and lessonTimeId=%s  and groupId=%s", date,
		    lessonTimeId, groupId), e);
	}
    }

    public int findQuantity() {
	log.debug("find lessons count");
	try {
	    return jdbcTemplate.queryForObject(SQL_LESSONS_COUNT, Integer.class);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find lessons count", e);
	}
    }

    public List<Lesson> findWithLimit(int limit, int offset) {
	log.debug("find lessons by limit");
	try {
	    return jdbcTemplate.query(SQL_SELECT_WITH_LIMIT, lessonRowMapper, limit, offset);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find lessons by limit", e);
	}
    }

}
