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
import ua.com.foxminded.krailo.university.model.Faculty;

@Repository
public class FacultyDao {

    private static final Logger log = LoggerFactory.getLogger(FacultyDao.class);
    
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM faculties WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM faculties";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM faculties WHERE id = ?";
    private static final String SQL_INSERT_FACULTY = "INSERT INTO faculties (name, deans_office_id) VALUES (?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE faculties SET name = ?, deans_office_id = ? where id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Faculty> facultyRowMapper;

    public FacultyDao(JdbcTemplate jdbcTemplate, RowMapper<Faculty> facultyRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.facultyRowMapper = facultyRowMapper;
    }

    public void create(Faculty faculty) {  
	log.debug("Create faculty={}", faculty);
	try {
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    jdbcTemplate.update(connection -> {
		PreparedStatement ps = connection.prepareStatement(SQL_INSERT_FACULTY, new String[] { "id" });
		ps.setString(1, faculty.getName());
		ps.setInt(2, faculty.getDeansOffice().getId());
		return ps;
	    }, keyHolder);
	    faculty.setId(keyHolder.getKey().intValue());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(format("Not created, faculty=%", faculty));
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to  create faculty=" + faculty, e);
	}
	log.info("Created faculty={}", faculty);
    }

    public void update(Faculty faculty) {
	log.debug("Update faculty={}", faculty);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_UPDATE_BY_ID, faculty.getName(), faculty.getDeansOffice().getId(),
		    faculty.getId());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(format("Not created, faculty=%", faculty));
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to update faculty=" + faculty, e);
	}
	if (rowsAffected > 0) {
	    log.info("Updated faculty={}", faculty);
	} else {
	    log.debug("Not updated faculty={}", faculty);
	}
    }

    public Optional<Faculty> findById(int id) {
	log.debug("Find faculty by id={}", id);
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, facultyRowMapper, id));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("Faculty with id={} not found", id);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find faculty by id=" + id, e);
	}
    }

    public List<Faculty> findAll() {
	log.debug("Find all faculty");
	try {
	    return jdbcTemplate.query(SQL_SELECT_ALL, facultyRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find all faculty", e);
	}
    }

    public void deleteById(int id) {
	log.debug("Delete faculty by id={}", id);
	int rowsAffected = 0;
	try {
	    jdbcTemplate.update(SQL_DELETE_BY_ID, id);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to delete faculty by id=" + id, e);
	}
	if (rowsAffected > 0) {
	    log.info("Deleted faculty by id={}", id);
	} else {
	    log.debug("Not deleted faculty by id={}", id);
	}
    }

}
