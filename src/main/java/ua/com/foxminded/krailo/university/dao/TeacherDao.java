package ua.com.foxminded.krailo.university.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;

@Repository
public class TeacherDao {

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM teachers WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM teachers";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM teachers WHERE id = ?";
    private static final String SQL_INSERT_TEACHER = "INSERT INTO teachers (teacher_id, first_name, last_name, birth_date, phone, address, email, degree, gender, department_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE teachers SET teacher_id = ?, first_name = ?, last_name = ?, birth_date = ?, phone = ?, address = ?, email = ?, degree = ?, gender = ?, department_id = ? WHERE id = ?";
    private static final String SQL_SELECT_TEACHERS_BY_SUBJECT_ID = "SELECT id, teachers.teacher_id, first_name, last_name, birth_date, phone, address, email, degree, gender, department_id  FROM teachers JOIN  teachers_subjects ON (teachers.id = teachers_subjects.teacher_id ) WHERE teachers_subjects.subject_id = ?";
    private static final String SQL_INSERT_INTO_TEACHERS_SUBJECTS = "INSERT INTO teachers_subjects (teacher_id, subject_id) VALUES (?, ?)";
    private static final String SQL_DELETE_TEACHERS_SUBJECTS_BY_TEACHER_ID = "DELETE FROM teachers_subjects WHERE teacher_id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Teacher> teacherRowMapper;

    public TeacherDao(JdbcTemplate jdbcTemplate, RowMapper<Teacher> teacherRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.teacherRowMapper = teacherRowMapper;
    }

    public void create(Teacher teacher) {
	KeyHolder keyHolder = new GeneratedKeyHolder();
	jdbcTemplate.update(connection -> {
	    PreparedStatement ps = connection.prepareStatement(SQL_INSERT_TEACHER, new String[] { "id" });
	    ps.setString(1, teacher.getTeacherId());
	    ps.setString(2, teacher.getFirstName());
	    ps.setString(3, teacher.getLastName());
	    ps.setDate(4, Date.valueOf(teacher.getBirthDate()));
	    ps.setString(5, teacher.getPhone());
	    ps.setString(6, teacher.getEmail());
	    ps.setString(7, teacher.getAddress());
	    ps.setString(8, teacher.getDegree());
	    ps.setString(9, teacher.getGender().toString());
	    ps.setInt(10, teacher.getDepartment().getId());
	    return ps;
	}, keyHolder);
	teacher.setId(keyHolder.getKey().intValue());
	for (Subject subject : teacher.getSubjects()) {
	    jdbcTemplate.update(SQL_INSERT_INTO_TEACHERS_SUBJECTS, teacher.getId(), subject.getId());
	}
    }

    public void update(Teacher teacher) {
	jdbcTemplate.update(SQL_UPDATE_BY_ID, teacher.getTeacherId(), teacher.getFirstName(), teacher.getLastName(),
		Date.valueOf(teacher.getBirthDate()), teacher.getPhone(), teacher.getAddress(), teacher.getEmail(),
		teacher.getDegree(), teacher.getGender().toString(), teacher.getDepartment().getId(), teacher.getId());
	jdbcTemplate.update(SQL_DELETE_TEACHERS_SUBJECTS_BY_TEACHER_ID, teacher.getId());
	for (Subject subject : teacher.getSubjects()) {
	    jdbcTemplate.update(SQL_INSERT_INTO_TEACHERS_SUBJECTS, teacher.getId(), subject.getId());
	}
    }

    public Teacher findById(int id) {
	return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, teacherRowMapper, id);
    }

    public List<Teacher> findAll() {
	return jdbcTemplate.query(SQL_SELECT_ALL, teacherRowMapper);
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

    public List<Teacher> findTeacherBySubjectId(int id) {
	return jdbcTemplate.query(SQL_SELECT_TEACHERS_BY_SUBJECT_ID, new Object[] { id }, teacherRowMapper);
    }

}
