package ua.com.foxminded.krailo.university.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.LessonTime;

@Repository
public class LessonTimeDao {

    private static final String SQL_SELECT = "SELECT * FROM lesson_times WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM lesson_times ";
    private static final String SQL_SELECT_BY_LESSON_TIME_SCHEDULE_ID = "SELECT * FROM lesson_times WHERE lessons_timeschedule_id = ? ";
    private static final String SQL_DELETE = "DELETE FROM lesson_times WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO lesson_times (order_number, start_time, end_time, lessons_timeschedule_id) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE lesson_times SET order_number = ?, start_time = ?, end_time = ?, lessons_timeschedule_id = ? where id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<LessonTime> lessonTimeRowMapper;

    public LessonTimeDao(JdbcTemplate jdbcTemplate, RowMapper<LessonTime> lessonTimeRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.lessonTimeRowMapper = lessonTimeRowMapper;
    }

    public void create(LessonTime lessonTime) {
	KeyHolder keyHolder = new GeneratedKeyHolder();
	jdbcTemplate.update(connection -> {
	    PreparedStatement ps = connection.prepareStatement(SQL_INSERT, new String[] { "id" });
	    ps.setString(1, lessonTime.getOrderNumber());
	    ps.setObject(2, lessonTime.getStartTime());
	    ps.setObject(3, lessonTime.getEndTime());
	    ps.setInt(4, lessonTime.getLessonsTimeSchedule().getId());
	    return ps;
	}, keyHolder);
	lessonTime.setId(keyHolder.getKey().intValue());

    }

    public void update(LessonTime lessonTime) {
	jdbcTemplate.update(SQL_UPDATE, lessonTime.getOrderNumber(), lessonTime.getStartTime(),
		lessonTime.getEndTime(), lessonTime.getLessonsTimeSchedule().getId(), lessonTime.getId());

    }

    public LessonTime findById(int id) {
	return jdbcTemplate.queryForObject(SQL_SELECT, lessonTimeRowMapper, id);
    }

    public List<LessonTime> findAll() {
	return jdbcTemplate.query(SQL_SELECT_ALL, lessonTimeRowMapper);
    }
    
    public List<LessonTime> findBylessonTimeScheduleId(int scheduleId) {
	return jdbcTemplate.query(SQL_SELECT_BY_LESSON_TIME_SCHEDULE_ID, lessonTimeRowMapper, scheduleId);
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE, id);

    }

}
