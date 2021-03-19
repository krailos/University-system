package ua.com.foxminded.krailo.university.dao;

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
import ua.com.foxminded.krailo.university.model.LessonsTimeSchedule;

@Repository
public class LessonTimeScheduleDao {

    private static final Logger log = LoggerFactory.getLogger(LessonTimeScheduleDao.class);
    
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM lessons_timeschedule WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM lessons_timeschedule  ";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM lessons_timeschedule   WHERE id = ?";
    private static final String SQL_INSERT_LESSONS_TIME_SCHEDULE = "INSERT INTO lessons_timeschedule (name) VALUES (?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE lessons_timeschedule  SET name = ? where id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<LessonsTimeSchedule> lessonsTimeScheduleRowMapper;

    public LessonTimeScheduleDao(JdbcTemplate jdbcTemplate, 
	    RowMapper<LessonsTimeSchedule> lessonsTimescheduleRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.lessonsTimeScheduleRowMapper = lessonsTimescheduleRowMapper;
    }

    public void create(LessonsTimeSchedule lessonsTimeSchedule) {
	log.debug("Create LessonsTimeSchedule={}", lessonsTimeSchedule);
	try {
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    jdbcTemplate.update(connection -> {
		PreparedStatement ps = connection.prepareStatement(SQL_INSERT_LESSONS_TIME_SCHEDULE,
			new String[] { "id" });
		ps.setString(1, lessonsTimeSchedule.getName());
		return ps;
	    }, keyHolder);
	    lessonsTimeSchedule.setId(keyHolder.getKey().intValue());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(
		    "Not created LessonsTimeSchedule=" + lessonsTimeSchedule);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to create LessonsTimeSchedule=" + lessonsTimeSchedule, e);
	}
	log.info("Created LessonsTimeSchedule={}", lessonsTimeSchedule);
    }

    public void update(LessonsTimeSchedule lessonsTimeSchedule) {
	log.debug("Update lessonsTimeSchedule={}", lessonsTimeSchedule);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_UPDATE_BY_ID, lessonsTimeSchedule.getName(),
		    lessonsTimeSchedule.getId());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(
		    "Not created, lessonsTimeSchedule=" + lessonsTimeSchedule, e);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to update lessonsTimeSchedule=" + lessonsTimeSchedule, e);
	}
	if (rowsAffected > 0) {
	    log.info("Updated lessonsTimeSchedule={}", lessonsTimeSchedule);
	} else {
	    log.debug("Not updated departmlessonsTimeScheduleent={}", lessonsTimeSchedule);
	}
    }

    public Optional<LessonsTimeSchedule> findById(int id) {
	log.debug("Find lessonsTimeSchedule by id={}", id);
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, lessonsTimeScheduleRowMapper, id));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("lessonsTimeSchedule with id={} not found", id);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find lessonsTimeSchedule by id=" + id, e);
	}
    }

    public List<LessonsTimeSchedule> findAll() {
	log.debug("Find all lessonsTimeSchedules");
	try {
	    return jdbcTemplate.query(SQL_SELECT_ALL, lessonsTimeScheduleRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find all lessonsTimeSchedules", e);
	}
    }

    public void deleteById(int id) {
	log.debug("Delete lessonsTimeSchedule by id={}", id);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_DELETE_BY_ID, id);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to delete lessonsTimeSchedule by id=" + id, e);
	}
	if (rowsAffected > 0) {
	    log.info("Deleted lessonsTimeSchedule  by id={}", id);
	} else {
	    log.debug("Not deleted lessonsTimeSchedule by id={}", id);
	}

    }

}
