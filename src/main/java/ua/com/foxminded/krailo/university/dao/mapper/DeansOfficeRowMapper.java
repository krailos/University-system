package ua.com.foxminded.krailo.university.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.dao.UniversityOfficeDao;
import ua.com.foxminded.krailo.university.model.DeansOffice;

@Component
public class DeansOfficeRowMapper implements RowMapper<DeansOffice> {

    private UniversityOfficeDao universityOfficeDao;

    public DeansOfficeRowMapper(UniversityOfficeDao universityOfficeDao) {
	this.universityOfficeDao = universityOfficeDao;
    }

    @Override
    public DeansOffice mapRow(ResultSet rs, int rowNum) throws SQLException {
	DeansOffice deansOffice = new DeansOffice();
	deansOffice.setId(rs.getInt("id"));
	deansOffice.setName(rs.getString("name"));
	universityOfficeDao.findById(rs.getInt("university_office_id")).ifPresent(deansOffice::setUniversityOffice);
	return deansOffice;
    }

}