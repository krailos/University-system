package ua.com.foxminded.krailo.university.main;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import ua.com.foxminded.krailo.university.domain.FacultyAdmin;
import ua.com.foxminded.krailo.university.domain.UniversityAdmin;
import ua.com.foxminded.krailo.university.entities.Audience;
import ua.com.foxminded.krailo.university.entities.Building;
import ua.com.foxminded.krailo.university.entities.DeansOffice;
import ua.com.foxminded.krailo.university.entities.Department;
import ua.com.foxminded.krailo.university.entities.Faculty;
import ua.com.foxminded.krailo.university.entities.Gender;
import ua.com.foxminded.krailo.university.entities.Group;
import ua.com.foxminded.krailo.university.entities.Holiday;
import ua.com.foxminded.krailo.university.entities.Lesson;
import ua.com.foxminded.krailo.university.entities.LessonTime;
import ua.com.foxminded.krailo.university.entities.LessonsTimeSchedule;
import ua.com.foxminded.krailo.university.entities.Speciality;
import ua.com.foxminded.krailo.university.entities.Student;
import ua.com.foxminded.krailo.university.entities.Subject;
import ua.com.foxminded.krailo.university.entities.Teacher;
import ua.com.foxminded.krailo.university.entities.Timetable;
import ua.com.foxminded.krailo.university.entities.UniversityOffice;
import ua.com.foxminded.krailo.university.entities.Vocation;
import ua.com.foxminded.krailo.university.entities.Year;

public class Main {

    UniversityOffice universityOffice = new UniversityOffice("KNEU");

    public static void main(String[] args) {

	Main app = new Main();
	app.fillExampleData();
	System.out.println("example data for faculty with id=61 is loaded");
	UniversityAdmin universityAdmin = new UniversityAdmin(app.universityOffice);
	FacultyAdmin facultyAdmin = new FacultyAdmin(universityAdmin.getFacultyById("61"), universityAdmin);

	Scanner scanner = new Scanner(System.in);
	boolean exit = false;
	while (exit != true) {
	    System.out.println("application university office");
	    System.out.println("choose operations");
	    System.out.println("-- 1. show time table for all faculty press -- 1");
	    System.out.println("-- 2. show time table for student using students id press -- 2");
	    System.out.println("-- 3. show time table for teacher using teachers id press -- 3");
	    System.out.println("-- 4. show holiday for university press-- 4");
	    System.out.println("-- 5. show vocations for teacher using teachers id press-- 5");
	    System.out.println("-- 6. create student press-- 6");
	    System.out.println("-- 7. edit student press-- 7");
	    System.out.println("-- 8. delete student press-- 8");
	    System.out.println("-- 9. add lesson to timetable -- 9");
	    System.out.println("-- 0. to exit press -- 0");
	    int userInput = scanner.nextInt();
	    switch (userInput) {
	    case 1:
		facultyAdmin.showLessonsFromAllTimetablesOfFaculty();
		break;
	    case 2:
		app.showTimetableForStudentByStudentsId(scanner, facultyAdmin);
		break;
	    case 3:
		app.showTimetableForTeacherByTeachersId(scanner, facultyAdmin);
		break;
	    case 4:
		System.out.println(universityAdmin.showHolidays());
		break;
	    case 5:
		app.showVocationsForTeacherByTeachersId(scanner, facultyAdmin);
		break;
	    case 6:
		app.createStudent(scanner, facultyAdmin);
		break;
	    case 7:
		app.editSudent(scanner, facultyAdmin);
		break;
	    case 8:
		app.deleteStudent(scanner, facultyAdmin);
		break;
	    case 9:
		app.addLessonForTimetable(scanner, facultyAdmin);
		break;
	    case 0:
		exit = true;
		break;
	    default:
		System.out.println("any operation choosen");
		break;
	    }
	}

    }
    
    private void addLessonForTimetable(Scanner scanner, FacultyAdmin facultyAdmin) {
	System.out.println("enter speciality id, from available: ");
	facultyAdmin.showSpecialitiesId();
	String specialityId = scanner.next();
	System.out.println("enter year name, from available:");
	facultyAdmin.showYearsNameBySpeciality(specialityId);
	String yearName = scanner.next();
	Timetable timetable = facultyAdmin.getTimetableBySpecialityAndYear(specialityId, yearName);
	boolean exitAddlessons = false;
	while (exitAddlessons != true) {
	    System.out.println("add lessons menu");
	    System.out.println(" -- show timetable press -- 1");
	    System.out.println(" -- add lesson press -- 2");
	    System.out.println(" -- exit press -- 0");
	    int addLessonOperation = scanner.nextInt();
	    switch (addLessonOperation) {
	    case 1:
		System.out.println(facultyAdmin.showTimetable(timetable));
		break;
	    case 2:
		addLesson(scanner, facultyAdmin, specialityId, yearName, timetable);
		break;
	    case 0:
		exitAddlessons=true;
		break;
	    default:
		System.out.println("any operation choosen");
		break;
	    }
	}
    }

    private void addLesson (Scanner scanner, FacultyAdmin facultyAdmin, String specialityId, String yearName, Timetable timetable ) {
	System.out.println("enter date use folowing date formatt d/mm/yyyy");
	String dateOfLesson = scanner.next();
	System.out.println("enter subject name");
	facultyAdmin.showSubjectbySpecialityIdAndYearName(specialityId, yearName);
	String subjectName = scanner.next();
	System.out.println("enter building id");
	String buildingId = scanner.next();
	System.out.println("enter audience name");
	String audienceName = scanner.next();
	System.out.println("enter lesson number");
	String lesonNumber = scanner.next();
	System.out.println("enter teacher id");
	String teacherId = scanner.next();
	Lesson lesson = facultyAdmin.createLesson(dateOfLesson, subjectName, buildingId, audienceName, lesonNumber, teacherId);
	System.out.println("enter group name, from available:");
	facultyAdmin.showGroupsNameByYearAndSpeciality(yearName, specialityId);
	String groupName = scanner.next();
	Group group = facultyAdmin.getGroupByNameAndSpecialityAndYear(groupName, specialityId, yearName);
	facultyAdmin.addGroupToLesson(group, lesson);
	timetable.getLessons().add(lesson);
    }
    
    private void deleteStudent(Scanner scanner, FacultyAdmin facultyAdmin) {
	System.out.println("enter student id");
	String studentsId = scanner.next();
	Student student = facultyAdmin.getStudentsById(studentsId);
	student.getGroup().getStudents().remove(student);
	student.getGroup().getYear().getStudents().remove(student);
	System.out.println("student " + student.getId() + " was deleted");
    }

    private void editSudent(Scanner scanner, FacultyAdmin facultyAdmin) {
	boolean exitEditStudent = false;
	while (exitEditStudent != true) {
	    System.out.println("edit student");
	    System.out.println(" -- for editing student enter student id");
	    System.out.println(" -- for exit enter 0");
	    System.out.println("available students id");
	    facultyAdmin.getFaculty().getSpecialities().stream().flatMap(s -> s.getYears().stream())
		    .flatMap(y -> y.getStudents().stream()).forEach(s -> System.out.print(s.getId() + "; "));
	    String studentsId = scanner.next();
	    if (studentsId.equals(0)) {
		break;
	    }
	    Student student = facultyAdmin.getStudentsById(studentsId);
	    if (student == null) {
		break;
	    }
	    System.out.println("choose operations");
	    System.out.println("	- 1. add group press - 1");
	    System.out.println("	- 2. add birthDate press - 2");
	    System.out.println("	- 3. add phone press - 3");
	    System.out.println("	- 4. add address press - 4");
	    System.out.println("	- 5. add email press - 5");
	    System.out.println("	- 6. add rank press - 6");
	    System.out.println("	- 0. to exit press - 0");
	    int editStudentOperation = scanner.nextInt();
	    switch (editStudentOperation) {
	    case 1:
		addStudentToGroup(scanner, facultyAdmin, student);
		break;
	    case 2:
		System.out.println("enter students birtDate use folowing date formatt d/mm/yyyy");
		String studentsBirthDate = scanner.next();
		student.setBirthDate(facultyAdmin.getDateFromString(studentsBirthDate));
		break;
	    case 3:
		System.out.println("enter phone number");
		String pnoneNumber = scanner.next();
		student.setPhone(pnoneNumber);
		break;
	    case 4:
		System.out.println("enter address");
		String address = scanner.next();
		student.setAddress(address);
		break;
	    case 5:
		System.out.println("enter email");
		String email = scanner.next();
		student.setEmail(email);
		break;
	    case 6:
		System.out.println("enter rank");
		Double rank = scanner.nextDouble();
		student.setRank(rank);
		break;
	    case 0:
		exitEditStudent = true;
		break;
	    default:
		System.out.println("any operation choosen");
		break;
	    }
	}
    }

    private void createStudent(Scanner scanner, FacultyAdmin facultyAdmin) {
	System.out.println("enter student id");
	String studentsId = scanner.next();
	System.out.println("enter student first name");
	String studentsFirstName = scanner.next();
	System.out.println("enter student last name");
	String studentsLastName = scanner.next();
	System.out.println("enter speciality id");
	String specialityId = scanner.next();
	System.out.println("enter year name");
	String yearName = scanner.next();
	facultyAdmin.createNewStudent(studentsId, studentsFirstName, studentsLastName, specialityId, yearName);
	System.out.println("student " + facultyAdmin.getStudentsById(studentsId) + " was created");
    }
    

    
    private void addStudentToGroup(Scanner scanner, FacultyAdmin facultyAdmin, Student student) {
	System.out.println("eneter speciality id");
	System.out.println("available specialities Id ");
	facultyAdmin.showSpecialitiesId();
	String specialityId = scanner.next();
	System.out.println("enter year name");
	System.out.println("available year name");
	facultyAdmin.showYearsNameBySpeciality(specialityId);
	String yearName = scanner.next();
	System.out.println("enter group name");
	System.out.println("available group name");
	facultyAdmin.showGroupsNameByYearAndSpeciality(yearName, specialityId);
	String groupsName = scanner.next();
	facultyAdmin.addStudentToGroop(student, groupsName, yearName, specialityId);
	System.out.println("student was added to group " + student.getGroup().getName());
    }

    private void showVocationsForTeacherByTeachersId(Scanner scanner, FacultyAdmin facultyAdmin) {
	System.out.println("please enter teachers id");
	String teachersId = scanner.next();
	System.out.println("enter start of period use folowing date formatt d/mm/yyyy ");
	String startDate = scanner.next();
	System.out.println("enter end of period use folowing formatt d/mm/yyyy ");
	String endDate = scanner.next();
	System.out.println(facultyAdmin.showVocationsByTeachersId(teachersId, facultyAdmin.getDateFromString(startDate),
		facultyAdmin.getDateFromString(endDate)));
    }

    private void showTimetableForStudentByStudentsId(Scanner scanner, FacultyAdmin facultyAdmin) {
	System.out.println("please enter students id");
	String studentsId = scanner.next();
	System.out.println("enter start of period use folowing date formatt d/mm/yyyy ");
	String startDate = scanner.next();
	System.out.println("enter end of period use folowing formatt d/mm/yyyy ");
	String endDate = scanner.next();
	System.out.println(facultyAdmin.showTimeTableByStudentsId(studentsId, facultyAdmin.getDateFromString(startDate),
		facultyAdmin.getDateFromString(endDate)));
    }

    private void showTimetableForTeacherByTeachersId(Scanner scanner, FacultyAdmin facultyAdmin) {
	System.out.println("please enter teachers id");
	String teachersId = scanner.next();
	System.out.println("enter start of period use folowing date formatt d/mm/yyyy ");
	String startDate = scanner.next();
	System.out.println("enter end of period use folowing formatt d/mm/yyyy ");
	String endDate = scanner.next();
	System.out.println(facultyAdmin.showTimetableByTeachersId(teachersId, facultyAdmin.getDateFromString(startDate),
		facultyAdmin.getDateFromString(endDate)));
    }

    private void fillExampleData() {
	Faculty facultyOfFinance = new Faculty("61", "Faculty of Finance and Economy", universityOffice);
	universityOffice.setFaculties(new ArrayList<>());
	universityOffice.getFaculties().add(facultyOfFinance);
	Department department = new Department("Department of Financial faculty");
	facultyOfFinance.setDepartments(new ArrayList<>());
	facultyOfFinance.getDepartments().add(department);
	DeansOffice deansOffice = new DeansOffice("Deans office of Financial faculty", facultyOfFinance,
		universityOffice);
	facultyOfFinance.setDeansOffice(deansOffice);
	Speciality specialityFinance = new Speciality("6104", "Finance", facultyOfFinance);
	Speciality specialityBanking = new Speciality("6105", "Banking", facultyOfFinance);
	facultyOfFinance.setSpecialities(new ArrayList<>());
	facultyOfFinance.getSpecialities().add(specialityFinance);
	facultyOfFinance.getSpecialities().add(specialityBanking);
	Year year1Finance = new Year("1year", specialityFinance);
	specialityFinance.setYears(new ArrayList<>());
	specialityFinance.getYears().add(year1Finance);
	Year year1Banking = new Year("1year", specialityBanking);
	specialityBanking.setYears(new ArrayList<>());
	specialityBanking.getYears().add(year1Banking);
	Group group1FinanceYear1 = new Group("1", year1Finance, specialityFinance);
	Group group2FinanceYear1 = new Group("2", year1Finance, specialityFinance);
	year1Finance.setGroups(new ArrayList<>());
	year1Finance.getGroups().add(group1FinanceYear1);
	year1Finance.getGroups().add(group2FinanceYear1);
	Group group1BankingYear1 = new Group("1", year1Banking, specialityBanking);
	Group group2BankingYear1 = new Group("2", year1Banking, specialityFinance);
	year1Banking.setGroups(new ArrayList<>());
	year1Banking.getGroups().add(group1BankingYear1);
	year1Banking.getGroups().add(group2BankingYear1);
	Student student1FinanceYear1Group1 = new Student("6104_1", "Name1", "LastName1", facultyOfFinance,
		specialityFinance);
	student1FinanceYear1Group1.setGender(Gender.MALE);
	year1Finance.setStudents(new ArrayList<>());
	year1Finance.getStudents().add(student1FinanceYear1Group1);
	group1FinanceYear1.setStudents(new ArrayList<>());
	group1FinanceYear1.addStudentToGroup(student1FinanceYear1Group1);
	Student student2FinanceYear1Group2 = new Student("6104_2", "Name2", "LastName2", facultyOfFinance,
		specialityFinance);
	student2FinanceYear1Group2.setGender(Gender.MALE);
	year1Finance.getStudents().add(student2FinanceYear1Group2);
	group2FinanceYear1.setStudents(new ArrayList<>());
	group2FinanceYear1.addStudentToGroup(student2FinanceYear1Group2);
	Student student1BankingYear1Group1 = new Student("6105_1", "Name3", "LastName3", facultyOfFinance,
		specialityBanking);
	student1BankingYear1Group1.setGender(Gender.MALE);
	year1Banking.setStudents(new ArrayList<>());
	year1Banking.getStudents().add(student1BankingYear1Group1);
	group1BankingYear1.setStudents(new ArrayList<>());
	group1BankingYear1.addStudentToGroup(student1BankingYear1Group1);
	Student student2BankingYear1Group2 = new Student("6105_2", "Name4", "LastName4", facultyOfFinance,
		specialityBanking);
	student2BankingYear1Group2.setGender(Gender.MALE);
	year1Banking.getStudents().add(student2BankingYear1Group2);
	group2BankingYear1.setStudents(new ArrayList<>());
	group2BankingYear1.addStudentToGroup(student2BankingYear1Group2);
	Building building = new Building("1", "Main building");
	universityOffice.setBuildings(new ArrayList<>());
	universityOffice.getBuildings().add(building);
	Audience audience1 = new Audience("1", building);
	Audience audience2 = new Audience("2", building);
	Audience audience3 = new Audience("3", building);
	Audience audience4 = new Audience("4", building);
	building.setAudiences(new ArrayList<>());
	building.getAudiences().add(audience1);
	building.getAudiences().add(audience2);
	building.getAudiences().add(audience3);
	building.getAudiences().add(audience4);
	Teacher teacher1 = new Teacher("t1", "Teacher", "One");
	Teacher teacher2 = new Teacher("t2", "Teacher", "Two");
	Teacher teacher3 = new Teacher("t3", "Teacher", "Three");
	Teacher teacher4 = new Teacher("t4", "Teacher", "Four");
	department.setTeachers(new ArrayList<>());
	department.getTeachers().add(teacher1);
	department.getTeachers().add(teacher2);
	department.getTeachers().add(teacher3);
	department.getTeachers().add(teacher4);
	Subject subject1Finance = new Subject("Economy");
	Subject subject2Finance = new Subject("Meth");
	Subject subject1Banking = new Subject("Banking Sysytem");
	Subject subject2Banking = new Subject("Riscs");
	year1Finance.setSubjects(new ArrayList<>());
	year1Banking.setSubjects(new ArrayList<>());
	year1Finance.getSubjects().add(subject1Finance);
	year1Finance.getSubjects().add(subject2Finance);
	year1Banking.getSubjects().add(subject1Banking);
	year1Banking.getSubjects().add(subject2Banking);
	subject1Finance.setTeachers(new ArrayList<>());
	subject2Finance.setTeachers(new ArrayList<>());
	subject1Banking.setTeachers(new ArrayList<>());
	subject2Banking.setTeachers(new ArrayList<>());
	subject1Finance.getTeachers().add(teacher1);
	subject2Finance.getTeachers().add(teacher2);
	subject1Banking.getTeachers().add(teacher3);
	subject2Banking.getTeachers().add(teacher4);
	LessonsTimeSchedule lessonsTimeSchedule = new LessonsTimeSchedule("lessons time schedule");
	deansOffice.setLessonsTimeSchedule(lessonsTimeSchedule);
	LessonTime lessonTime1 = new LessonTime("1", LocalTime.of(8, 30), LocalTime.of(9, 15));
	LessonTime lessonTime2 = new LessonTime("2", LocalTime.of(9, 30), LocalTime.of(10, 15));
	lessonsTimeSchedule.setLessonTimes(new ArrayList<>());
	lessonsTimeSchedule.getLessonTimes().add(lessonTime1);
	lessonsTimeSchedule.getLessonTimes().add(lessonTime2);
	Lesson lesson1FinanceYear1Date01 = new Lesson(LocalDate.of(2020, 12, 1), subject1Finance, audience1,
		lessonTime1, teacher1);
	Lesson lesson2FinanceYear1Date01 = new Lesson(LocalDate.of(2020, 12, 1), subject2Finance, audience1,
		lessonTime2, teacher2);
	Lesson lesson1FinanceYear1Date02 = new Lesson(LocalDate.of(2020, 12, 2), subject1Finance, audience1,
		lessonTime1, teacher1);
	Lesson lesson2FinanceYear1Date02 = new Lesson(LocalDate.of(2020, 12, 2), subject2Finance, audience1,
		lessonTime2, teacher2);
	Lesson lesson1FinanceYear1Date03 = new Lesson(LocalDate.of(2020, 12, 3), subject1Finance, audience1,
		lessonTime1, teacher1);
	Lesson lesson2FinanceYear1Date03 = new Lesson(LocalDate.of(2020, 12, 3), subject2Finance, audience1,
		lessonTime2, teacher2);
	Lesson lesson1BankingYear1Date01 = new Lesson(LocalDate.of(2020, 12, 1), subject1Banking, audience2,
		lessonTime1, teacher3);
	Lesson lesson2BankingYear1Date01 = new Lesson(LocalDate.of(2020, 12, 1), subject2Banking, audience2,
		lessonTime2, teacher4);
	Lesson lesson1BankingYear1Date02 = new Lesson(LocalDate.of(2020, 12, 2), subject1Banking, audience2,
		lessonTime1, teacher3);
	Lesson lesson2BankingYear1Date02 = new Lesson(LocalDate.of(2020, 12, 2), subject2Banking, audience2,
		lessonTime2, teacher4);
	Lesson lesson1BankingYear1Date03 = new Lesson(LocalDate.of(2020, 12, 3), subject1Banking, audience2,
		lessonTime1, teacher3);
	Lesson lesson2BankingYear1Date03 = new Lesson(LocalDate.of(2020, 12, 3), subject2Banking, audience2,
		lessonTime2, teacher4);
	lesson1FinanceYear1Date01.setGroups(new ArrayList<>());
	lesson2FinanceYear1Date01.setGroups(new ArrayList<>());
	lesson1FinanceYear1Date02.setGroups(new ArrayList<>());
	lesson2FinanceYear1Date02.setGroups(new ArrayList<>());
	lesson1FinanceYear1Date03.setGroups(new ArrayList<>());
	lesson2FinanceYear1Date03.setGroups(new ArrayList<>());
	lesson1BankingYear1Date01.setGroups(new ArrayList<>());
	lesson2BankingYear1Date01.setGroups(new ArrayList<>());
	lesson1BankingYear1Date02.setGroups(new ArrayList<>());
	lesson2BankingYear1Date02.setGroups(new ArrayList<>());
	lesson1BankingYear1Date03.setGroups(new ArrayList<>());
	lesson2BankingYear1Date03.setGroups(new ArrayList<>());
	lesson1FinanceYear1Date01.getGroups().add(group1FinanceYear1);
	lesson1FinanceYear1Date01.getGroups().add(group2FinanceYear1);
	lesson2FinanceYear1Date01.getGroups().add(group1FinanceYear1);
	lesson2FinanceYear1Date01.getGroups().add(group2FinanceYear1);
	lesson1FinanceYear1Date02.getGroups().add(group1FinanceYear1);
	lesson1FinanceYear1Date02.getGroups().add(group2FinanceYear1);
	lesson2FinanceYear1Date02.getGroups().add(group1FinanceYear1);
	lesson2FinanceYear1Date02.getGroups().add(group2FinanceYear1);
	lesson1FinanceYear1Date03.getGroups().add(group1FinanceYear1);
	lesson1FinanceYear1Date03.getGroups().add(group2FinanceYear1);
	lesson2FinanceYear1Date03.getGroups().add(group1FinanceYear1);
	lesson2FinanceYear1Date03.getGroups().add(group2FinanceYear1);
	lesson1BankingYear1Date01.getGroups().add(group1BankingYear1);
	lesson1BankingYear1Date01.getGroups().add(group2BankingYear1);
	lesson2BankingYear1Date01.getGroups().add(group1BankingYear1);
	lesson2BankingYear1Date01.getGroups().add(group2BankingYear1);
	lesson1BankingYear1Date02.getGroups().add(group1BankingYear1);
	lesson1BankingYear1Date02.getGroups().add(group2BankingYear1);
	lesson2BankingYear1Date02.getGroups().add(group1BankingYear1);
	lesson2BankingYear1Date02.getGroups().add(group2BankingYear1);
	lesson1BankingYear1Date03.getGroups().add(group1BankingYear1);
	lesson1BankingYear1Date03.getGroups().add(group2BankingYear1);
	lesson2BankingYear1Date03.getGroups().add(group1BankingYear1);
	lesson2BankingYear1Date03.getGroups().add(group2BankingYear1);
	Timetable timetableFinanceYear1 = new Timetable("Timetable finance year 1", specialityFinance, year1Finance);
	Timetable timetableBankingYear1 = new Timetable("Timetable banking year 1", specialityBanking, year1Banking);
	deansOffice.setTimetables(new ArrayList<>());
	deansOffice.getTimetables().add(timetableFinanceYear1);
	deansOffice.getTimetables().add(timetableBankingYear1);
	timetableFinanceYear1.setLessons(new ArrayList<>());
	timetableBankingYear1.setLessons(new ArrayList<>());
	timetableFinanceYear1.getLessons().add(lesson1FinanceYear1Date01);
	timetableFinanceYear1.getLessons().add(lesson2FinanceYear1Date01);
	timetableFinanceYear1.getLessons().add(lesson1FinanceYear1Date02);
	timetableFinanceYear1.getLessons().add(lesson2FinanceYear1Date02);
	timetableFinanceYear1.getLessons().add(lesson1FinanceYear1Date03);
	timetableFinanceYear1.getLessons().add(lesson2FinanceYear1Date03);
	timetableBankingYear1.getLessons().add(lesson1BankingYear1Date01);
	timetableBankingYear1.getLessons().add(lesson2BankingYear1Date01);
	timetableBankingYear1.getLessons().add(lesson1BankingYear1Date02);
	timetableBankingYear1.getLessons().add(lesson2BankingYear1Date02);
	timetableBankingYear1.getLessons().add(lesson1BankingYear1Date03);
	timetableBankingYear1.getLessons().add(lesson2BankingYear1Date03);
	Vocation vocation = new Vocation("yearly", LocalDate.of(2020, 1, 1), teacher1, LocalDate.of(2020, 1, 1),
		LocalDate.of(2020, 1, 15));
	deansOffice.setVocations(new ArrayList<>());
	deansOffice.getVocations().add(vocation);
	Holiday holidayNewYear = new Holiday("New year", LocalDate.of(2020, 1, 1));
	Holiday holidayIndependantDay = new Holiday("Independent Day", LocalDate.of(2020, 8, 24));
	universityOffice.setHolidays(new ArrayList<>());
	universityOffice.getHolidays().add(holidayNewYear);
	universityOffice.getHolidays().add(holidayIndependantDay);
    }

}