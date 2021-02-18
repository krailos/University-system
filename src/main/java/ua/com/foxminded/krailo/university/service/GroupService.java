package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.GroupDao;
import ua.com.foxminded.krailo.university.model.Group;

@Service
public class GroupService {

    private GroupDao groupDao;

    public GroupService(GroupDao groupDao) {
	this.groupDao = groupDao;
    }

    public void create(Group group) {
	List<Group> groups = groupDao.findByYearId(group.getId());
	if (isGroupNameExist(groups, group)) {
	    groupDao.create(group);
	}
    }

    public void update(Group group) {
	List<Group> groups = groupDao.findByYearId(group.getId());
	if (isGroupNameExist(groups, group)) {
	    groupDao.update(group);
	}
    }

    public Group getById(int id) {
	return groupDao.findById(id);

    }

    public List<Group> getAll() {
	return groupDao.findAll();
    }

    public void delete(Group group) {
	groupDao.deleteById(group.getId());
    }

    private boolean isGroupNameExist(List<Group> groups, Group group) {
	return groups.stream().map(Group::getName).noneMatch(n -> n.equals(group.getName()));
    }

}
