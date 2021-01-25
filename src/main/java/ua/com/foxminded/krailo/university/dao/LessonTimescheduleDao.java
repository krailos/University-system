package ua.com.foxminded.krailo.university.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.LessonsTimeSchedule;

@Repository
public class LessonTimescheduleDao {

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM lessons_timeschedule WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM lessons_timeschedule  ";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM lessons_timeschedule   WHERE id = ?";
    private static final String SQL_INSERT_LESSONS_TIME_SCHEDULE = "INSERT INTO lessons_timeschedule (name) VALUES (?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE lessons_timeschedule  SET name = ? where id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<LessonsTimeSchedule> lessonsTimeScheduleRowMapper;

    public LessonTimescheduleDao(JdbcTemplate jdbcTemplate,  RowMapper<LessonsTimeSchedule> lessonsTimescheduleRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.lessonsTimeScheduleRowMapper = lessonsTimescheduleRowMapper;
    }

    public void create(LessonsTimeSchedule lessonsTimeSchedule) {
	jdbcTemplate.update(SQL_INSERT_LESSONS_TIME_SCHEDULE, lessonsTimeSchedule.getName());

    }

    public void update(LessonsTimeSchedule lessonsTimeSchedule) {
	jdbcTemplate.update(SQL_UPDATE_BY_ID, lessonsTimeSchedule.getName(), lessonsTimeSchedule.getId());

    }

    public LessonsTimeSchedule findById(int id) {
	return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, lessonsTimeScheduleRowMapper, id);
    }

    public List<LessonsTimeSchedule> findAll() {
	return jdbcTemplate.query(SQL_SELECT_ALL, lessonsTimeScheduleRowMapper);
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_BY_ID, id);

    }

}
