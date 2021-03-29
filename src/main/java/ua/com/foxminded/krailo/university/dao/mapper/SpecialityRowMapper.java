package ua.com.foxminded.krailo.university.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.dao.FacultyDao;
import ua.com.foxminded.krailo.university.model.Specialty;

@Component
public class SpecialityRowMapper implements RowMapper<Specialty> {

    private FacultyDao facultyDao;

    public SpecialityRowMapper(FacultyDao facultyDao) {
	this.facultyDao = facultyDao;
    }

    @Override
    public Specialty mapRow(ResultSet rs, int rowNum) throws SQLException {
	Specialty speciality = new Specialty();
	speciality.setId(rs.getInt("id"));
	speciality.setName(rs.getString("name"));
	facultyDao.findById(rs.getInt("faculty_id")).ifPresent(speciality::setFaculty);
	return speciality;
    }

}
