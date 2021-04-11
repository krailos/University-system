package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.GroupDao;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.exception.NotUniqueNameException;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.util.Paging;

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
	return groupDao.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(format("Group whith id=%s not exist", id)));
    }

    public List<Group> getAll() {
	log.debug("get all groups");
	return groupDao.findAll();
    }

    public List<Group> getByYearId(int yearId) {
	log.debug("get groups by yearId={}", yearId);
	return groupDao.findByYearId(yearId);
    }

    public void delete(Group group) {
	log.debug("delete group={}", group);
	groupDao.deleteById(group.getId());
    }

    private void checkGroupNameBeUnique(Group group) {
	Optional<Group> existingGroup = groupDao.findByNameAndYearId(group.getName(), group.getYear().getId());
	if (existingGroup.filter(a -> a.getId() != group.getId()).isPresent()) {
	    throw new NotUniqueNameException(
		    format("group name=%s and yearId=%s not unique", group.getName(), group.getYear().getId()));
	}
    }

    public int getQuantity() {
	log.debug("get group quantity");
	return groupDao.findQuantity();
    }

    public List<Group> getByPage(Paging paging) {
	log.debug("get groups by limit");
	return groupDao.findWithLimit(paging.getPageSize(), paging.getOffset());
    }

}
