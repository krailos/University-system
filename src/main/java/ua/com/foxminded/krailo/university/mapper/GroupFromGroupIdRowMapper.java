package ua.com.foxminded.krailo.university.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.dao.GroupDao;
import ua.com.foxminded.krailo.university.model.Group;

@Component
public class GroupFromGroupIdRowMapper implements RowMapper<Group> {

    private GroupDao groupDao;

    public GroupFromGroupIdRowMapper(GroupDao groupDao) {
	super();
	this.groupDao = groupDao;
    }

    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
	return groupDao.findById(rs.getInt("group_id"));
    }

}
