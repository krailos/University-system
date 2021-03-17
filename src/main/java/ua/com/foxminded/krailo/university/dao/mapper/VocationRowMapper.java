package ua.com.foxminded.krailo.university.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.dao.TeacherDao;
import ua.com.foxminded.krailo.university.model.Vocation;
import ua.com.foxminded.krailo.university.model.VocationKind;

@Component
public class VocationRowMapper implements RowMapper<Vocation> {

    private TeacherDao teacherDao;

    public VocationRowMapper(TeacherDao teacherDao) {
	this.teacherDao = teacherDao;
    }

    @Override
    public Vocation mapRow(ResultSet rs, int rowNum) throws SQLException {
	Vocation vocation = new Vocation();
	vocation.setId(rs.getInt("id"));
	vocation.setKind(VocationKind.valueOf(rs.getString("kind")));
	vocation.setApplyingDate(rs.getObject("applying_date", LocalDate.class));
	vocation.setStart(rs.getObject("start_date", LocalDate.class));
	vocation.setEnd(rs.getObject("end_date", LocalDate.class));
	teacherDao.findById(rs.getInt("teacher_id")).ifPresent(vocation::setTeacher);
	return vocation;
    }

}
