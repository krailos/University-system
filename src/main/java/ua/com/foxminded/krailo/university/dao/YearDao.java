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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.exception.DaoConstraintViolationException;
import ua.com.foxminded.krailo.university.exception.DaoException;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Year;

@Repository
public class YearDao {

    private static final Logger log = LoggerFactory.getLogger(YearDao.class);
    
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM years WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM years ";
    private static final String SQL_SELECT_BY_SPECIALITY_ID = "SELECT * FROM years WHERE speciality_id = ? ";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM years WHERE id = ?";
    private static final String SQL_INSERT_YEAR = "INSERT INTO years (name, speciality_id) VALUES (?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE years SET name = ?, speciality_id = ? where id = ?";
    private static final String SQL_INSERT_INTO_YEARS_SUBJECTS = "INSERT INTO years_subjects (year_id, subject_id) VALUES (?, ?)";
    private static final String SQL_DELETE_YEARS_SUBJECTS = "DELETE FROM years_subjects WHERE year_id = ? AND subject_id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Year> yearRowMapper;

    public YearDao(JdbcTemplate jdbcTemplate, RowMapper<Year> yearRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.yearRowMapper = yearRowMapper;
    }
    
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void create(Year year) {
	log.debug("Create year={}", year);
	try {
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    jdbcTemplate.update(connection -> {
		PreparedStatement ps = connection.prepareStatement(SQL_INSERT_YEAR, new String[] { "id" });
		ps.setString(1, year.getName());
		ps.setInt(2, year.getSpeciality().getId());
		return ps;
	    }, keyHolder);
	    year.setId(keyHolder.getKey().intValue());
	    for (Subject subject : year.getSubjects()) {
		addSubjectToYear(subject, year);
	    }
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(format("Not created year=%s", year));
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to create year=%s", year), e);
	}
	log.info("Created year={}", year);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(Year year) {
	log.debug("existing year={}", findById(year.getId()).get());
	log.debug("Update year={}", year);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_UPDATE_BY_ID, year.getName(), year.getSpeciality().getId(),
		    year.getId());
	    log.debug("year={} was updated", year);
	    log.debug("update years_subject table");
	    List<Subject> subjectsOld = findById(year.getId()).get().getSubjects();
	    subjectsOld.stream().filter(s -> !year.getSubjects().contains(s))
		    .forEach(s -> { 
			log.debug("delelete rows from year_subjects which will be apdated");
		    jdbcTemplate.update(SQL_DELETE_YEARS_SUBJECTS, year.getId(), s.getId());
			log.debug("rows deleleted from years_subjects");
		    });
	    year.getSubjects().stream().filter(s -> !subjectsOld.contains(s))
		    .forEach(s -> {
			log.debug("insert new rows into  years_subjects");
			jdbcTemplate.update(SQL_INSERT_INTO_YEARS_SUBJECTS, year.getId(), s.getId());
			log.debug("inserted new rows into years_subjects");
		    });
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(format("Not updated, year=%s", year));
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to update year=%s", year), e);
	}
	if (rowsAffected > 0) {
	    log.info("Updated year={}", year);
	} else {
	    log.debug("Not updated year={}", year);
	}

    }

    public Optional<Year> findById(int id) {
	log.debug("Find year by id={}", id);
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, yearRowMapper, id));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("year with id={} not found", id);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to find year by id=%s", id), e);
	}
    }

    public List<Year> findAll() {
	log.debug("Find all years");
	try {
	    return jdbcTemplate.query(SQL_SELECT_ALL, yearRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find all years", e);
	}
    }

    public List<Year> findBySpecialityId(int specialityId) {
	log.debug("Find year by specialityId={}", specialityId);
	try {
	    return jdbcTemplate.query(SQL_SELECT_BY_SPECIALITY_ID, yearRowMapper, specialityId);
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to find years by specialityId=%s", specialityId), e);
	}
    }

    public void deleteById(int id) {
	log.debug("Delete year by id={}", id);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_DELETE_BY_ID, id);
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to delete year by id=%s", id), e);
	}
	if (rowsAffected > 0) {
	    log.info("Deleted year  by id={}", id);
	} else {
	    log.debug("Not deleted year by id={}", id);
	}
    }

    private void addSubjectToYear(Subject subject, Year year) {
	log.debug("Add subjet={} to year={}", subject, year);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_INSERT_INTO_YEARS_SUBJECTS, year.getId(), subject.getId());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(format("Not add subject=%s to year=%s", subject, year));
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to add subject=%s to year=%s", subject, year));
	}
	if (rowsAffected > 0) {
	    log.info("Added subject={} to year={}", subject, year);
	} else {
	    log.debug("Not added subject={} to year={}", subject, year);
	}
    }

}
