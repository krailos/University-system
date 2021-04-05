package ua.com.foxminded.krailo.university.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.dao.SubjectDao;
import ua.com.foxminded.krailo.university.model.Year;

@Component
public class YearRowMapper implements RowMapper<Year> {

    private SubjectDao subjectDao;

    public YearRowMapper(SubjectDao subjectDao) {

	this.subjectDao = subjectDao;
    }

    @Override
    public Year mapRow(ResultSet rs, int rowNum) throws SQLException {
	Year year = new Year();
	year.setId(rs.getInt("id"));
	year.setName(rs.getString("name"));
	year.setSubjects(subjectDao.findByYearId(rs.getInt("id")));
	return year;
    }

}
