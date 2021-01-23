package ua.com.foxminded.krailo.university.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.Department;

@Repository
public class DepartmentDao {

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM departments    WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM departments  ";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM departments   WHERE id = ?";
    private static final String SQL_INSERT_DEPARTMENT = "INSERT INTO departments (name) VALUES (?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE departments  SET name = ? where id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Department> departmentRowMapper;

    public DepartmentDao(JdbcTemplate jdbcTemplate, RowMapper<Department> departmentRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.departmentRowMapper = departmentRowMapper;
    }

    public void create(Department department) {
	jdbcTemplate.update(SQL_INSERT_DEPARTMENT, department.getName());

    }

    public void update(Department department) {
	jdbcTemplate.update(SQL_UPDATE_BY_ID, department.getName(), department.getId());

    }

    public Department findById(int id) {
	return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, departmentRowMapper, id);
    }

    public List<Department> findAll() {
	return jdbcTemplate.query(SQL_SELECT_ALL, departmentRowMapper);
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_BY_ID, id);

    }

}
