package ua.com.foxminded.krailo.university.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.dao.DepartmentDao;
import ua.com.foxminded.krailo.university.model.Gender;
import ua.com.foxminded.krailo.university.model.Teacher;

@Component
public class TeacherRowMapper implements RowMapper<Teacher> {

    private DepartmentDao departmentDao;

    public TeacherRowMapper(DepartmentDao departmentDao) {
	this.departmentDao = departmentDao;
    }

    @Override
    public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
	Teacher teacher = new Teacher();
	teacher.setId(rs.getInt("id"));
	teacher.setTeacherId(rs.getString("teacher_id"));
	teacher.setFirstName(rs.getString("first_name"));
	teacher.setLastName(rs.getString("last_name"));
	teacher.setBirthDate(rs.getObject("birth_date", LocalDate.class));
	teacher.setPhone(rs.getString("phone"));
	teacher.setEmail(rs.getString("email"));
	teacher.setAddress(rs.getString("address"));
	teacher.setDegree(rs.getString("degree"));
	teacher.setGender(Gender.valueOf(rs.getString("gender")));
	teacher.setDepartment(departmentDao.findById(rs.getInt("department_id")));
	return teacher;
    }

}
