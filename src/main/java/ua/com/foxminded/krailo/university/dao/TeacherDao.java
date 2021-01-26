package ua.com.foxminded.krailo.university.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.Teacher;

@Repository
public class TeacherDao {

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM teachers WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM teachers";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM teachers WHERE id = ?";
    private static final String SQL_INSERT_TEACHER = "INSERT INTO teachers (teacher_id, first_name, last_name, birth_date, phone, address, email, degree, gender, department_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE teachers SET teacher_id = ?, first_name = ?, last_name = ?, birth_date = ?, phone = ?, address = ?, email = ?, degree = ?, gender = ?, department_id = ? WHERE id = ?";

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
    }

    public void update(Teacher teacher) {
	jdbcTemplate.update(SQL_UPDATE_BY_ID, teacher.getTeacherId(), teacher.getFirstName(), teacher.getLastName(),
		Date.valueOf(teacher.getBirthDate()), teacher.getPhone(), teacher.getAddress(), teacher.getEmail(),
		teacher.getDegree(), teacher.getGender().toString(), teacher.getDepartment().getId(), teacher.getId());
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

}
