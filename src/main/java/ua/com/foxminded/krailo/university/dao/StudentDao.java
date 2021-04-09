package ua.com.foxminded.krailo.university.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.exception.DaoConstraintViolationException;
import ua.com.foxminded.krailo.university.exception.DaoException;
import ua.com.foxminded.krailo.university.model.Student;

@Repository
public class StudentDao {

    private static final Logger log = LoggerFactory.getLogger(StudentDao.class);

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM students WHERE id = ?";
    private static final String SQL_SELECT_BY_GROUO_ID = "SELECT * FROM students WHERE group_id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM students";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM students WHERE id = ?";
    private static final String SQL_INSERT_STUDENT = "INSERT INTO students (student_id, first_name, last_name, birth_date, phone, address, email, rank, gender, group_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE students SET student_id = ?, first_name = ?, last_name = ?, birth_date = ?, phone = ?, address = ?, email = ?, rank = ?, gender = ?, group_id = ? WHERE id = ?";

    private static final String SQL_STUDENTS_COUNT = "SELECT COUNT (*) AS count FROM students";

    private static final String SQL_SELECT_WITH_LIMIT = "SELECT * FROM students ORDER BY last_name, first_name LIMIT ? OFFSET ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Student> studentRowMapper;

    public StudentDao(JdbcTemplate jdbcTemplate, RowMapper<Student> studentRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.studentRowMapper = studentRowMapper;
    }

    public void create(Student student) {
	log.debug("Create student={}", student);
	try {
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
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException("Not created student=" + student, e);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to create student=" + student, e);
	}
	log.info("Created student={}", student);
    }

    public void update(Student student) {
	log.debug("Update student={}", student);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_UPDATE_BY_ID, student.getStudentId(), student.getFirstName(),
		    student.getLastName(), Date.valueOf(student.getBirthDate()), student.getPhone(),
		    student.getAddress(), student.getEmail(), student.getRank(), student.getGender().toString(),
		    student.getGroup().getId(), student.getId());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException("Not updated, student= " + student, e);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to update student=" + student, e);
	}
	if (rowsAffected > 0) {
	    log.info("Updated student={}", student);
	} else {
	    log.debug("Not updated student={}", student);
	}
    }

    public Optional<Student> findById(int id) {
	log.debug("Find student by id={}", id);
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, studentRowMapper, id));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("student with id={} not found", id);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find student by id=" + id, e);
	}
    }

    public List<Student> findAll() {
	log.debug("Find all students");
	try {
	    return jdbcTemplate.query(SQL_SELECT_ALL, studentRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find all students", e);
	}
    }

    public List<Student> findByGroupId(int groupId) {
	log.debug("Find students by groupId={}", groupId);
	try {
	    return jdbcTemplate.query(SQL_SELECT_BY_GROUO_ID, studentRowMapper, groupId);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find students by groupId=" + groupId, e);
	}
    }

    public void deleteById(int studentId) {
	log.debug("Delete student by id={}", studentId);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_DELETE_BY_ID, studentId);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to delete student by id=" + studentId, e);
	}
	if (rowsAffected > 0) {
	    log.info("Deleted student  by id={}", studentId);
	} else {
	    log.debug("Not deleted student by id={}", studentId);
	}
    }

    public int findQuantity() {
	log.debug("find students count");
	try {
	    return jdbcTemplate.queryForObject(SQL_STUDENTS_COUNT, Integer.class);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find students count", e);
	}
    }

    public List<Student> findWithLimit(int limit, int offset) {
	log.debug("find students by page");
	try {
	    return jdbcTemplate.query(SQL_SELECT_WITH_LIMIT, studentRowMapper, limit, offset);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find students by page", e);
	}
    }

}
