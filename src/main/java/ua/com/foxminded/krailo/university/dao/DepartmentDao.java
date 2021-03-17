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
import ua.com.foxminded.krailo.university.model.Department;

@Repository
public class DepartmentDao {

    private static final Logger log = LoggerFactory.getLogger(DepartmentDao.class);
    
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM departments    WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM departments  ";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM departments   WHERE id = ?";
    private static final String SQL_INSERT_DEPARTMENT = "INSERT INTO departments (name) VALUES (?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE departments  SET name = ? where id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Department> departmentRowMapper;

    public DepartmentDao(JdbcTemplate jdbcTemplate, RowMapper<Department> departmentRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.departmentRowMapper = departmentRowMapper;
    }

    public void create(Department department) {
	log.debug("Create department={}", department);
	try { 
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    jdbcTemplate.update(connection -> {
		PreparedStatement ps = connection.prepareStatement(SQL_INSERT_DEPARTMENT, new String[] { "id" });
		ps.setString(1, department.getName());
		return ps;
	    }, keyHolder);
	    department.setId(keyHolder.getKey().intValue());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(format("Not created department=%s", department));
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to create department=" + department, e);
	}
	log.info("Created department={}", department);
    }

    public void update(Department department) {
	log.debug("Update department={}", department);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_UPDATE_BY_ID, department.getName(), department.getId());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(format("Not created, department=%s", department));
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to update department=" + department, e);
	}
	if (rowsAffected > 0) {
	    log.info("Updated department={}", department);
	} else {
	    log.debug("Not updated department={}", department);
	}
    }

    public Optional<Department> findById(int id) {
	log.debug("Find department by id={}", id);
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, departmentRowMapper, id));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("Department with id={} not found", id);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find department by id=" + id, e);
	}
    }

    public List<Department> findAll() {
	log.debug("Find all department");
	try {
	    return jdbcTemplate.query(SQL_SELECT_ALL, departmentRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find all departnents", e);
	}
    }

    public void deleteById(int id) {
	log.debug("Delete department by id={}", id);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_DELETE_BY_ID, id);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to delete department by id=" + id, e);
	}
	if (rowsAffected > 0) {
	    log.info("Deleted department  by id={}", id);
	} else {
	    log.debug("Not deleted department by id={}", id);
	}
    }

}
