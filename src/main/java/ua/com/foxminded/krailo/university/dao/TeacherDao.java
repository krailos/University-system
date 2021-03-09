package ua.com.foxminded.krailo.university.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

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
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;

@Repository
public class TeacherDao {

    private static final Logger log = LoggerFactory.getLogger(DepartmentDao.class);
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM teachers WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM teachers";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM teachers WHERE id = ?";
    private static final String SQL_INSERT_TEACHER = "INSERT INTO teachers (teacher_id, first_name, last_name, birth_date, phone, address, email, degree, gender, department_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE teachers SET teacher_id = ?, first_name = ?, last_name = ?, birth_date = ?, phone = ?, address = ?, email = ?, degree = ?, gender = ?, department_id = ? WHERE id = ?";
    private static final String SQL_SELECT_BY_SUBJECT_ID = "SELECT id, teachers.teacher_id, first_name, last_name, birth_date, phone, address, email, degree, gender, department_id  FROM teachers JOIN  teachers_subjects ON (teachers.id = teachers_subjects.teacher_id ) WHERE teachers_subjects.subject_id = ?";
    private static final String SQL_SELECT_BY_DEPARTMENT_ID = "SELECT * FROM teachers WHERE department_id = ?";
    private static final String SQL_INSERT_TEACHERS_SUBJECTS = "INSERT INTO teachers_subjects (teacher_id, subject_id) VALUES (?, ?)";
    private static final String SQL_DELETE_TEACHERS_SUBJECTS_BY_TEACHER_ID_SUBJECT_ID = "DELETE FROM teachers_subjects WHERE teacher_id = ? AND subject_id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Teacher> teacherRowMapper;

    public TeacherDao(JdbcTemplate jdbcTemplate, RowMapper<Teacher> teacherRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.teacherRowMapper = teacherRowMapper;
    }

    public void create(Teacher teacher) {
	log.debug("Create teacher={}", teacher);
	try {
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
		jdbcTemplate.update(SQL_INSERT_TEACHERS_SUBJECTS, teacher.getId(), subject.getId());
	    }
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(format("Not created teacher=%s", teacher));
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to create teacher=%s", teacher), e);
	}
	log.info("Created teacher={}", teacher);
    }

    public void update(Teacher teacher) {
	log.debug("Update teacher={}", teacher);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_UPDATE_BY_ID, teacher.getTeacherId(), teacher.getFirstName(),
		    teacher.getLastName(), Date.valueOf(teacher.getBirthDate()), teacher.getPhone(),
		    teacher.getAddress(), teacher.getEmail(), teacher.getDegree(), teacher.getGender().toString(),
		    teacher.getDepartment().getId(), teacher.getId());
	    List<Subject> subjectsOld = findById(teacher.getId()).get().getSubjects();
	    subjectsOld.stream().filter(s -> !teacher.getSubjects().contains(s)).forEach(s -> jdbcTemplate
		    .update(SQL_DELETE_TEACHERS_SUBJECTS_BY_TEACHER_ID_SUBJECT_ID, teacher.getId(), s.getId()));
	    teacher.getSubjects().stream().filter(s -> !subjectsOld.contains(s))
		    .forEach(s -> jdbcTemplate.update(SQL_INSERT_TEACHERS_SUBJECTS, teacher.getId(), s.getId()));
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(format("Not updated, teacher=%s", teacher));
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to update teacher=%s", teacher));
	}
	if (rowsAffected > 0) {
	    log.info("Updated teacher={}", teacher);
	} else {
	    log.debug("Not updated teacher={}", teacher);
	}
    }

    public Optional<Teacher> findById(int id) {
	log.debug("Find teacher by id={}", id);
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, teacherRowMapper, id));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("teacher with id={} not found", id);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to find teacher by id=%s", id), e);
	}
    }

    public List<Teacher> findAll() {
	log.debug("Find all teachers");
	try {
	    return jdbcTemplate.query(SQL_SELECT_ALL, teacherRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find all teachers", e);
	}
    }

    public void deleteById(int id) {
	log.debug("Delete teacher by id={}", id);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_DELETE_BY_ID, id);
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to delete teacher by id=%s", id), e);
	}
	if (rowsAffected > 0) {
	    log.info("Deleted teacher  by id={}", id);
	} else {
	    log.debug("Not deleted teacher by id={}", id);
	}
    }

    public List<Teacher> findBySubjectId(int id) {
	log.debug("Find teacher by subjectId={}", id);
	try {
	    return jdbcTemplate.query(SQL_SELECT_BY_SUBJECT_ID, new Object[] { id }, teacherRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to find teachers by subjectId=%s", id), e);
	}
    }

    public List<Teacher> findByDepartmentId(int id) {
	log.debug("Delete teacher by departmentId={}", id);
	try {
	    return jdbcTemplate.query(SQL_SELECT_BY_DEPARTMENT_ID, new Object[] { id }, teacherRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to find teachers by departmentId=%s", id), e);
	}
    }

}
