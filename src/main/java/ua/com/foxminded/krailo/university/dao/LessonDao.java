package ua.com.foxminded.krailo.university.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Lesson;

@Component
public class LessonDao {

    private static final String SQL_SELECT_ALL = "SELECT * FROM lessons ORDER BY id";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM lessons WHERE id = ?";
    private static final String SQL_UPDATE_BY_ID = "UPDATE lessons SET date = ?, lesson_time_id = ?, subject_id = ?, teacher_id = ?, audience_id = ?, timetable_id = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM lessons WHERE id = ?";
    private static final String SQL_INSERT_INTO_LESSONS = "INSERT INTO lessons (date, lesson_time_id, subject_id, teacher_id, audience_id, timetable_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_INSERT_INTO_LESSONS_GROUPS = "INSERT INTO lessons_groups (lesson_id, group_id) VALUES (?, ?)";
    private static final String SQL_DELETE_LESSONS_GROUPS_BY_LESSON_ID_GROUP_ID = "DELETE FROM lessons_groups WHERE lesson_id = ? AND group_id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Lesson> lessonRowMapper;

    public LessonDao(JdbcTemplate jdbcTemplate, RowMapper<Lesson> lessonRowMapper,
	    RowMapper<Group> groupFromGroupIdRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.lessonRowMapper = lessonRowMapper;
    }

    public Lesson findById(int id) {
	return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, lessonRowMapper, id);
    }

    public List<Lesson> findAll() {
	return jdbcTemplate.query(SQL_SELECT_ALL, lessonRowMapper);
    }

    public void create(Lesson lesson) {
	KeyHolder keyHolder = new GeneratedKeyHolder();
	jdbcTemplate.update(connection -> {
	    PreparedStatement ps = connection.prepareStatement(SQL_INSERT_INTO_LESSONS, new String[] { "id" });
	    ps.setObject(1, lesson.getDate());
	    ps.setInt(2, lesson.getLessonTime().getId());
	    ps.setInt(3, lesson.getSubject().getId());
	    ps.setInt(4, lesson.getTeacher().getId());
	    ps.setInt(5, lesson.getAudience().getId());
	    ps.setInt(6, lesson.getTimetable().getId());
	    return ps;
	}, keyHolder);
	lesson.setId(keyHolder.getKey().intValue());
	for (Group group : lesson.getGroups()) {
	    jdbcTemplate.update(SQL_INSERT_INTO_LESSONS_GROUPS, lesson.getId(), group.getId());
	}
    }

    public void update(Lesson lesson) {
	jdbcTemplate.update(SQL_UPDATE_BY_ID, Date.valueOf(lesson.getDate()), lesson.getLessonTime().getId(),
		lesson.getSubject().getId(), lesson.getTeacher().getId(), lesson.getAudience().getId(),
		lesson.getTimetable().getId(), lesson.getId());
	List<Group> groupsForDelete = findById(lesson.getId()).getGroups().stream()
		.filter(g -> !lesson.getGroups().contains(g)).collect(Collectors.toList());
	List<Group> groupsForInsert = lesson.getGroups().stream()
		.filter(g -> !findById(lesson.getId()).getGroups().contains(g)).collect(Collectors.toList());
	for (Group group : groupsForDelete) {
	    jdbcTemplate.update(SQL_DELETE_LESSONS_GROUPS_BY_LESSON_ID_GROUP_ID, lesson.getId(), group.getId());
	}
	for (Group group : groupsForInsert) {
	    jdbcTemplate.update(SQL_INSERT_INTO_LESSONS_GROUPS, lesson.getId(), group.getId());
	}
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

}
