package ua.com.foxminded.krailo.university.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.model.Vocation;

@Component
public class VocationDao {

    private static final String SQL_SELECT_ALL = "SELECT * FROM vocations ORDER BY id";
    private static final String SQL_SELECT_BY_TEACHER_ID = "SELECT * FROM vocations where teacher_id = ?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM vocations WHERE id = ?";
    private static final String SQL_UPDATE_BY_ID = "UPDATE vocations SET kind = ?, applying_date = ?, start_date = ?, end_date = ?, teacher_id = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM vocations WHERE id = ?";
    private static final String SQL_INSERT_VOCATION = "INSERT INTO vocations (kind, applying_date, start_date, end_date, teacher_id) VALUES (?, ?, ?, ?, ?)";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Vocation> vocationRowMapper;

    public VocationDao(JdbcTemplate jdbcTemplate, RowMapper<Vocation> vocationRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.vocationRowMapper = vocationRowMapper;
    }

    public Vocation findById(int id) {
	return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, vocationRowMapper, id);
    }

    public List<Vocation> findAll() {
	return jdbcTemplate.query(SQL_SELECT_ALL, vocationRowMapper);
    }
    
    public List<Vocation> findByTeacherId(int teacherId) {
	return jdbcTemplate.query(SQL_SELECT_BY_TEACHER_ID, vocationRowMapper, teacherId);
    }

    public void create(Vocation vocation) {
	KeyHolder keyHolder = new GeneratedKeyHolder();
	jdbcTemplate.update(connection -> {
	    PreparedStatement ps = connection.prepareStatement(SQL_INSERT_VOCATION, new String[] { "id" });
	    ps.setString(1, vocation.getKind());
	    ps.setDate(2, Date.valueOf(vocation.getApplyingDate()));
	    ps.setDate(3, Date.valueOf(vocation.getStart()));
	    ps.setDate(4, Date.valueOf(vocation.getEnd()));
	    ps.setInt(5, vocation.getTeacher().getId());
	    return ps;
	}, keyHolder);
	vocation.setId(keyHolder.getKey().intValue());
    }

    public void update(Vocation vocation) {
	jdbcTemplate.update(SQL_UPDATE_BY_ID, vocation.getKind(), Date.valueOf(vocation.getApplyingDate()),
		Date.valueOf(vocation.getStart()), Date.valueOf(vocation.getEnd()), vocation.getTeacher().getId(),
		vocation.getId());
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

}
