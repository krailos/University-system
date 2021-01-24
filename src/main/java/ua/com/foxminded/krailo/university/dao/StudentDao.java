package ua.com.foxminded.krailo.university.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.Student;

@Repository
public class StudentDao {

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM students WHERE id = ?";
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
	jdbcTemplate.update(SQL_INSERT_STUDENT, student.getStudentId(), student.getFirstName(), student.getLastName(),
		Date.valueOf(student.getBirthDate()), student.getPhone(), student.getEmail(), student.getAddress(),
		student.getRank(), student.getGender().toString(), student.getGroup().getId());

    }

    public void update(Student student) {
	jdbcTemplate.update(SQL_UPDATE_BY_ID, student.getStudentId(), student.getFirstName(), student.getLastName(),
		Date.valueOf(student.getBirthDate()), student.getPhone(), student.getAddress(), student.getEmail(),
		student.getRank(), student.getGender().toString(), student.getGroup().getId(), student.getId());
    }

    public Student findById(int id) {
	return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, studentRowMapper, id);
    }

    public List<Student> findAll() {
	return jdbcTemplate.query(SQL_SELECT_ALL, studentRowMapper);
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_BY_ID, id);

    }

}
