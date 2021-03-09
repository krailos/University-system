package ua.com.foxminded.krailo.university.dao;

import static java.lang.String.format;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.exception.DaoConstraintViolationException;
import ua.com.foxminded.krailo.university.exception.DaoException;
import ua.com.foxminded.krailo.university.model.LessonTime;

@Repository
public class LessonTimeDao {

    private static final Logger log = LoggerFactory.getLogger(LessonTimeDao.class);
    private static final String SQL_SELECT = "SELECT * FROM lesson_times WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM lesson_times ";
    private static final String SQL_SELECT_BY_LESSON_TIME_SCHEDULE_ID = "SELECT * FROM lesson_times WHERE lessons_timeschedule_id = ? ";
    private static final String SQL_DELETE = "DELETE FROM lesson_times WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO lesson_times (order_number, start_time, end_time, lessons_timeschedule_id) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE lesson_times SET order_number = ?, start_time = ?, end_time = ?, lessons_timeschedule_id = ? where id = ?";
    private static final String SQL_SELECT_BY_START_OR_END_TIME = "SELECT * FROM lesson_times where ? between start_time and end_time or ? between start_time and end_time";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<LessonTime> lessonTimeRowMapper;

    public LessonTimeDao(JdbcTemplate jdbcTemplate, RowMapper<LessonTime> lessonTimeRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.lessonTimeRowMapper = lessonTimeRowMapper;
    }

    public void create(LessonTime lessonTime) {
	log.debug("Create lessonTime={}", lessonTime);
	try {
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    jdbcTemplate.update(connection -> {
		PreparedStatement ps = connection.prepareStatement(SQL_INSERT, new String[] { "id" });
		ps.setString(1, lessonTime.getOrderNumber());
		ps.setObject(2, lessonTime.getStartTime());
		ps.setObject(3, lessonTime.getEndTime());
		ps.setInt(4, lessonTime.getLessonsTimeSchedule().getId());
		return ps;
	    }, keyHolder);
	    lessonTime.setId(keyHolder.getKey().intValue());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(format("Not created lessonTime=%", lessonTime));
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to create lessonTime=%s", lessonTime), e);
	}
	log.info("Created lessonTime={}", lessonTime);
    }

    public void update(LessonTime lessonTime) {
	log.debug("Update lessonTime={}", lessonTime);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_UPDATE, lessonTime.getOrderNumber(), lessonTime.getStartTime(),
		    lessonTime.getEndTime(), lessonTime.getLessonsTimeSchedule().getId(), lessonTime.getId());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(format("Not created, lessonTime=%", lessonTime));
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to update lessonTime=%s", lessonTime));
	}
	if (rowsAffected > 0) {
	    log.info("Updated lessonTime={}", lessonTime);
	} else {
	    log.debug("Not updated lessonTime={}", lessonTime);
	}
    }

    public Optional<LessonTime> findById(int id) {
	log.debug("Find lessonTime by id={}", id);
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT, lessonTimeRowMapper, id));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("lessonTime with id={} not found", id);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to find lessonTime by id=%s", id), e);
	}
    }

    public List<LessonTime> findAll() {
	log.debug("Find all LessonTime");
	try {
	    return jdbcTemplate.query(SQL_SELECT_ALL, lessonTimeRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find all LessonTime", e);
	}
    }

    public List<LessonTime> findBylessonTimeScheduleId(int scheduleId) {
	log.debug("Find by LessonTime Schedule");
	try {
	    return jdbcTemplate.query(SQL_SELECT_BY_LESSON_TIME_SCHEDULE_ID, lessonTimeRowMapper, scheduleId);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find by LessonTime Schedule", e);
	}
    }

    public void deleteById(int id) {
	log.debug("Delete LessonTime by id={}", id);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_DELETE, id);
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to delete LessonTime by id=%s", id), e);
	}
	if (rowsAffected > 0) {
	    log.info("Deleted LessonTime  by id={}", id);
	} else {
	    log.debug("Not deleted LessonTime by id={}", id);
	}
    }

    public Optional<LessonTime> findByStartOrEndLessonTime(LessonTime lessonTime) {
	log.debug("Find lessonTime by Start={} or End={} lessonTime", lessonTime.getStartTime(),
		lessonTime.getEndTime());
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_START_OR_END_TIME, lessonTimeRowMapper,
		    lessonTime.getStartTime(), lessonTime.getEndTime()));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("lessonTime by Start={} or End={} lessonTime not found", lessonTime.getStartTime(),
		    lessonTime.getEndTime());
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to find lessonTime lessonTime by Start=%s or End=%s lessonTime",
		    lessonTime.getStartTime(), lessonTime.getEndTime()), e);
	}
    }

}
