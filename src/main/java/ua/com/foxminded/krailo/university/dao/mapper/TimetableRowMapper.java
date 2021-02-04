package ua.com.foxminded.krailo.university.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.dao.YearDao;
import ua.com.foxminded.krailo.university.model.Timetable;

@Component
public class TimetableRowMapper implements RowMapper<Timetable> {

    private YearDao yearDao;

    public TimetableRowMapper(YearDao yearDao) {
	this.yearDao = yearDao;
    }

    @Override
    public Timetable mapRow(ResultSet rs, int rowNum) throws SQLException {
	Timetable timetable = new Timetable();
	timetable.setId(rs.getInt("id"));
	timetable.setName(rs.getString("name"));
	timetable.setYear(yearDao.findById(rs.getInt("year_id")));
	return timetable;
    }

}
