package ua.com.foxminded.krailo.university.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.Group;

@Repository
public class GroupDao {

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
	KeyHolder keyHolder = new GeneratedKeyHolder();
	jdbcTemplate.update(connection -> {
	    PreparedStatement ps = connection.prepareStatement(SQL_INSERT_GROUP, new String[] { "id" });
	    ps.setString(1, group.getName());
	    ps.setInt(2, group.getYear().getId());
	    return ps;
	}, keyHolder);
	group.setId(keyHolder.getKey().intValue());
    }

    public void update(Group group) {
	jdbcTemplate.update(SQL_UPDATE_BY_ID, group.getName(), group.getYear().getId(), group.getId());
    }

    public Group findById(int id) {
	return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, groupRowMapper, id);
    }

    public List<Group> findAll() {
	return jdbcTemplate.query(SQL_SELECT_ALL, groupRowMapper);
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

    public List<Group> findByLessonId(int lessonId) {
	return jdbcTemplate.query(SQL_SELECT_GROUPS_BY_LESSON_ID, new Object[] { lessonId }, groupRowMapper);
    }

    public List<Group> findByYearId(int yearId) {
	return jdbcTemplate.query(SQL_SELECT_GROUPS_BY_YEAR_ID, groupRowMapper, yearId);
    }

    public Group findByNameAndYearId(String name, int yearId) {
	try {
	    return jdbcTemplate.queryForObject(SQL_SELECT_BY_NAME_YEAR_ID, groupRowMapper, name, yearId);
	} catch (EmptyResultDataAccessException e) {
	    return null;
	}
    }

}
