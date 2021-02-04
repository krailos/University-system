package ua.com.foxminded.krailo.university.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.Timetable;

@Repository
public class TimetableDao {

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM timetables WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM timetables";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM timetables WHERE id = ?";
    private static final String SQL_INSERT_FACULTY = "INSERT INTO timetables (name, year_id) VALUES (?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE timetables SET name = ?, year_id = ? where id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Timetable> timetableRowMapper;

    public TimetableDao(JdbcTemplate jdbcTemplate, RowMapper<Timetable> timetableRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.timetableRowMapper = timetableRowMapper;
    }

    public void create(Timetable timetable) {
	KeyHolder keyHolder = new GeneratedKeyHolder();
	jdbcTemplate.update(connection -> {
	    PreparedStatement ps = connection.prepareStatement(SQL_INSERT_FACULTY, new String[] { "id" });
	    ps.setString(1, timetable.getName());
	    ps.setInt(2, timetable.getYear().getId());
	    return ps;
	}, keyHolder);
	timetable.setId(keyHolder.getKey().intValue());

    }

    public void update(Timetable timetable) {
	jdbcTemplate.update(SQL_UPDATE_BY_ID, timetable.getName(), timetable.getYear().getId(), timetable.getId());
    }

    public Timetable findById(int id) {
	return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, timetableRowMapper, id);
    }

    public List<Timetable> findAll() {
	return jdbcTemplate.query(SQL_SELECT_ALL, timetableRowMapper);
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_BY_ID, id);

    }

}
