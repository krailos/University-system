
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
import ua.com.foxminded.krailo.university.model.Group;

@Repository
public class GroupDao {

    private static final Logger log = LoggerFactory.getLogger(GroupDao.class);
    
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM groups WHERE id = ?";
    private static final String SQL_SELECT_BY_NAME_YEAR_ID = "SELECT * FROM groups WHERE name = ? AND year_id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM groups ";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM groups WHERE id = ?";
    private static final String SQL_INSERT_GROUP = "INSERT INTO groups (name, year_id) VALUES (?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE groups SET name = ?, year_id = ? where id = ?";
    private static final String SQL_SELECT_GROUPS_BY_LESSON_ID = "SELECT id, name, year_id FROM groups JOIN lessons_groups ON (groups.id = lessons_groups.group_id ) WHERE lessons_groups.lesson_id = ?";
    private static final String SQL_SELECT_GROUPS_BY_YEAR_ID = "SELECT * FROM groups WHERE year_id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Group> groupRowMapper;

    public GroupDao(JdbcTemplate jdbcTemplate, RowMapper<Group> groupRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.groupRowMapper = groupRowMapper;
    }
 
    public void create(Group group) {
	log.debug("Create group={}", group);
	try {
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    jdbcTemplate.update(connection -> {
		PreparedStatement ps = connection.prepareStatement(SQL_INSERT_GROUP, new String[] { "id" });
		ps.setString(1, group.getName());
		ps.setInt(2, group.getYear().getId());
		return ps;  
	    }, keyHolder);
	    group.setId(keyHolder.getKey().intValue());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(format("Not created, group=%", group));
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to create group=" + group, e);
	}
	log.info("Created group={}", group);

    }

    public void update(Group group) {
	log.debug("Update group={}", group);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_UPDATE_BY_ID, group.getName(), group.getYear().getId(),
		    group.getId());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(format("Not created, group=%", group));
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to update group=" + group);
	}
	if (rowsAffected > 0) {
	    log.info("Updated group={}", group);
	} else {
	    log.debug("Not updated group={}", group);
	}
    }

    public Optional<Group> findById(int id) {
	log.debug("Find group by id={}", id);
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, groupRowMapper, id));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("Group with id={} not found", id);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find group by id=" + id, e);
	}
    }

    public List<Group> findAll() {
	log.debug("Find all groups");
	try {
	    return jdbcTemplate.query(SQL_SELECT_ALL, groupRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find all groups", e);
	}
    }

    public void deleteById(int id) {
	log.debug("Delete group by id={}", id);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_DELETE_BY_ID, id);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to delete group by id=" + id, e);
	}
	if (rowsAffected > 0) {
	    log.info("Deleted group  by id={}", id);
	} else {
	    log.debug("Not deleted group by id={}", id);
	}
    }

    public List<Group> findByLessonId(int lessonId) {
	log.debug("Find groups by lessonId={}", lessonId);
	try {
	    return jdbcTemplate.query(SQL_SELECT_GROUPS_BY_LESSON_ID, new Object[] { lessonId }, groupRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find groups by lessonId=" + lessonId, e);
	}
    }

    public List<Group> findByYearId(int yearId) {
	log.debug("Find groups by yearId={}", yearId);
	try {
	    return jdbcTemplate.query(SQL_SELECT_GROUPS_BY_YEAR_ID, groupRowMapper, yearId);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find groups by yearId=" + yearId, e);
	}
    }

    public Optional<Group> findByNameAndYearId(String name, int yearId) {
	log.debug("Find group by name={} and yearId={}", name, yearId);
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_NAME_YEAR_ID, groupRowMapper, name, yearId));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("Group with name={} and yearId={} not found", name, yearId);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to find group by name=%s and yearId=%s", name, yearId), e);
	}
    }

}