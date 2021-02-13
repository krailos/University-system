package ua.com.foxminded.krailo.university.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.Student;

@Repository
public class StudentDao {

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM students WHERE id = ?";
    private static final String SQL_SELECT_BY_GROUO_ID = "SELECT * FROM students WHERE group_id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM students";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM students WHERE id = ?";
    private static final String SQL_INSERT_STUDENT = "INSERT INTO students (student_id, first_name, last_name, birth_date, phone, address, email, rank, gender, group_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE students SET student_id = ?, first_name = ?, last_name = ?, birth_date = ?, phone = ?, address = ?, email = ?, rank = ?, gender = ?, group_id = ? WHERE id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Student> studentRowMapper;

    public StudentDao(JdbcTemplate jdbcTemplate, RowMapper<Student> studentRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.studentRowMapper = studentRowMapper;
    }

    public void create(Student student) {
	KeyHolder keyHolder = new GeneratedKeyHolder();
	jdbcTemplate.update(connection -> {
	    PreparedStatement ps = connection.prepareStatement(SQL_INSERT_STUDENT, new String[] { "id" });
	    ps.setString(1, student.getStudentId());
	    ps.setString(2, student.getFirstName());
	    ps.setString(3, student.getLastName());
	    ps.setDate(4, Date.valueOf(student.getBirthDate()));
	    ps.setString(5, student.getPhone());
	    ps.setString(6, student.getEmail());
	    ps.setString(7, student.getAddress());
	    ps.setString(8, student.getRank());
	    ps.setString(9, student.getGender().toString());
	    ps.setInt(10, student.getGroup().getId());
	    return ps;
	}, keyHolder);
	student.setId(keyHolder.getKey().intValue());
    }

    public void update(Student student) {
	jdbcTemplate.update(SQL_UPDATE_BY_ID, student.getStudentId(), student.getFirstName(), student.getLastName(),
		Date.valueOf(student.getBirthDate()), student.getPhone(), student.getAddress(), student.getEmail(),
		student.getRank(), student.getGender().toString(), student.getGroup().getId(), student.getId());
    }

    public Student findById(int studentId) {
	return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, studentRowMapper, studentId);
    }

    public List<Student> findAll() {
	return jdbcTemplate.query(SQL_SELECT_ALL, studentRowMapper);
    }
    
    public List<Student> findByGroupId(int groupId) {
	return jdbcTemplate.query(SQL_SELECT_BY_GROUO_ID, studentRowMapper, groupId);
    }

    public void deleteById(int studentId) {
	jdbcTemplate.update(SQL_DELETE_BY_ID, studentId);
    }

}
