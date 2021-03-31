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
import ua.com.foxminded.krailo.university.model.UniversityOffice;

@Repository
public class UniversityOfficeDao {

    private static final Logger log = LoggerFactory.getLogger(UniversityOfficeDao.class);

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM university_office  WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM university_office";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM university_office WHERE id = ?";
    private static final String SQL_INSERT_UNIVERSITY_OFFICE = "INSERT INTO university_office (name, address) VALUES (?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE university_office SET name = ?, address = ? where id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<UniversityOffice> universityOfficeRowMapper;

    public UniversityOfficeDao(JdbcTemplate jdbcTemplate, RowMapper<UniversityOffice> universityOfficeRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.universityOfficeRowMapper = universityOfficeRowMapper;
    }

    public Optional<UniversityOffice> findById(int id) {
	log.debug("Find universityOffice by id={}", id);
	try {
	    return Optional
		    .of(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[] { id }, universityOfficeRowMapper));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("universityOffice with id={} not found", id);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find universityOffice by id=" + id, e);
	}
    }

    public List<UniversityOffice> findAll() {
	log.debug("Find all universityOffices");
	try {
	    return jdbcTemplate.query(SQL_SELECT_ALL, universityOfficeRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find all universityOffices", e);
	}
    }

    public void deleteById(int id) {
	log.debug("Delete universityOffice by id={}", id);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_DELETE_BY_ID, id);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to delete universityOffice by id=" + id, e);
	}
	if (rowsAffected > 0) {
	    log.info("Deleted universityOffice  by id={}", id);
	} else {
	    log.debug("Not deleted universityOffice by id={}", id);
	}
    }

    public void update(UniversityOffice universityOffice) {
	log.debug("Update universityOffice={}", universityOffice);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_UPDATE_BY_ID, universityOffice.getName(),
		    universityOffice.getAddress(), universityOffice.getId());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException("Not updated, universityOffice=" + universityOffice, e);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to update universityOffice=" + universityOffice, e);
	}
	if (rowsAffected > 0) {
	    log.info("Updated universityOffice={}", universityOffice);
	} else {
	    log.debug("Not updated universityOffice={}", universityOffice);
	}
    }

    public void create(UniversityOffice universityOffice) {
	log.debug("Create universityOffice={}", universityOffice);
	try {
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    jdbcTemplate.update(connection -> {
		PreparedStatement ps = connection.prepareStatement(SQL_INSERT_UNIVERSITY_OFFICE, new String[] { "id" });
		ps.setString(1, universityOffice.getName());
		ps.setString(2, universityOffice.getAddress());
		return ps;
	    }, keyHolder);
	    universityOffice.setId(keyHolder.getKey().intValue());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException("Not created universityOffice=" + universityOffice, e);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to create universityOffice=" + universityOffice, e);
	}
	log.info("Created universityOffice={}", universityOffice);
    }

}