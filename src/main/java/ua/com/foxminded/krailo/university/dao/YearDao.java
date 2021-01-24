package ua.com.foxminded.krailo.university.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.Year;

@Repository
public class YearDao {

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM years WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM years ";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM years WHERE id = ?";
    private static final String SQL_INSERT_YEAR = "INSERT INTO years (name, speciality_id) VALUES (?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE years SET name = ?, speciality_id = ? where id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Year> yearRowMapper;

    public YearDao(JdbcTemplate jdbcTemplate, RowMapper<Year> yearRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.yearRowMapper = yearRowMapper;
    }

    public void create(Year  year) {
	jdbcTemplate.update(SQL_INSERT_YEAR, year.getName(), year.getSpeciality().getId());

    }

    public void update(Year year) {
	jdbcTemplate.update(SQL_UPDATE_BY_ID, year.getName(), year.getSpeciality().getId(),
		year.getId());

    }

    public Year findById(int id) {
	return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, yearRowMapper, id);
    }

    public List<Year> findAll() {
	return jdbcTemplate.query(SQL_SELECT_ALL, yearRowMapper);
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_BY_ID, id);

    }

}
