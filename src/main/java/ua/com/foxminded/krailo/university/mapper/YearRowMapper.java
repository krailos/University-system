package ua.com.foxminded.krailo.university.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.dao.SpecialityDao;
import ua.com.foxminded.krailo.university.model.Year;

@Component
public class YearRowMapper implements RowMapper<Year> {

    private SpecialityDao specialityDao;

    public YearRowMapper(SpecialityDao specialityDao) {
	this.specialityDao = specialityDao;
    }

    @Override
    public Year mapRow(ResultSet rs, int rowNum) throws SQLException {
	Year year = new Year();
	year.setId(rs.getInt("id"));
	year.setName(rs.getString("name"));
	year.setSpeciality(specialityDao.findById(rs.getInt("speciality_id")));
	return year;
    }

}
