package ua.com.foxminded.krailo.university.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.Speciality;

@Repository
public class SpecialityDao {

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM specialities WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM specialities ";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM specialities WHERE id = ?";
    private static final String SQL_INSERT_SPECIALITY = "INSERT INTO specialities (name, faculty_id) VALUES (?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE specialities SET name = ?, faculty_id = ? where id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Speciality> specialityRowMapper;

    public SpecialityDao(JdbcTemplate jdbcTemplate, RowMapper<Speciality> specialityRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.specialityRowMapper = specialityRowMapper;
    }

    public void create(Speciality speciality) {
	jdbcTemplate.update(SQL_INSERT_SPECIALITY, speciality.getName(), speciality.getFaculty().getId());

    }

    public void update(Speciality speciality) {
	jdbcTemplate.update(SQL_UPDATE_BY_ID, speciality.getName(), speciality.getFaculty().getId(),
		speciality.getId());

    }

    public Speciality findById(int id) {
	return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, specialityRowMapper, id);
    }

    public List<Speciality> findAll() {
	return jdbcTemplate.query(SQL_SELECT_ALL, specialityRowMapper);
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_BY_ID, id);

    }

}
