package ua.com.foxminded.krailo.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UniversityOffice {

    private String name;
    private String address;
    private List<Holiday> holidays;

    public UniversityOffice() {

    }

    public UniversityOffice(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public List<Holiday> getHolidays() {
	return holidays;
    }

    public void setHolidays(List<Holiday> holidays) {
	this.holidays = holidays;
    }

    @Override
    public String toString() {
	return name;
    }

    public String showHolidays() {
	StringBuilder sb = new StringBuilder();
	for (Holiday holiday : holidays) {
	    sb.append(holiday.toString()).append(System.lineSeparator());
	}
	return sb.toString();
    }
    
    public void addHoliday (Scanner scanner) {
	System.out.println("enter holiday name");
	String holidayName = scanner.nextLine();	
	System.out.println("enter year");
	int holidayYear = scanner.nextInt();
	System.out.println("enter month");
	int holidayMonth = scanner.nextInt();
	System.out.println("enter day");
	int holidayDay = scanner.nextInt();	
	Holiday holiday = new Holiday(holidayName, LocalDate.of(holidayYear, holidayMonth, holidayDay));
	holidays.add(holiday);
	System.out.println(holidays.get(holidays.size()-1));
    }

}
