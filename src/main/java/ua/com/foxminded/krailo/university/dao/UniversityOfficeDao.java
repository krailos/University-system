package ua.com.foxminded.krailo.university.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.UniversityOffice;

@Repository
public class UniversityOfficeDao {

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM university_office  WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM university_office";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM university_office WHERE id = ?";
    private static final String SQL_INSERT_UNIVERSITY_OFFICE = "INSERT INTO university_office (name, address) VALUES (?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE university_office SET name = ?, address = ? where id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<UniversityOffice> universityOfficeRowMapper;
    
    public UniversityOfficeDao(JdbcTemplate jdbcTemplate, RowMapper<UniversityOffice> universityOfficeRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.universityOfficeRowMapper = universityOfficeRowMapper;
    }
    
    public UniversityOffice findById (int id) {
	return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object [] {id}, universityOfficeRowMapper);
    }
    
    public List<UniversityOffice> findAll () {
	return jdbcTemplate.query(SQL_SELECT_ALL, universityOfficeRowMapper);
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_BY_ID, id);
	
    }

    public void update(UniversityOffice universityOffice) {
	jdbcTemplate.update(SQL_UPDATE_BY_ID, universityOffice.getName(), universityOffice.getAddress(), universityOffice.getId());
	
    }

    public void create(UniversityOffice universityOffice) {
	jdbcTemplate.update(SQL_INSERT_UNIVERSITY_OFFICE, universityOffice.getName(), universityOffice.getAddress());
	
    }
    
    

    
    
}
