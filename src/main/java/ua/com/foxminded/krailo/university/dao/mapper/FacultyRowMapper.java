package ua.com.foxminded.krailo.university.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.dao.DeansOfficeDao;
import ua.com.foxminded.krailo.university.model.Faculty;

@Component
public class FacultyRowMapper implements RowMapper<Faculty> {

    private DeansOfficeDao deansOfficeDao;

    public FacultyRowMapper(DeansOfficeDao deansOfficeDao) {
	this.deansOfficeDao = deansOfficeDao;
    }

    @Override
    public Faculty mapRow(ResultSet rs, int rowNum) throws SQLException {
	Faculty faculty = new Faculty();
	faculty.setId(rs.getInt("id"));
	faculty.setName(rs.getString("name"));
	faculty.setDeansOffice(deansOfficeDao.findById(rs.getInt("deans_office_id")));
	return faculty;
    }

}
