package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.DepartmentDao;
import ua.com.foxminded.krailo.university.dao.TeacherDao;
import ua.com.foxminded.krailo.university.model.Department;

@Service
public class DepartmentService {

    private DepartmentDao departmentDao;
    private TeacherDao teacherDao;

    public DepartmentService(DepartmentDao departmentDao, TeacherDao teacherDao) {
	this.departmentDao = departmentDao;
	this.teacherDao = teacherDao;
    }

    public void create(Department department) {
	departmentDao.create(department);
    }

    public void update(Department department) {
	departmentDao.update(department);
    }

    public Department getById(int id) {
	Department department = departmentDao.findById(id);
	addTechersToDepaetment(department);
	return department;
    }

    public List<Department> getAll() {
	List<Department> departments = departmentDao.findAll();
	for (Department department : departments) {
	    addTechersToDepaetment(department);
	}
	return departments;
    }

    public void delete(Department department) {
	departmentDao.deleteById(department.getId());
    }

    private void addTechersToDepaetment(Department department) {
	department.setTeachers(teacherDao.findByDepartmentId(department.getId()));
    }

}
