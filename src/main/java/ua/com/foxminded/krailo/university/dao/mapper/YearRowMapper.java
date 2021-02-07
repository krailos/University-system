package ua.com.foxminded.krailo.university.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.dao.SpecialityDao;
import ua.com.foxminded.krailo.university.dao.SubjectDao;
import ua.com.foxminded.krailo.university.model.Year;

@Component
public class YearRowMapper implements RowMapper<Year> {

    private SpecialityDao specialityDao;
    private SubjectDao subjectDao;

    public YearRowMapper(SpecialityDao specialityDao, SubjectDao subjectDao) {
	this.specialityDao = specialityDao;
	this.subjectDao = subjectDao;
    }

    @Override
    public Year mapRow(ResultSet rs, int rowNum) throws SQLException {
	Year year = new Year();
	year.setId(rs.getInt("id"));
	year.setName(rs.getString("name"));
	year.setSpeciality(specialityDao.findById(rs.getInt("speciality_id")));
	year.setSubjects(subjectDao.findByYearId(rs.getInt("id")));
	return year;
    }

}
