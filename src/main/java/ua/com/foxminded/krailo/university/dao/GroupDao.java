package ua.com.foxminded.krailo.university.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.Group;

@Repository
public class GroupDao {

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM groups WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM groups ";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM groups WHERE id = ?";
    private static final String SQL_INSERT_GROUP = "INSERT INTO groups (name, year_id) VALUES (?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE groups SET name = ?, year_id = ? where id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Group> groupRowMapper;

    public GroupDao(JdbcTemplate jdbcTemplate, RowMapper<Group> groupRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.groupRowMapper = groupRowMapper;
    }

    public void create(Group group) {
	jdbcTemplate.update(SQL_INSERT_GROUP, group.getName(), group.getYear().getId());
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
    
}
