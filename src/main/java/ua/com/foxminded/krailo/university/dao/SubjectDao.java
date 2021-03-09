package ua.com.foxminded.krailo.university.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

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
import ua.com.foxminded.krailo.university.model.Subject;

@Repository
public class SubjectDao {

    private static final Logger log = LoggerFactory.getLogger(DepartmentDao.class);
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM subjects WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM subjects";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM subjects WHERE id = ?";
    private static final String SQL_INSERT_DEPARTMENT = "INSERT INTO subjects (name) VALUES (?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE subjects  SET name = ? where id = ?";
    private static final String SQL_SELECT_SUBJECTS_BY_TEACHER_ID = "SELECT id, name FROM subjects JOIN teachers_subjects  ON (teachers_subjects.subject_id = subjects.id) WHERE teachers_subjects.teacher_id = ?";
    private static final String SQL_SELECT_SUBJECTS_BY_YEAR_ID = "SELECT id, name FROM subjects JOIN years_subjects ON (years_subjects.subject_id = subjects.id) WHERE years_subjects.year_id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Subject> subjectRowMapper;

    public SubjectDao(JdbcTemplate jdbcTemplate, RowMapper<Subject> subjectRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.subjectRowMapper = subjectRowMapper;
    }

    public void create(Subject subject) {
	log.debug("Create subject={}", subject);
	try {
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    jdbcTemplate.update(connection -> {
		PreparedStatement ps = connection.prepareStatement(SQL_INSERT_DEPARTMENT, new String[] { "id" });
		ps.setString(1, subject.getName());
		return ps;
	    }, keyHolder);
	    subject.setId(keyHolder.getKey().intValue());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(format("Not created subject=%s", subject));
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to create subject=%s", subject), e);
	}
	log.info("Created subject={}", subject);
    }

    public void update(Subject subject) {
	log.debug("Update subject={}", subject);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_UPDATE_BY_ID, subject.getName(), subject.getId());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(format("Not updated, subject=%s", subject));
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to update subject=%s", subject));
	}
	if (rowsAffected > 0) {
	    log.info("Updated subject={}", subject);
	} else {
	    log.debug("Not updated subject={}", subject);
	}
    }

    public Optional<Subject> findById(int id) {
	log.debug("Find subject by id={}", id);
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, subjectRowMapper, id));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("subject with id={} not found", id);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to find subject by id=%s", id), e);
	}
    }

    public List<Subject> findAll() {
	log.debug("Find all subjects");
	try {
	    return jdbcTemplate.query(SQL_SELECT_ALL, subjectRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find all subjects", e);
	}
    }

    public void deleteById(int id) {
	log.debug("Delete subject by id={}", id);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_DELETE_BY_ID, id);
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to delete subject by id=%s", id), e);
	}
	if (rowsAffected > 0) {
	    log.info("Deleted subject  by id={}", id);
	} else {
	    log.debug("Not deleted subject by id={}", id);
	}
    }

    public List<Subject> findByTeacherId(int teacherId) {
	log.debug("Find subjects by teacherId={}", teacherId);
	try {
	    return jdbcTemplate.query(SQL_SELECT_SUBJECTS_BY_TEACHER_ID, new Object[] { teacherId }, subjectRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to find subjects by teacherId=%s", teacherId), e);
	}
    }

    public List<Subject> findByYearId(int yearId) {
	log.debug("Find subjects by yearId={}", yearId);
	try {
	    return jdbcTemplate.query(SQL_SELECT_SUBJECTS_BY_YEAR_ID, new Object[] { yearId }, subjectRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to find subjects by yearId=%s", yearId), e);
	}
    }

}
