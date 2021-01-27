package ua.com.foxminded.krailo.university.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.Subject;

@Repository
public class SubjectDao {

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM subjects WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM subjects";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM subjects WHERE id = ?";
    private static final String SQL_INSERT_DEPARTMENT = "INSERT INTO subjects (name) VALUES (?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE subjects  SET name = ? where id = ?";
    private static final String SQL_SELECT_SUBJECTS_BY_TEACHER_ID = "SELECT * FROM teachers_subjects JOIN subjects ON (teachers_subjects.subject_id = subjects.id) WHERE teachers_subjects.teacher_id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Subject> subjectRowMapper;

    public SubjectDao(JdbcTemplate jdbcTemplate, RowMapper<Subject> subjectRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.subjectRowMapper = subjectRowMapper;
    }

    public void create(Subject subject) {
	KeyHolder keyHolder = new GeneratedKeyHolder();
	jdbcTemplate.update(connection -> {
	    PreparedStatement ps = connection.prepareStatement(SQL_INSERT_DEPARTMENT, new String[] { "id" });
	    ps.setString(1, subject.getName());
	    return ps;
	}, keyHolder);
	subject.setId(keyHolder.getKey().intValue());
    }

    public void update(Subject subject) {
	jdbcTemplate.update(SQL_UPDATE_BY_ID, subject.getName(), subject.getId());

    }

    public Subject findById(int id) {
	return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, subjectRowMapper, id);
    }

    public List<Subject> findAll() {
	return jdbcTemplate.query(SQL_SELECT_ALL, subjectRowMapper);
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

    public List<Subject> findSubjectsByTeacherId(int id) {
	return jdbcTemplate.query(SQL_SELECT_SUBJECTS_BY_TEACHER_ID, new Object[] { id }, subjectRowMapper);
    }

}
