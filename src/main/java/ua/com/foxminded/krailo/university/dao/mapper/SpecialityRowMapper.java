package ua.com.foxminded.krailo.university.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.dao.FacultyDao;
import ua.com.foxminded.krailo.university.model.Speciality;

@Component
public class SpecialityRowMapper implements RowMapper<Speciality> {

    private FacultyDao facultyDao;

    public SpecialityRowMapper(FacultyDao facultyDao) {
	this.facultyDao = facultyDao;
    }

    @Override
    public Speciality mapRow(ResultSet rs, int rowNum) throws SQLException {
	Speciality speciality = new Speciality();
	speciality.setId(rs.getInt("id"));
	speciality.setName(rs.getString("name"));
	speciality.setFaculty(facultyDao.findById(rs.getInt("faculty_id")).get());
	return speciality;
    }

}
