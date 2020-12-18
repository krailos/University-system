package ua.com.foxminded.krailo.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    UniversityOffice universityOffice = new UniversityOffice("KNEU");
    Faculty facultyOfFinance = new Faculty("61", "Faculty of Finance and Economy");
    Department department = new Department("Department of Financial faculty");
    DeansOffice deansOffice = new DeansOffice("Deans office of Financial faculty", facultyOfFinance, universityOffice);
    Speciality specialityFinance = new Speciality("6104", "Finance", facultyOfFinance);
    Speciality specialityBanking = new Speciality("6105", "Banking", facultyOfFinance);
    Year year1Finance = new Year("1 year", specialityFinance);
    Year year1Banking = new Year("1 year", specialityBanking);
    Group group1FinanceYear1 = new Group("1", year1Finance, specialityFinance);
    Group group2FinanceYear1 = new Group("2", year1Finance, specialityFinance);
    Group group1BankingYear1 = new Group("1", year1Banking, specialityBanking);
    Group group2BankingYear1 = new Group("2", year1Banking, specialityFinance);
    Building building = new Building("Main building");
    Audience audience1 = new Audience("audience 1");
    Audience audience2 = new Audience("audience 2");
    Audience audience3 = new Audience("audience 3");
    Audience audience4 = new Audience("audience 4");
    LessonsTimeSchedule lessonsTimeSchedule = new LessonsTimeSchedule("lessons time schedule");
    StudentsRegister studentsRegister = new StudentsRegister("register of all universities students");

    public static void main(String[] args) {

	Main app = new Main();
	app.fillExampleData();
	System.out.println("data loaded");
	Scanner scanner = new Scanner(System.in);
	boolean exit = false;
	while (exit != true) {
	    System.out.println("application university office");
	    System.out.println("choose operations");
	    System.out.println("-- 1. Show time table for all faculty press -- 1");
	    System.out.println("-- 2. Show time table for student using students id press -- 2");
	    System.out.println("-- 3. Show time table for teacher using teachers id press -- 3");
	    System.out.println("-- 4. Show holiday for university press-- 4");
	    System.out.println("-- 5. Show vocations for teacher using teachers id press-- 5");
	    System.out.println("-- 6. create student press-- 6");
	    System.out.println("-- 10. Exit press -- 10");
	    int userInput = scanner.nextInt();
	    switch (userInput) {
	    case 1:
		System.out.println(app.deansOffice.showLessonsFromAllTimetablesOfFaculty());
		break;
	    case 2:
		System.out.println("please enter students id");
		String studentsId = scanner.next();
		System.out.println("enter start of period use folowing date formatt d/mm/yyyy ");
		String startDate = scanner.next();
		System.out.println("enter end of period use folowing formatt d/mm/yyyy ");
		String endDate = scanner.next();
		System.out.println(app.deansOffice.showTimeTableByStudentsId(studentsId, getDateFromString(startDate),
			getDateFromString(endDate)));
		break;
	    case 3:
		System.out.println("please enter teachers id");
		String teachersId = scanner.next();
		System.out.println("enter start of period use folowing date formatt d/mm/yyyy ");
		startDate = scanner.next();
		System.out.println("enter end of period use folowing formatt d/mm/yyyy ");
		endDate = scanner.next();
		System.out.println(app.deansOffice.showTimetableByTeachersId(teachersId, getDateFromString(startDate),
			getDateFromString(endDate)));
		break;
	    case 4:
		System.out.println(app.universityOffice.showHolidays());
		break;
	    case 5:
		System.out.println("please enter teachers id");
		teachersId = scanner.next();
		System.out.println("enter start of period use folowing date formatt d/mm/yyyy ");
		startDate = scanner.next();
		System.out.println("enter end of period use folowing formatt d/mm/yyyy ");
		endDate = scanner.next();
		System.out.println(app.deansOffice.showVocationsByTeachersId(teachersId, getDateFromString(startDate),
			getDateFromString(endDate)));
		break;
	    case 6:
		System.out.println("enter student id");
		studentsId = scanner.next();
		System.out.println("enter student first name");
		String studentsFirstName = scanner.next();
		System.out.println("enter student last name");
		String studentsLastName = scanner.next();
		System.out.println("enter faculty id");
		String facultyId = scanner.next();
		List<Faculty> facultyFiltered = app.universityOffice.getFaculties().stream()
			.filter(f -> f.getId().equals(facultyId)).collect(Collectors.toList());
		if (facultyFiltered.size() == 0) {
		    System.out.println("faculty with id " + facultyId + "not exist");
		    break;
		}
		Faculty facultyByid = facultyFiltered.get(0);

		System.out.println("enter speciality id");
		String specialityId = scanner.next();
		List<Speciality> specialityFiltered = app.universityOffice.getFaculties().stream()
			.flatMap(f -> f.getSpecialities().stream()).filter(s -> s.getId().equals(specialityId))
			.collect(Collectors.toList());
		if (specialityFiltered.size() == 0) {
		    System.out.println("speciality with id " + specialityId + "not exist");
		    break;
		}
		Speciality specialityById = specialityFiltered.get(0);
		Student studentByUser = new Student(studentsId, studentsFirstName, studentsLastName, facultyByid,
			specialityById);
		app.studentsRegister.getStudents().add(studentByUser);
		System.out.println("student " + studentByUser + " was created");

	    case 10:
		exit = true;
		break;
	    default:
		System.out.println("any operation choosen");
		break;
	    }
	}

    }

    private static LocalDate getDateFromString(String date) {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
	return LocalDate.parse(date, formatter);

    }

    private void fillExampleData() {
	universityOffice.setFaculties(new ArrayList<>());
	universityOffice.getFaculties().add(facultyOfFinance);
	Holiday holidayNewYear = new Holiday("New year", LocalDate.of(2020, 1, 1));
	Holiday holidayIndependantDay = new Holiday("Independent Day", LocalDate.of(2020, 8, 24));
	universityOffice.setHolidays(new ArrayList<>());
	universityOffice.getHolidays().add(holidayNewYear);
	universityOffice.getHolidays().add(holidayIndependantDay);
	Teacher teacher1 = new Teacher("t1", "Teacher", "One");
	Teacher teacher2 = new Teacher("t2", "Teacher", "Two");
	Teacher teacher3 = new Teacher("t3", "Teacher", "Three");
	Teacher teacher4 = new Teacher("t4", "Teacher", "Four");
	Teacher teacher5 = new Teacher("t5", "Teacher", "Five");
	Teacher teacher6 = new Teacher("t6", "Teacher", "Six");
	Teacher teacher7 = new Teacher("t7", "Teacher", "Seven");
	Teacher teacher8 = new Teacher("t8", "Teacher", "Eight");
	Subject subject1Finance = new Subject("Economy");
	Subject subject2Finance = new Subject("Meth");
	Subject subject3Finance = new Subject("Data Analysis");
	Subject subject4Finance = new Subject("Accounting");
	Subject subject1Banking = new Subject("Banking Sysytem");
	Subject subject2Banking = new Subject("Riscs");
	Subject subject3Banking = new Subject("Ensuarence");
	Subject subject4Banking = new Subject("Low");
	facultyOfFinance.setSpecialities(new ArrayList<>());
	facultyOfFinance.getSpecialities().add(specialityFinance);
	facultyOfFinance.getSpecialities().add(specialityBanking);
	facultyOfFinance.setDepartments(new ArrayList<>());
	facultyOfFinance.getDepartments().add(department);
	department.setTeachers(new ArrayList<>());
	department.getTeachers().add(teacher1);
	department.getTeachers().add(teacher2);
	department.getTeachers().add(teacher3);
	department.getTeachers().add(teacher4);
	department.getTeachers().add(teacher5);
	department.getTeachers().add(teacher6);
	department.getTeachers().add(teacher7);
	department.getTeachers().add(teacher8);
	facultyOfFinance.setDeansOffice(deansOffice);
	facultyOfFinance.getSpecialities().get(0).setYears(new ArrayList<>());
	facultyOfFinance.getSpecialities().get(0).getYears().add(year1Finance);
	facultyOfFinance.getSpecialities().get(1).setYears(new ArrayList<>());
	facultyOfFinance.getSpecialities().get(1).getYears().add(year1Banking);
	year1Finance.setGroups(new ArrayList<>());
	year1Finance.getGroups().add(group1FinanceYear1);
	year1Finance.getGroups().add(group2FinanceYear1);
	year1Banking.setGroups(new ArrayList<>());
	year1Banking.getGroups().add(group1BankingYear1);
	year1Banking.getGroups().add(group2BankingYear1);
	facultyOfFinance.getSpecialities().get(0).getYears().get(0).setGroups(new ArrayList<>());
	facultyOfFinance.getSpecialities().get(1).getYears().get(0).setGroups(new ArrayList<>());
	facultyOfFinance.getSpecialities().get(0).getYears().get(0).getGroups().add(group1FinanceYear1);
	facultyOfFinance.getSpecialities().get(0).getYears().get(0).getGroups().add(group2FinanceYear1);
	facultyOfFinance.getSpecialities().get(1).getYears().get(0).getGroups().add(group1BankingYear1);
	facultyOfFinance.getSpecialities().get(1).getYears().get(0).getGroups().add(group2BankingYear1);
	subject1Finance.setTeachers(new ArrayList<>());
	subject2Finance.setTeachers(new ArrayList<>());
	subject3Finance.setTeachers(new ArrayList<>());
	subject4Finance.setTeachers(new ArrayList<>());
	subject1Banking.setTeachers(new ArrayList<>());
	subject2Banking.setTeachers(new ArrayList<>());
	subject3Banking.setTeachers(new ArrayList<>());
	subject4Banking.setTeachers(new ArrayList<>());
	subject1Finance.getTeachers().add(teacher1);
	subject2Finance.getTeachers().add(teacher2);
	subject3Finance.getTeachers().add(teacher3);
	subject4Finance.getTeachers().add(teacher4);
	subject1Banking.getTeachers().add(teacher5);
	subject2Banking.getTeachers().add(teacher6);
	subject3Banking.getTeachers().add(teacher7);
	subject4Banking.getTeachers().add(teacher8);
	year1Finance.setSubjects(new ArrayList<>());
	year1Banking.setSubjects(new ArrayList<>());
	year1Finance.getSubjects().add(subject1Finance);
	year1Finance.getSubjects().add(subject2Finance);
	year1Banking.getSubjects().add(subject1Banking);
	year1Banking.getSubjects().add(subject2Banking);
	LessonTime lessonTime1 = new LessonTime("First lesson", LocalTime.of(8, 30), LocalTime.of(9, 15));
	LessonTime lessonTime2 = new LessonTime("Second lesson", LocalTime.of(9, 30), LocalTime.of(10, 15));
	lessonsTimeSchedule.setLessonTimes(new ArrayList<>());
	lessonsTimeSchedule.getLessonTimes().add(lessonTime1);
	lessonsTimeSchedule.getLessonTimes().add(lessonTime2);
	building.setAudiences(new ArrayList<>());
	building.getAudiences().add(audience1);
	building.getAudiences().add(audience2);
	building.getAudiences().add(audience3);
	building.getAudiences().add(audience4);
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
	Timetable timetableFinanceYear1 = new Timetable("Timetable", specialityFinance, year1Finance);
	Timetable timetableBankingYear1 = new Timetable("Timetable", specialityBanking, year1Banking);
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
	lesson1FinanceYear1Date01.setGroups(new ArrayList<>());
	lesson1FinanceYear1Date01.getGroups().addAll(year1Finance.getGroups());
	Student student1FinanceYear1Group1 = new Student("6104_1", "Name1", "LastName1", facultyOfFinance,
		specialityFinance);
	Student student2FinanceYear1Group2 = new Student("6104_2", "Name2", "LastName2", facultyOfFinance,
		specialityFinance);
	Student student1BankingYear1Group1 = new Student("6105_1", "Name3", "LastName3", facultyOfFinance,
		specialityBanking);
	Student student2BankingYear1Group2 = new Student("6105_2", "Name4", "LastName4", facultyOfFinance,
		specialityBanking);
	student1FinanceYear1Group1.setGender(Gender.MALE);
	student2FinanceYear1Group2.setGender(Gender.MALE);
	student1BankingYear1Group1.setGender(Gender.MALE);
	student2BankingYear1Group2.setGender(Gender.MALE);
	studentsRegister.setStudents(new ArrayList<>());
	studentsRegister.getStudents().add(student1FinanceYear1Group1);
	studentsRegister.getStudents().add(student2FinanceYear1Group2);
	studentsRegister.getStudents().add(student1BankingYear1Group1);
	studentsRegister.getStudents().add(student2BankingYear1Group2);
	group1FinanceYear1.setStudents(new ArrayList<>());
	group2FinanceYear1.setStudents(new ArrayList<>());
	group1BankingYear1.setStudents(new ArrayList<>());
	group2BankingYear1.setStudents(new ArrayList<>());
	group1FinanceYear1.addStudentToGroup(student1FinanceYear1Group1);
	group2FinanceYear1.addStudentToGroup(student2FinanceYear1Group2);
	group1BankingYear1.addStudentToGroup(student1BankingYear1Group1);
	group2BankingYear1.addStudentToGroup(student2BankingYear1Group2);
	Vocation vocation = new Vocation("yearly", LocalDate.of(2020, 1, 1), teacher1, LocalDate.of(2020, 1, 1),
		LocalDate.of(2020, 1, 15));
	deansOffice.setVocations(new ArrayList<>());
	deansOffice.getVocations().add(vocation);
    }

}