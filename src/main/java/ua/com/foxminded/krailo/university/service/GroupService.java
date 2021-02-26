package ua.com.foxminded.krailo.university.service;

import java.util.List;
import java.util.Optional;

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
	if (isUniqueGroupName(group)) {
	    groupDao.create(group);
	}
    }

    public void update(Group group) {
	if (isUniqueGroupName(group)) {
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

    private boolean isUniqueGroupName(Group group) {
	Optional<Group> existingGroup = Optional
		.ofNullable(groupDao.findByNameAndYearId(group.getName(), group.getYear().getId()));
	return (existingGroup.isEmpty() || existingGroup.filter(g -> g.getId() == group.getId()).isPresent());
    }

}
