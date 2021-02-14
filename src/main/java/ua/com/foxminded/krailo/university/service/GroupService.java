package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.GroupDao;
import ua.com.foxminded.krailo.university.dao.StudentDao;
import ua.com.foxminded.krailo.university.model.Group;

@Service
public class GroupService {

    private GroupDao groupDao;
    private StudentDao studentDao;

    public GroupService(GroupDao groupDao, StudentDao studentDao) {
	this.groupDao = groupDao;
	this.studentDao = studentDao;
    }

    public void create(Group group) {
	groupDao.create(group);
    }

    public void update(Group group) {
	groupDao.update(group);
    }

    public Group getById(int id) {
	Group group = groupDao.findById(id);
	addStudentsToGroup(group);
	return group;
    }

    public List<Group> getAll() {
	List<Group> groups = groupDao.findAll();
	for (Group group : groups) {
	    addStudentsToGroup(group);
	}
	return groups;
    }

    public void delete(Group group) {
	groupDao.deleteById(group.getId());
    }

    private void addStudentsToGroup(Group group) {
	group.setStudents(studentDao.findByGroupId(group.getId()));
    }

}
