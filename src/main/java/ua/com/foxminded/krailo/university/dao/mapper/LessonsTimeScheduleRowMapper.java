package ua.com.foxminded.krailo.university.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.dao.LessonTimeDao;
import ua.com.foxminded.krailo.university.dao.LessonTimeScheduleDao;
import ua.com.foxminded.krailo.university.model.LessonsTimeSchedule;

@Component
public class LessonsTimeScheduleRowMapper implements RowMapper<LessonsTimeSchedule> {

    @Override
    public LessonsTimeSchedule mapRow(ResultSet rs, int rowNum) throws SQLException {
	LessonsTimeSchedule lessonsTimeSchedule = new LessonsTimeSchedule();
	lessonsTimeSchedule.setId(rs.getInt("id"));
	lessonsTimeSchedule.setName(rs.getString("name"));
	return lessonsTimeSchedule;
    }

}
