package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.DepartmentDao;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.model.Department;

@Service
public class DepartmentService {

    private static final Logger log = LoggerFactory.getLogger(DepartmentService.class);

    private DepartmentDao departmentDao;

    public DepartmentService(DepartmentDao departmentDao) {
	this.departmentDao = departmentDao;
    }

    public void create(Department department) {
	log.debug("Create department={}", department);
	departmentDao.create(department);
    }

    public void update(Department department) {
	log.debug("Update department={}", department);
	departmentDao.update(department);
    }

    public Department getById(int id) {
	log.debug("Get department by id={}", id);
	return departmentDao.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(format("Department whith id=%s not exist", id)));
    }

    public List<Department> getAll() {
	log.debug("Get all departments");
	return departmentDao.findAll();
    }

    public void delete(Department department) {
	log.debug("Delete department={}", department);
	departmentDao.deleteById(department.getId());
    }

}
