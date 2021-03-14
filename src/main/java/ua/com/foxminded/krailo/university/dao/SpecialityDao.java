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
import ua.com.foxminded.krailo.university.model.Speciality;

@Repository
public class SpecialityDao {

    private static final Logger log = LoggerFactory.getLogger(SpecialityDao.class);
    
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM specialities WHERE id = ?";
    private static final String SQL_SELECT_BY_NAME_AND_FACULTY_ID = "SELECT * FROM specialities WHERE name = ? AND id = ?";
    private static final String SQL_SELECT_BY_FACULTY_ID = "SELECT * FROM specialities WHERE faculty_id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM specialities ";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM specialities WHERE id = ?";
    private static final String SQL_INSERT_SPECIALITY = "INSERT INTO specialities (name, faculty_id) VALUES (?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE specialities SET name = ?, faculty_id = ? where id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Speciality> specialityRowMapper;

    public SpecialityDao(JdbcTemplate jdbcTemplate, RowMapper<Speciality> specialityRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.specialityRowMapper = specialityRowMapper;
    } 

    public void create(Speciality speciality) {
	log.debug("Create speciality={}", speciality);
	try {
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    jdbcTemplate.update(connection -> {
		PreparedStatement ps = connection.prepareStatement(SQL_INSERT_SPECIALITY, new String[] { "id" });
		ps.setString(1, speciality.getName());
		ps.setInt(2, speciality.getFaculty().getId());
		return ps;
	    }, keyHolder);
	    speciality.setId(keyHolder.getKey().intValue());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException("Not created speciality=" + speciality, e);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to create speciality=" + speciality, e);
	}
	log.info("Created speciality={}", speciality);
    }

    public void update(Speciality speciality) {
	log.debug("Update speciality={}", speciality);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_UPDATE_BY_ID, speciality.getName(), speciality.getFaculty().getId(),
		    speciality.getId());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(format("Not created, speciality=%s", speciality));
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to update speciality=" + speciality, e);
	}
	if (rowsAffected > 0) {
	    log.info("Updated speciality={}", speciality);
	} else {
	    log.debug("Not updated speciality={}", speciality);
	}
    }

    public Optional<Speciality> findById(int id) {
	log.debug("Find speciality by id={}", id);
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, specialityRowMapper, id));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("speciality with id={} not found", id);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find speciality by id=" + id, e);
	}
    }

    public List<Speciality> findAll() {
	log.debug("Find all specialities");
	try {
	    return jdbcTemplate.query(SQL_SELECT_ALL, specialityRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find all specialities", e);
	}
    }

    public void deleteById(int id) {
	log.debug("Delete speciality by id={}", id);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_DELETE_BY_ID, id);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to delete speciality by id=" + id, e);
	}
	if (rowsAffected > 0) {
	    log.info("Deleted speciality  by id={}", id);
	} else {
	    log.debug("Not deleted speciality by id={}", id);
	}
    }

    public List<Speciality> findByFacultyId(int facultyId) {
	log.debug("Find specialities by facultyId={}", facultyId);
	try {
	    return jdbcTemplate.query(SQL_SELECT_BY_FACULTY_ID, specialityRowMapper, facultyId);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find specialities by facultyId=" + facultyId, e);
	}
    }

    public Optional<Speciality> findByNameAndFacultyId(String name, int facultyId) {
	log.debug("Find speciality by name={} and facultyId={}", name, facultyId);
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_NAME_AND_FACULTY_ID, specialityRowMapper, name,
		    facultyId));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("speciality by name={} and facultyId={}", name, facultyId);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to find speciality by name=%s and facultyId=%s", name, facultyId), e);
	}
    }

}
