package ua.com.foxminded.krailo.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import ua.com.foxminded.krailo.entities.Faculty;
import ua.com.foxminded.krailo.entities.Group;
import ua.com.foxminded.krailo.entities.Lesson;
import ua.com.foxminded.krailo.entities.Speciality;
import ua.com.foxminded.krailo.entities.Student;
import ua.com.foxminded.krailo.entities.Teacher;
import ua.com.foxminded.krailo.entities.Timetable;
import ua.com.foxminded.krailo.entities.Vocation;
import ua.com.foxminded.krailo.entities.Year;

public class FacultyAdmin {

    Faculty faculty;

    public FacultyAdmin(Faculty faculty) {
	this.faculty = faculty;
    }

    public Student getStudentsById(String id) {
	List<Student> studentsFiltered = faculty.getSpecialities().stream().flatMap(s -> s.getYears().stream()).flatMap(s -> s.getStudents().stream())
		.filter(s -> s.getId().equals(id)).collect(Collectors.toList());
	if (studentsFiltered.size() == 0) {
	    System.out.println("student id " + id + " not exist");
	    return null;
	}
	return studentsFiltered.get(0);
    }

    public String showStudentsAllFaculty() {
	return faculty.getSpecialities().stream().flatMap(s -> s.getYears().stream()).flatMap(s -> s.getStudents().stream())
		.map(s -> s.toString() + System.lineSeparator()).collect(Collectors.joining());
    }

    public Group getGroupByNameAndSpecialityAndYear(String groupName, String specialityId, String yearName) {
	Year year = getYearByNameAndSpeciality(yearName, specialityId);
	
	List<Group> groupFiltered = year.getGroups().stream().filter(g -> g.getName().equals(groupName)).collect(Collectors.toList());
	if (groupFiltered.size() == 0) {
	    System.out.println("group with name " + groupName + " not exist");
	    return null;
	}
	return groupFiltered.get(0);
    }

    public Year getYearByNameAndSpeciality(String yearName, String specialityId) {
	Speciality speciallity = getSpecialityById(specialityId);
	List<Year> years = speciallity.getYears().stream().filter(y -> y.getName().equals(yearName))
		.collect(Collectors.toList());
	if (years.size() == 0) {
	    System.out.println("year with name " + yearName + " not exist");
	    return null;
	}
	return years.get(0);
    }

    public Speciality getSpecialityById(String id) {
	List<Speciality> specialityFiltered = faculty.getSpecialities().stream().filter(s -> s.getId().equals(id))
		.collect(Collectors.toList());
	if (specialityFiltered.size() == 0) {
	    System.out.println("speciality with id " + id + " not exist");
	    return null;
	}
	return specialityFiltered.get(0);
    }
    
    public String showLessonsFromAllTimetablesOfFaculty() {
	StringBuilder sb = new StringBuilder();
	sb.append(faculty.getName()).append(System.lineSeparator());
	String allTimetables = faculty.getDeansOffice().getTimetables().stream().map(t -> this.showTimetable(t)).collect(Collectors.joining());
	sb.append(allTimetables).append(System.lineSeparator());
	return sb.toString();
    }

    public String showTimeTableByStudentsId(String studentId, LocalDate start, LocalDate end) {
	Student student = getStudentsById(studentId);
	if (student == null) {
	    return "students id " + studentId + " not exist";
	}
	Timetable timetable = getTimetableBySpecialityAndYear(student.getSpeciality().getName(),
		student.getGroup().getYear().getName());
	StringBuilder sb = new StringBuilder();
	sb.append(timetable.getName()).append(System.lineSeparator());
	sb.append(student.getSpeciality()).append(" ").append(student.getGroup().getYear())
		.append(System.lineSeparator());
	String pattern = "%-10s| %-12s| %-15s| %-20s";
	List<Lesson> lessonFiltered = timetable
		.getLessons().stream().filter(l -> l.getDate().isAfter(start.minusDays(1))
			&& l.getDate().isBefore(end.plusDays(1)) && l.getGroups().contains(student.getGroup()))
		.collect(Collectors.toList());
	for (Lesson lesson : lessonFiltered) {
	    sb.append(String.format(pattern, lesson.getDate(), lesson.getAudience(), lesson.getSubject(),
		    lesson.getLessonTime()));
	    sb.append(System.lineSeparator());
	}
	return sb.toString();
    }

    public String showTimetableByTeachersId(String teacherId, LocalDate start, LocalDate end) {
	Teacher teacher = faculty.getDepartments().stream().flatMap(d -> d.getTeachers().stream())
		.filter(t -> t.getId().equals(teacherId)).collect(Collectors.toList()).get(0);
	if (teacher == null) {
	    return "teachers id " + teacherId + " not exist";
	}
	StringBuilder sb = new StringBuilder();
	sb.append(name).append(System.lineSeparator());
	sb.append(teacher.getFirstName()).append(" ").append(teacher.getLastName()).append(System.lineSeparator());
	String pattern = "%-10s| %-12s| %-15s| %-20s";
	List<Lesson> lessonFiltered = timetables
		.stream().flatMap(t -> t.getLessons().stream()).filter(l -> l.getDate().isAfter(start.minusDays(1))
			&& l.getDate().isBefore(end.plusDays(1)) && l.getTeacher().getId().equals(teacherId))
		.collect(Collectors.toList());
	for (Lesson lesson : lessonFiltered) {
	    sb.append(String.format(pattern, lesson.getDate(), lesson.getAudience(), lesson.getSubject(),
		    lesson.getLessonTime()));
	    sb.append(System.lineSeparator());
	}
	return sb.toString();
    }

    public Timetable getTimetableBySpecialityAndYear(String specialityName, String yearName) {
	Timetable timetable = faculty.getDeansOffice().getTimetables().stream().filter(
		t -> t.getSpeciality().getName().equals(specialityName) && t.getYear().getName().equals(yearName))
		.collect(Collectors.toList()).get(0);
	if (timetable == null) {
	    System.out.println("timetable for speciality " + specialityName + " and year " + yearName + " not exist");
	    return null;
	}
	return timetable;
    }

    public String showVocationsByTeachersId(String teacherId, LocalDate start, LocalDate end) {
	Teacher teacher = faculty.getDepartments().stream().flatMap(d -> d.getTeachers().stream())
		.filter(t -> t.getId().equals(teacherId)).collect(Collectors.toList()).get(0);
	if (teacher == null) {
	    return "teachers id " + teacherId + " not exist";
	}
	StringBuilder sb = new StringBuilder();
	sb.append("Vocations").append(System.lineSeparator());
	sb.append(teacher.getFirstName()).append(" ").append(teacher.getLastName()).append(System.lineSeparator());
	sb.append("Period from " + start.toString()).append(" to " + end.toString()).append(System.lineSeparator());
	String pattern = "%-10s| %-12s| %-12s";

	List<Vocation> vocationFiltered = vocations.stream().filter(v -> v.getTeacher().equals(teacher)
		&& v.getStartVocation().isAfter(start.minusDays(1)) && v.getStartVocation().isBefore(end.plusDays(1)))
		.collect(Collectors.toList());
	for (Vocation vocation : vocationFiltered) {
	    sb.append(
		    String.format(pattern, vocation.getKind(), vocation.getStartVocation(), vocation.getEndVocation()));
	    sb.append(System.lineSeparator());
	}
	return sb.toString();
    }
    
    public String showTimetable(Timetable timetable) {
   	StringBuilder sb = new StringBuilder();
   	sb.append(timetable.getName()).append(System.lineSeparator());
   	sb.append("all lessons for ").append(timetable.getSpeciality()).append(" ").append(timetable.getYear()).append(System.lineSeparator());
   	String pattern = "%-10s| %-12s| %-15s| %-20s";
   	for (Lesson lesson : timetable.getLessons()) {
   	    sb.append(String.format(pattern, lesson.getDate(), lesson.getAudience(), lesson.getSubject(),
   		    lesson.getLessonTime()));
   	    sb.append(System.lineSeparator());
   	}
   	return sb.toString();
       }

}
