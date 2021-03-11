package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.GroupDao;
import ua.com.foxminded.krailo.university.exception.GroupNameNotUniqueException;
import ua.com.foxminded.krailo.university.exception.ServiceException;
import ua.com.foxminded.krailo.university.model.Group;

@Service
public class GroupService {

    private static final Logger log = LoggerFactory.getLogger(GroupService.class);
    private GroupDao groupDao;

    public GroupService(GroupDao groupDao) {
	this.groupDao = groupDao;
    }

    public void create(Group group) {
	log.debug("create group={}", group);
	checkGroupNameBeUnique(group);
	groupDao.create(group);
    }

    public void update(Group group) {
	log.debug("update group={}", group);
	checkGroupNameBeUnique(group);
	groupDao.update(group);
    }

    public Group getById(int id) {
	log.debug("get group by id={}", id);
	Optional<Group> existingGroup = groupDao.findById(id);
	if (existingGroup.isPresent()) {
	    return existingGroup.get();
	} else {
	    throw new ServiceException(format("group whith id=%s not exist", id));
	}
    }

    public List<Group> getAll() {
	log.debug("get all groups");
	return groupDao.findAll();
    }

    public void delete(Group group) {
	log.debug("delete group={}", group);
	groupDao.deleteById(group.getId());
    }

    private void checkGroupNameBeUnique(Group group) {
	log.debug("is group name unique ?");
	Optional<Group> existingGroup = groupDao.findByNameAndYearId(group.getName(), group.getYear().getId());
	if (existingGroup.isEmpty() || existingGroup.filter(a -> a.getId() == group.getId()).isPresent()) {
	    log.debug("group name is unique");
	} else {
	    throw new GroupNameNotUniqueException(
		    format("group name=%s and yearId=%s not unique", group.getName(), group.getYear().getId()));
	}
    }

}
