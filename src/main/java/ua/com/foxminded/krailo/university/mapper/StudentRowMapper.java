package ua.com.foxminded.krailo.university.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.dao.GroupDao;
import ua.com.foxminded.krailo.university.model.Gender;
import ua.com.foxminded.krailo.university.model.Student;

@Component
public class StudentRowMapper implements RowMapper<Student> {

    private GroupDao groupDao;

    public StudentRowMapper(GroupDao groupDao) {
	this.groupDao = groupDao;
    }

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
	Student student = new Student();
	student.setId(rs.getInt("id"));
	student.setStudentId(rs.getString("student_id"));
	student.setFirstName(rs.getString("first_name"));
	student.setLastName(rs.getString("last_name"));
	student.setBirthDate(rs.getObject("birth_date", LocalDate.class ));
	student.setPhone(rs.getString("phone"));
	student.setEmail(rs.getString("email"));
	student.setAddress(rs.getString("address"));
	student.setRank(rs.getString("rank"));
	student.setGender(Gender.valueOf(rs.getString("gender")));
	student.setGroup(groupDao.findById(rs.getInt("group_id")));
	return student;
    }

}
