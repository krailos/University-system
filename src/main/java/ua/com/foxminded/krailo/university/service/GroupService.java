package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.dao.interf.GroupDaoInt;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.exception.NotUniqueNameException;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Year;

@Transactional
@Service
public class GroupService {

    private static final Logger log = LoggerFactory.getLogger(GroupService.class);

    private GroupDaoInt groupDaoInt;

    public GroupService(GroupDaoInt groupDaoInt) {
	this.groupDaoInt = groupDaoInt;
    }

    public Group getById(int id) {
	log.debug("get group by id={}", id);
	return groupDaoInt.getById(id)
		.orElseThrow(() -> new EntityNotFoundException(format("Group whith id=%s not exist", id)));
    }

    public void create(Group group) {
	log.debug("create group={}", group);
	checkGroupNameBeUnique(group);
	groupDaoInt.create(group);
    }

    public void update(Group group) {
	log.debug("update group={}", group);
	checkGroupNameBeUnique(group);
	groupDaoInt.update(group);
    }

    public List<Group> getAll() {
	log.debug("get all groups");
	return groupDaoInt.getAll();
    }

    public List<Group> getByYear(Year year) {
	log.debug("get groups by yearId={}", year.getId());
	return groupDaoInt.getByYear(year);
    }

    public void delete(Group group) {
	log.debug("delete group={}", group);
	groupDaoInt.delete(group);
    }

    private void checkGroupNameBeUnique(Group group) {
	Optional<Group> existingGroup = groupDaoInt.getByNameAndYear(group.getName(), group.getYear());
	if (existingGroup.filter(a -> a.getId() != group.getId()).isPresent()) {
	    throw new NotUniqueNameException(
		    format("group name=%s and yearId=%s not unique", group.getName(), group.getYear().getId()));
	}
    }

    public int getQuantity() {
	log.debug("get group quantity");
	return groupDaoInt.count();
    }

    public Page<Group> getSelectedPage(Pageable pageable) {
	log.debug("get audiences by page");
	return new PageImpl<>(groupDaoInt.getAllByPage(pageable), pageable, groupDaoInt.count());
    }

}
