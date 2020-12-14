package ua.com.foxminded.krailo.domain;

import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;

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
    
    
    

}
