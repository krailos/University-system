package ua.com.foxminded.krailo.university.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.Faculty;

@Repository
public class FacultyDao {

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
	KeyHolder keyHolder = new GeneratedKeyHolder();
	jdbcTemplate.update(connection -> {
	    PreparedStatement ps = connection.prepareStatement(SQL_INSERT_FACULTY, new String[] { "id" });
	    ps.setString(1, faculty.getName());
	    ps.setInt(2, faculty.getDeansOffice().getId());
	    return ps;
	}, keyHolder);
	faculty.setId(keyHolder.getKey().intValue());
    }

    public void update(Faculty faculty) {
	jdbcTemplate.update(SQL_UPDATE_BY_ID, faculty.getName(), faculty.getDeansOffice().getId(), faculty.getId());

    }

    public Faculty findById(int id) {
	return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, facultyRowMapper, id);
    }

    public List<Faculty> findAll() {
	return jdbcTemplate.query(SQL_SELECT_ALL, facultyRowMapper);
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_BY_ID, id);

    }

}
