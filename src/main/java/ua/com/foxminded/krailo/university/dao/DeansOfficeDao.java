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
import ua.com.foxminded.krailo.university.model.DeansOffice;

@Repository
public class DeansOfficeDao {

    private static final Logger log = LoggerFactory.getLogger(DeansOfficeDao.class);
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM deans_office   WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM deans_office ";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM deans_office  WHERE id = ?";
    private static final String SQL_INSERT_DEANS_OFFICE = "INSERT INTO deans_office  (name, university_office_id) VALUES (?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE deans_office SET name = ?, university_office_id = ? where id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<DeansOffice> deansOfficeRowMapper;

    public DeansOfficeDao(JdbcTemplate jdbcTemplate, RowMapper<DeansOffice> deansOfficeRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.deansOfficeRowMapper = deansOfficeRowMapper; 
    }

    public void create(DeansOffice deansOffice) {
	log.debug("Create deansOffice={}", deansOffice);
	try {
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    jdbcTemplate.update(connection -> {
		PreparedStatement ps = connection.prepareStatement(SQL_INSERT_DEANS_OFFICE, new String[] { "id" });
		ps.setString(1, deansOffice.getName());
		ps.setInt(2, deansOffice.getUniversityOffice().getId());
		return ps;
	    }, keyHolder);
	    deansOffice.setId(keyHolder.getKey().intValue());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(format("DeansOffice not created, deanseOffice=%s", deansOffice),
		    e);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to create deansOffice=" + deansOffice, e);
	}
	log.info("Created deansOffice={}", deansOffice);
    }

    public void update(DeansOffice deansOffice) {
	log.debug("Updating deanseOffice={}", deansOffice);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_UPDATE_BY_ID, deansOffice.getName(),
		    deansOffice.getUniversityOffice().getId(), deansOffice.getId());
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to update deansOffice=" + deansOffice, e);
	}
	if (rowsAffected > 0) {
	    log.info("Updated deansOffice={}", deansOffice);
	} else {
	    log.debug("Not updated DeansOffice={}", deansOffice);
	}
    }

    public Optional<DeansOffice> findById(int id) {
	log.debug("Find deansOffice by id={}", id);
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, deansOfficeRowMapper, id));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("DeansOffice with id={} not found", id);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find deansOffice by id=" + id, e);
	}
    }

    public List<DeansOffice> findAll() {
	log.debug("Find all deansOffices");
	try {
	    return jdbcTemplate.query(SQL_SELECT_ALL, deansOfficeRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find all deansOffices", e);
	}
    }

    public void deleteById(int id) {
	log.debug("Delete deanseOffice by id={}", id);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_DELETE_BY_ID, id);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to delete deansOffice by id=" + id, e);
	}
	if (rowsAffected > 0) {
	    log.info("Deleted deansOffice  by id={}", id);
	} else {
	    log.debug("Not deleted deansOffice by id={}", id);
	}
    }

}
