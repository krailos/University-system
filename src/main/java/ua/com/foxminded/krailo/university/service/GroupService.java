package ua.com.foxminded.krailo.university.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.dao.GroupDao;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.exception.NotUniqueNameException;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Year;

@Transactional
@Service
public class GroupService {

    private static final Logger log = LoggerFactory.getLogger(GroupService.class);

    private GroupDao groupDao;

    public GroupService(GroupDao groupDao) {
	this.groupDao = groupDao;
    }

    public Group getById(int id) {
	log.debug("get group by id={}", id);
	return groupDao.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(String.format("Group whith id=%s not exist", id)));
    }

    public Page<Group> getSelectedPage(Pageable pageable) {
	log.debug("get group by page");
	return groupDao.findAll(pageable);
    }

    public List<Group> getAll() {
	log.debug("get all group");
	return (List<Group>) groupDao.findAll();
    }
    
    public void create(Group group) {
	log.debug("create group={}", group);
	checkGroupNameBeUnique(group);
	groupDao.save(group);
    }

    public List<Group> getByYear(Year year) {
	log.debug("get groups by yearId={}", year.getId());
	return groupDao.findByYear(year);
    }

    public void delete(Group group) {
	log.debug("delete group={}", group);
	groupDao.delete(group);
    }

    private void checkGroupNameBeUnique(Group group) {
	Optional<Group> existingGroup = groupDao.findByNameAndYear(group.getName(), group.getYear());
	if (existingGroup.filter(a -> a.getId() != group.getId()).isPresent()) {
	    throw new NotUniqueNameException(
		    String.format("group name=%s and yearId=%s not unique", group.getName(), group.getYear().getId()));
	}
    }

}
