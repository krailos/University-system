package ua.com.foxminded.krailo.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    UniversityOffice universityOffice = new UniversityOffice("KNEU");
    Faculty faculty = new Faculty("Faculty of Finance and Economy");
    Department department = new Department("Department of Financial faculty");
    DeansOffice deansOffice = new DeansOffice("Deans office of Financial faculty", faculty, universityOffice);
    Speciality specialityFinance = new Speciality("Finance", faculty);
    Speciality specialityBanking = new Speciality("Banking", faculty);
    Year year1Finance = new Year("1 year", specialityFinance);
    Year year1Banking = new Year("1 year", specialityBanking);
    Group group1FinanceYear1 = new Group("1 group", year1Finance, specialityFinance);
    Group group2FinanceYear1 = new Group("2 group", year1Finance, specialityFinance);
    Group group1BankingYear1 = new Group("1 group", year1Banking, specialityBanking);
    Group group2BankingYear1 = new Group("2 group", year1Banking, specialityFinance);
    Building building = new Building("Main building");
    Audience audience1 = new Audience("audience 1");
    Audience audience2 = new Audience("audience 2");
    Audience audience3 = new Audience("audience 3");
    Audience audience4 = new Audience("audience 4");
    LessonsTimeSchedule lessonsTimeSchedule = new LessonsTimeSchedule("lessons time schedule");

    public static void main(String[] args) {

	Main app = new Main();

	app.fillExampleData();
	System.out.println("data loaded");
	
	System.out.println(app.deansOffice.showAllTimetables());
	
	System.out.println(app.deansOffice.getTimetableBySpecialityAndYear("Finance", "1 year")
		.showTimetableByStudent("finance1", LocalDate.of(2020, 12, 1), LocalDate.of(2020, 12, 3)));
	Scanner scanner = new Scanner(System.in);

	System.out.println("application university office");
	System.out.println("choose operations");
	System.out.println();

    }

    private void showSceduleOfFaculty() {
	System.out.println("-- Using timetable");
	System.out.println(deansOffice.getTimetableBySpecialityAndYear("Finance", "Year1")
		.showTimetableByStudent("finance1", LocalDate.of(2020, 12, 1), LocalDate.of(2020, 12, 3)));
	System.out.println(deansOffice.getTimetableBySpecialityAndYear("Banking", "Year1")
		.showTimetableByStudent("banling1", LocalDate.of(2020, 12, 1), LocalDate.of(2020, 12, 2)));
//	System.out.println(timetableFinanceYear1.showTimetableByTeacher(teacher1, LocalDate.of(2020, 12, 1),
//		LocalDate.of(2020, 12, 2)));
	System.out.println("-- Using holidays");
	System.out.println(universityOffice.showHolidays());

	System.out.println("-- Using vocations");
//	System.out.println(
//		deansOffice.showVocationByTeacher(teacher1, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 12, 31)));
    }

    private void fillExampleData() {
	Holiday holidayNewYear = new Holiday("New year", LocalDate.of(2020, 1, 1));
	Holiday holidayIndependantDay = new Holiday("Independent Day", LocalDate.of(2020, 8, 24));
	universityOffice.setHolidays(new ArrayList<>());
	universityOffice.getHolidays().add(holidayNewYear);
	universityOffice.getHolidays().add(holidayIndependantDay);
	Teacher teacher1 = new Teacher("Teacher", "One");
	Teacher teacher2 = new Teacher("Teacher", "Two");
	Teacher teacher3 = new Teacher("Teacher", "Three");
	Teacher teacher4 = new Teacher("Teacher", "Four");
	Teacher teacher5 = new Teacher("Teacher", "Five");
	Teacher teacher6 = new Teacher("Teacher", "Six");
	Teacher teacher7 = new Teacher("Teacher", "Seven");
	Teacher teacher8 = new Teacher("Teacher", "Eight");
	Subject subject1Finance = new Subject("Economy");
	Subject subject2Finance = new Subject("Meth");
	Subject subject3Finance = new Subject("Data Analysis");
	Subject subject4Finance = new Subject("Accounting");
	Subject subject1Banking = new Subject("Banking Sysytem");
	Subject subject2Banking = new Subject("Riscs");
	Subject subject3Banking = new Subject("Ensuarence");
	Subject subject4Banking = new Subject("Low");
	faculty.setSpecialities(new ArrayList<>());
	faculty.getSpecialities().add(specialityFinance);
	faculty.getSpecialities().add(specialityBanking);
	faculty.setDepartments(new ArrayList<>());
	faculty.getDepartments().add(department);
	department.setTeachers(new ArrayList<>());
	department.getTeachers().add(teacher1);
	department.getTeachers().add(teacher2);
	department.getTeachers().add(teacher3);
	department.getTeachers().add(teacher4);
	department.getTeachers().add(teacher5);
	department.getTeachers().add(teacher6);
	department.getTeachers().add(teacher7);
	department.getTeachers().add(teacher8);
	faculty.setDeansOffice(deansOffice);
	faculty.getSpecialities().get(0).setYears(new ArrayList<>());
	faculty.getSpecialities().get(0).getYears().add(year1Finance);
	faculty.getSpecialities().get(1).setYears(new ArrayList<>());
	faculty.getSpecialities().get(1).getYears().add(year1Banking);
	year1Finance.setGroups(new ArrayList<>());
	year1Finance.getGroups().add(group1FinanceYear1);
	year1Finance.getGroups().add(group2FinanceYear1);
	year1Banking.setGroups(new ArrayList<>());
	year1Banking.getGroups().add(group1BankingYear1);
	year1Banking.getGroups().add(group2BankingYear1);
	faculty.getSpecialities().get(0).getYears().get(0).setGroups(new ArrayList<>());
	faculty.getSpecialities().get(1).getYears().get(0).setGroups(new ArrayList<>());
	faculty.getSpecialities().get(0).getYears().get(0).getGroups().add(group1FinanceYear1);
	faculty.getSpecialities().get(0).getYears().get(0).getGroups().add(group2FinanceYear1);
	faculty.getSpecialities().get(1).getYears().get(0).getGroups().add(group1BankingYear1);
	faculty.getSpecialities().get(1).getYears().get(0).getGroups().add(group2BankingYear1);
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
	Lesson lesson1FinanceYear1Date01 = new Lesson(LocalDate.of(2020, 12, 1), subject1Finance, audience1, lessonTime1,
		teacher1);
	Lesson lesson2FinanceYear1Date01 = new Lesson(LocalDate.of(2020, 12, 1), subject2Finance, audience1, lessonTime2,
		teacher2);
	Lesson lesson1FinanceYear1Date02 = new Lesson(LocalDate.of(2020, 12, 2), subject1Finance, audience1, lessonTime1,
		teacher1);
	Lesson lesson2FinanceYear1Date02 = new Lesson(LocalDate.of(2020, 12, 2), subject2Finance, audience1, lessonTime2,
		teacher2);
	Lesson lesson1FinanceYear1Date03 = new Lesson(LocalDate.of(2020, 12, 3), subject1Finance, audience1, lessonTime1,
		teacher1);
	Lesson lesson2FinanceYear1Date03 = new Lesson(LocalDate.of(2020, 12, 3), subject2Finance, audience1, lessonTime2,
		teacher2);
	Lesson lesson1BankingYear1Date01 = new Lesson(LocalDate.of(2020, 12, 1), subject1Banking, audience2, lessonTime1,
		teacher3);
	Lesson lesson2BankingYear1Date01 = new Lesson(LocalDate.of(2020, 12, 1), subject2Banking, audience2, lessonTime2,
		teacher4);	
	Lesson lesson1BankingYear1Date02 = new Lesson(LocalDate.of(2020, 12, 2), subject1Banking, audience2, lessonTime1,
		teacher3);
	Lesson lesson2BankingYear1Date02 = new Lesson(LocalDate.of(2020, 12, 2), subject2Banking, audience2, lessonTime2,
		teacher4);	
	Lesson lesson1BankingYear1Date03 = new Lesson(LocalDate.of(2020, 12, 3), subject1Banking, audience2, lessonTime1,
		teacher3);
	Lesson lesson2BankingYear1Date03 = new Lesson(LocalDate.of(2020, 12, 3), subject2Banking, audience2, lessonTime2,
		teacher4);
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
	Student student1FinanceYear1Group1 = new Student("finance1", "Name1", "LastNameFinance1", faculty, specialityFinance,
		group1FinanceYear1);
	student1FinanceYear1Group1.setGender(Gender.MALE);
	Student student2FinanceYear1Group2 = new Student("finance2", "Name2", "LastNameFinance2", faculty, specialityFinance,
		group1FinanceYear1);
	student2FinanceYear1Group2.setGender(Gender.MALE);
	Student student1BankingYear1Group1 = new Student("banking1", "Name1", "LastNameBanking1", faculty, specialityBanking,
		group1BankingYear1);
	student1BankingYear1Group1.setGender(Gender.MALE);
	Student student2BankingYear1Group2 = new Student("banking2", "Name2", "LastNameBanking2", faculty, specialityBanking,
		group1BankingYear1);
	student2BankingYear1Group2.setGender(Gender.MALE);
	group1FinanceYear1.setStudents(new ArrayList<>());
	group1FinanceYear1.getStudents().add(student1FinanceYear1Group1);
	group2FinanceYear1.setStudents(new ArrayList<>());
	group2FinanceYear1.getStudents().add(student2FinanceYear1Group2);
	group1BankingYear1.setStudents(new ArrayList<>());
	group1BankingYear1.getStudents().add(student1BankingYear1Group1);
	group2BankingYear1.setStudents(new ArrayList<>());
	group2BankingYear1.getStudents().add(student2BankingYear1Group2);
	Vocation vocation = new Vocation("yearly", LocalDate.of(2020, 1, 1), teacher1, LocalDate.of(2021, 1, 1),
		LocalDate.of(2021, 1, 15));
	deansOffice.setVocations(new ArrayList<>());
	deansOffice.getVocations().add(vocation);
    }

}