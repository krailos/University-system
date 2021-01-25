package ua.com.foxminded.krailo.university.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.Timetable;

@Repository
public class TimetableDao {

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM timetables WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM timetables";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM timetables WHERE id = ?";
    private static final String SQL_INSERT_FACULTY = "INSERT INTO timetables (name, year_id, speciality_id) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE timetables SET name = ?, year_id = ?, speciality_id = ? where id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Timetable> timetableRowMapper;

    public TimetableDao(JdbcTemplate jdbcTemplate, RowMapper<Timetable> timetableRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.timetableRowMapper = timetableRowMapper;
    }

    public void create(Timetable timetable) {
	jdbcTemplate.update(SQL_INSERT_FACULTY, timetable.getName(), timetable.getYear().getId(), timetable.getSpeciality().getId());

    }

    public void update(Timetable timetable) {
	jdbcTemplate.update(SQL_UPDATE_BY_ID, timetable.getName(), timetable.getYear().getId(), timetable.getSpeciality().getId(),
		timetable.getId());

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
