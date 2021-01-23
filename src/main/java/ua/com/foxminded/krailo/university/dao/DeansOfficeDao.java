package ua.com.foxminded.krailo.university.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.DeansOffice;

@Repository
public class DeansOfficeDao {

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM deans_office   WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM deans_office ";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM deans_office  WHERE id = ?";
    private static final String SQL_INSERT_DEANS_OFFICE = "INSERT INTO deans_office  (name, university_office_id) VALUES (?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE deans_office SET name = ?, university_office_id = ? where id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<DeansOffice> deansOfficeRowMapper;

    public DeansOfficeDao(JdbcTemplate jdbcTemplate, RowMapper<DeansOffice> deansOfficeRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.deansOfficeRowMapper = deansOfficeRowMapper;
    }

    public void create(DeansOffice deansOffice) {
	jdbcTemplate.update(SQL_INSERT_DEANS_OFFICE, deansOffice.getName(), deansOffice.getUniversityOffice().getId());

    }

    public void update(DeansOffice deansOffice) {
	jdbcTemplate.update(SQL_UPDATE_BY_ID, deansOffice.getName(), deansOffice.getUniversityOffice().getId(),
		deansOffice.getId());

    }

    public DeansOffice findById(int id) {
	return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, deansOfficeRowMapper, id);
    }

    public List<DeansOffice> findAll() {
	return jdbcTemplate.query(SQL_SELECT_ALL, deansOfficeRowMapper);
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_BY_ID, id);

    }

}
