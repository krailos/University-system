package ua.com.foxminded.krailo.university.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.dao.LessonsTimescheduleDao;
import ua.com.foxminded.krailo.university.model.LessonTime;

@Component
public class LessonTimeRowMapper implements RowMapper<LessonTime> {

    private LessonsTimescheduleDao lessonTimeSceduleDao;

    public LessonTimeRowMapper(LessonsTimescheduleDao lessonTimeSceduleDao) {
	this.lessonTimeSceduleDao = lessonTimeSceduleDao;
    }

    @Override
    public LessonTime mapRow(ResultSet rs, int rowNum) throws SQLException {
	LessonTime lessonTime = new LessonTime();
	lessonTime.setId(rs.getInt("id"));
	lessonTime.setOrderNumber(rs.getString("order_number"));
	lessonTime.setStartTime(rs.getObject("start_time", LocalTime.class));
	lessonTime.setEndTime(rs.getObject("end_time", LocalTime.class));
	lessonTime.setLessonsTimeSchedule(lessonTimeSceduleDao.findById(rs.getInt("lessons_timeschedule_id")));
	return lessonTime;
    }

}
