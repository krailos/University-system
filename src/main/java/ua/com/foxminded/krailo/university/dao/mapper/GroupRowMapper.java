package ua.com.foxminded.krailo.university.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.dao.YearDao;
import ua.com.foxminded.krailo.university.model.Group;

@Component
public class GroupRowMapper implements RowMapper<Group> {

    private YearDao yearDao;

    public GroupRowMapper(YearDao yearDao) {
	this.yearDao = yearDao;
    }

    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
	Group group = new Group();
	group.setId(rs.getInt("id"));
	group.setName(rs.getString("name"));
	yearDao.findById(rs.getInt("year_id")).ifPresent(group::setYear);
	return group;
    }

}
