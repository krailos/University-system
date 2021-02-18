package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.DepartmentDao;
import ua.com.foxminded.krailo.university.model.Department;

@Service
public class DepartmentService {

    private DepartmentDao departmentDao;

    public DepartmentService(DepartmentDao departmentDao) {
	this.departmentDao = departmentDao;
    }

    public void create(Department department) {
	departmentDao.create(department);
    }

    public void update(Department department) {
	departmentDao.update(department);
    }

    public Department getById(int id) {
	return departmentDao.findById(id);

    }

    public List<Department> getAll() {
	return departmentDao.findAll();

    }

    public void delete(Department department) {
	departmentDao.deleteById(department.getId());
    }

}
