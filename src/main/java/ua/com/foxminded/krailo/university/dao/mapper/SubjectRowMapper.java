package ua.com.foxminded.krailo.university.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.model.Subject;

@Component
public class SubjectRowMapper implements RowMapper<Subject> {
    

    @Override
    public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
	Subject subject = new Subject();
	subject.setId(rs.getInt("id"));
	subject.setName(rs.getString("name"));
	return subject;
    }

}
