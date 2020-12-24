package ua.com.foxminded.krailo.university.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import ua.com.foxminded.krailo.university.entities.Audience;
import ua.com.foxminded.krailo.university.entities.Building;
import ua.com.foxminded.krailo.university.entities.Faculty;
import ua.com.foxminded.krailo.university.entities.Group;
import ua.com.foxminded.krailo.university.entities.Lesson;
import ua.com.foxminded.krailo.university.entities.LessonTime;
import ua.com.foxminded.krailo.university.entities.Speciality;
import ua.com.foxminded.krailo.university.entities.Student;
import ua.com.foxminded.krailo.university.entities.Subject;
import ua.com.foxminded.krailo.university.entities.Teacher;
import ua.com.foxminded.krailo.university.entities.Timetable;
import ua.com.foxminded.krailo.university.entities.Vocation;
import ua.com.foxminded.krailo.university.entities.Year;

public class FacultyAdmin {

    private Faculty faculty;
    private UniversityAdmin universityAdmin;

    public FacultyAdmin(Faculty faculty, UniversityAdmin universityAdmin) {
	this.faculty = faculty;
	this.universityAdmin = universityAdmin;
    }

    public Faculty getFaculty() {
	return faculty;
    }

    public UniversityAdmin getUniversityAdmin() {
	return universityAdmin;
    }

    public void setUniversityAdmin(UniversityAdmin universityAdmin) {
	this.universityAdmin = universityAdmin;
    }

    public Lesson createLesson(String dateOfLesson, String subjectName, String buildingId, String audienceName,
	    String lessonTimeNumber, String teacherId) {
	LocalDate date = getDateFromString(dateOfLesson);
	Subject subject = getSubjectByName(subjectName);
	Audience audience = getAudienceByNameAndBuildingId(buildingId, audienceName);
	LessonTime lessonTime = getLessonTimeByNumber(lessonTimeNumber);
	Teacher teacher = getTeacherById(teacherId);
	return new Lesson(date, subject, audience, lessonTime, teacher);
    }

    public LessonTime getLessonTimeByNumber(String lessonTimeNumber) {
	List<LessonTime> lessonTimes = faculty.getDeansOffice().getLessonsTimeSchedule().getLessonTimes().stream()
		.filter(l -> l.getLessonNumber().equals(lessonTimeNumber)).collect(Collectors.toList());
	if (lessonTimes.size() == 0) {
	    System.out.println("subjects with name " + lessonTimeNumber + "not exist");
	    return null;
	}
	return lessonTimes.get(0);
    }

    public Audience getAudienceByNameAndBuildingId(String buildingId, String audienceName) {
	Building building = universityAdmin.getBuildingById(buildingId);
	List<Audience> audiences = building.getAudiences().stream().filter(a -> a.getName().equals(audienceName))
		.collect(Collectors.toList());
	if (audiences.size() == 0) {
	    System.out.println("audience with name " + audienceName + "not exist");
	    return null;
	}
	return audiences.get(0);
    }

    public Subject getSubjectByName(String name) {
	List<Subject> subjects = faculty.getSpecialities().stream().flatMap(s -> s.getYears().stream())
		.flatMap(y -> y.getSubjects().stream()).filter(s -> s.getName().equals(name))
		.collect(Collectors.toList());
	if (subjects.size() == 0) {
	    System.out.println("subjects with name " + name + "not exist");
	    return null;
	}
	return subjects.get(0);
    }

    public Timetable getTimetable(String specialityId, String yearName) {
	List<Timetable> timetables = faculty.getDeansOffice().getTimetables().stream()
		.filter(t -> t.getSpeciality().getId().equals(specialityId) && t.getYear().getName().equals(yearName))
		.collect(Collectors.toList());
	if (timetables.size() == 0) {
	    System.out.println("there is any timetable");
	    return null;
	}
	return timetables.get(0);
    }

    public void showSpecialitiesId() {
	faculty.getSpecialities().stream().forEach(s -> System.out.print(s.getId() + "; "));
    }

    public void showYearsNameBySpeciality(String specialityId) {
	getSpecialityById(specialityId).getYears().stream().forEach(y -> System.out.print(y.getName() + "; "));
    }

    public void showGroupsNameByYearAndSpeciality(String yearName, String specialityId) {
	getYearByNameAndSpeciality(yearName, specialityId).getGroups().stream()
		.forEach(g -> System.out.print(g.getName() + "; "));
    }

    public void setFaculty(Faculty faculty) {
	this.faculty = faculty;
    }

    public Student getStudentsById(String id) {
	List<Student> studentsFiltered = faculty.getSpecialities().stream().flatMap(s -> s.getYears().stream())
		.flatMap(s -> s.getStudents().stream()).filter(s -> s.getId().equals(id)).collect(Collectors.toList());
	if (studentsFiltered.size() == 0) {
	    System.out.println("student id " + id + " not exist");
	    return null;
	}
	return studentsFiltered.get(0);
    }

    public Teacher getTeacherById(String id) {
	List<Teacher> teachers = faculty.getDepartments().stream().flatMap(d -> d.getTeachers().stream())
		.filter(t -> t.getId().equals(id)).collect(Collectors.toList());
	if (teachers.size() == 0) {
	    System.out.println("tacher id " + id + " not exist");
	    return null;
	}
	return teachers.get(0);
    }

    public String showStudentsAllFaculty() {
	return faculty.getSpecialities().stream().flatMap(s -> s.getYears().stream())
		.flatMap(s -> s.getStudents().stream()).map(s -> s.toString() + System.lineSeparator())
		.collect(Collectors.joining());
    }

    public Group getGroupByNameAndSpecialityAndYear(String groupName, String specialityId, String yearName) {
	Year year = getYearByNameAndSpeciality(yearName, specialityId);

	List<Group> groupFiltered = year.getGroups().stream().filter(g -> g.getName().equals(groupName))
		.collect(Collectors.toList());
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
	String allTimetables = faculty.getDeansOffice().getTimetables().stream().map(t -> this.showTimetable(t))
		.collect(Collectors.joining());
	sb.append(allTimetables).append(System.lineSeparator());
	return sb.toString();
    }

    public String showTimetable(Timetable timetable) {
	StringBuilder sb = new StringBuilder();
	sb.append(timetable.getName()).append(System.lineSeparator());
	sb.append("all lessons for ").append(timetable.getSpeciality()).append(" ").append(timetable.getYear())
		.append(System.lineSeparator());
	String pattern = "%-10s| %-12s| %-15s| %-20s";
	for (Lesson lesson : timetable.getLessons()) {
	    sb.append(String.format(pattern, lesson.getDate(), lesson.getAudience(), lesson.getSubject(),
		    lesson.getLessonTime()));
	    sb.append(System.lineSeparator());
	}
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
	sb.append(student.toString()).append(System.lineSeparator());
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
	Teacher teacher = getTeacherById(teacherId);
	StringBuilder sb = new StringBuilder();
	sb.append("schedule for teacher").append(System.lineSeparator());
	sb.append(teacher.toString()).append(System.lineSeparator());
	sb.append(teacher.getFirstName()).append(" ").append(teacher.getLastName()).append(System.lineSeparator());
	String pattern = "%-10s| %-12s| %-15s| %-20s";
	List<Lesson> lessonFiltered = faculty.getDeansOffice().getTimetables().stream()
		.flatMap(t -> t.getLessons().stream()).filter(l -> l.getDate().isAfter(start.minusDays(1))
			&& l.getDate().isBefore(end.plusDays(1)) && l.getTeacher().getId().equals(teacherId))
		.collect(Collectors.toList());
	for (Lesson lesson : lessonFiltered) {
	    sb.append(String.format(pattern, lesson.getDate(), lesson.getAudience(), lesson.getSubject(),
		    lesson.getLessonTime()));
	    sb.append(System.lineSeparator());
	}
	return sb.toString();
    }

    public Timetable getTimetableBySpecialityAndYear(String specialityId, String yearName) {
	List<Timetable> timetables = faculty.getDeansOffice().getTimetables().stream()
		.filter(t -> t.getSpeciality().getId().equals(specialityId) && t.getYear().getName().equals(yearName))
		.collect(Collectors.toList());
	if (timetables.size() == 0) {
	    System.out.println("timetable for speciality " + specialityId + " and year " + yearName + " not exist");
	    return null;
	}
	return timetables.get(0);
    }

    public String showVocationsByTeachersId(String teacherId, LocalDate start, LocalDate end) {
	Teacher teacher = getTeacherById(teacherId);
	StringBuilder sb = new StringBuilder();
	sb.append("Vocations").append(System.lineSeparator());
	sb.append(teacher.getFirstName()).append(" ").append(teacher.getLastName()).append(System.lineSeparator());
	sb.append("Period from " + start.toString()).append(" to " + end.toString()).append(System.lineSeparator());
	String pattern = "%-10s| %-12s| %-12s";
	List<Vocation> vocationFiltered = faculty.getDeansOffice().getVocations().stream()
		.filter(v -> v.getTeacher().equals(teacher) && v.getStartVocation().isAfter(start.minusDays(1))
			&& v.getStartVocation().isBefore(end.plusDays(1)))
		.collect(Collectors.toList());
	for (Vocation vocation : vocationFiltered) {
	    sb.append(
		    String.format(pattern, vocation.getKind(), vocation.getStartVocation(), vocation.getEndVocation()));
	    sb.append(System.lineSeparator());
	}
	return sb.toString();
    }

    public void createNewStudent(String id, String firstName, String lastName, String specialityId, String yearName) {
	Year year = getYearByNameAndSpeciality(yearName, specialityId);
	Student student = new Student(id, firstName, lastName, faculty, year.getSpeciality());
	year.getStudents().add(student);
    }

    public Year getYearByYearNameAndSpecialityId(String yearName, String spacialityId) {
	Speciality speciality = getSpecialityById(spacialityId);
	List<Year> yearFiltered = speciality.getYears().stream().filter(y -> y.getName().equals(yearName))
		.collect(Collectors.toList());
	if (yearFiltered.size() == 0) {
	    System.out.println("year with name " + yearName + " not exist");
	    return null;
	}
	return yearFiltered.get(0);
    }

    public void addStudentToGroop(Student student, String groupName, String yearName, String specialityId) {
	Group group = getGroupByNameAndSpecialityAndYear(groupName, specialityId, yearName);
	student.setGroup(group);
	group.getStudents().add(student);
    }

    public LocalDate getDateFromString(String date) {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
	return LocalDate.parse(date, formatter);

    }
}
