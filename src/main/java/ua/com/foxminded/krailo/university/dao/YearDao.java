package ua.com.foxminded.krailo.university.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Year;

@Repository
public class YearDao {

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM years WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM years ";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM years WHERE id = ?";
    private static final String SQL_INSERT_YEAR = "INSERT INTO years (name, speciality_id) VALUES (?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE years SET name = ?, speciality_id = ? where id = ?";
    private static final String SQL_INSERT_INTO_YEARS_SUBJECTS = "INSERT INTO years_subjects (year_id, subject_id) VALUES (?, ?)";
    private static final String SQL_DELETE_YEARS_SUBJECTS_BY_LESSON_ID_GROUP_ID = "DELETE FROM years_subjects WHERE year_id = ? AND subject_id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Year> yearRowMapper;

    public YearDao(JdbcTemplate jdbcTemplate, RowMapper<Year> yearRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.yearRowMapper = yearRowMapper;
    }

    public void create(Year year) {
	KeyHolder keyHolder = new GeneratedKeyHolder();
	jdbcTemplate.update(connection -> {
	    PreparedStatement ps = connection.prepareStatement(SQL_INSERT_YEAR, new String[] { "id" });
	    ps.setString(1, year.getName());
	    ps.setInt(2, year.getSpeciality().getId());
	    return ps;
	}, keyHolder);
	year.setId(keyHolder.getKey().intValue());
	for (Subject subject : year.getSubjects()) {
	    addSubjectToYear(subject, year);
	}
    }

    public void update(Year year) {
	jdbcTemplate.update(SQL_UPDATE_BY_ID, year.getName(), year.getSpeciality().getId(), year.getId());

	List<Subject> subjectsOld = findById(year.getId()).getSubjects();
	subjectsOld.stream().filter(s -> !year.getSubjects().contains(s)).forEach(
		s -> jdbcTemplate.update(SQL_DELETE_YEARS_SUBJECTS_BY_LESSON_ID_GROUP_ID, year.getId(), s.getId()));
	year.getSubjects().stream().filter(s -> !subjectsOld.contains(s))
		.forEach(s -> jdbcTemplate.update(SQL_INSERT_INTO_YEARS_SUBJECTS, year.getId(), s.getId()));

    }

    public Year findById(int id) {
	return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, yearRowMapper, id);
    }

    public List<Year> findAll() {
	return jdbcTemplate.query(SQL_SELECT_ALL, yearRowMapper);
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }
    private void addSubjectToYear(Subject subject, Year year) {
	jdbcTemplate.update(SQL_INSERT_INTO_YEARS_SUBJECTS, year.getId(), subject.getId());
    }

}
