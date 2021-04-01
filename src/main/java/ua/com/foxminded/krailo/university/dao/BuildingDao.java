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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.dao.mapper.BuildingRowMapper;
import ua.com.foxminded.krailo.university.exception.DaoConstraintViolationException;
import ua.com.foxminded.krailo.university.exception.DaoException;
import ua.com.foxminded.krailo.university.model.Building;

import static java.lang.String.format;

@Repository
public class BuildingDao {

    private static final Logger log = LoggerFactory.getLogger(BuildingDao.class);
    
    private static final String SQL_SELECT_BUILDINGS = "SELECT * FROM buildings ORDER BY id";
    private static final String SQL_SELECT_BUILDING_BY_ID = "SELECT * FROM buildings WHERE id = ?";
    private static final String SQL_DELETE_BUILDING_BY_ID = "DELETE FROM buildings WHERE id = ?";
    private static final String SQL_INSERT_BUILDING = "INSERT INTO buildings (name, address) VALUES (?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE buildings SET name = ?, address = ? WHERE id = ? ";

    private JdbcTemplate jdbcTemplate;
    private BuildingRowMapper buildingRowMapper;

    public BuildingDao(JdbcTemplate jdbcTemplate, BuildingRowMapper buildingRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.buildingRowMapper = buildingRowMapper;
    } 

    public List<Building> findAll() {
	log.debug("find all buildings");
	try {
	    return jdbcTemplate.query(SQL_SELECT_BUILDINGS, buildingRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException("unable to find all buildings", e);
	}
    }

    public Optional<Building> findById(int id) {
	log.debug("find building by id={}", id);
	try {
	    return Optional
		    .of(jdbcTemplate.queryForObject(SQL_SELECT_BUILDING_BY_ID, new Object[] { id }, buildingRowMapper));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("Building with id={} not found", id);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException("unable to find buildings by id=" + id, e);
	}
    }

    public void create(Building building) {
	log.debug("create new building={}", building);
	try {
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    jdbcTemplate.update(connection -> {
		PreparedStatement ps = connection.prepareStatement(SQL_INSERT_BUILDING, new String[] { "id" });
		ps.setString(1, building.getName());
		ps.setString(2, building.getAddress());
		return ps;
	    }, keyHolder);
	    building.setId(keyHolder.getKey().intValue());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException("Building not created", e);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to create building=" + building, e);
	}
	log.info("building created={}", building);
    }

    public void update(Building building) {
	log.debug("Updating building={}", building);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_UPDATE_BY_ID, building.getName(), building.getAddress(),
		    building.getId());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(format("Building not updated building=%s", building), e);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to update building=" + building, e);
	}
	if (rowsAffected > 0) {
	    log.info("building updated={}", building);
	} else {
	    log.debug("building not updated={}", building);
	}
    }

    public void deleteById(int id) {
	log.debug("Delete building by id={}", id);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_DELETE_BUILDING_BY_ID, id);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to update building by id=" + id, e);
	}
	if (rowsAffected > 0) {
	    log.info("building deleted by id={}", id);
	} else {
	    log.debug("building not deleted by id={}", id);
	}
    }

}
