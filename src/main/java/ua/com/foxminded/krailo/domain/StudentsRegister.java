package ua.com.foxminded.krailo.domain;

import java.util.List;
import java.util.stream.Collectors;

public class StudentsRegister {

    private String name;
    private List<Student> students;

    public StudentsRegister(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public List<Student> getStudents() {
	return students;
    }

    public void setStudents(List<Student> students) {
	this.students = students;
    }

    public Student getById(String id) {
	List<Student> studentsFiltered = students.stream().filter(s -> s.getId().equals(id))
		.collect(Collectors.toList());
	if (studentsFiltered.size() == 0) {
	    System.out.println("student id " + id + "not exist");
	    return null;
	}
	return studentsFiltered.get(0);
    }

    public String showAllStudents() {
	return students.stream().map(s -> s.toString() + System.lineSeparator()).collect(Collectors.joining());
    }

}
