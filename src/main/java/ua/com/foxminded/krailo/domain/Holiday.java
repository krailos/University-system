package ua.com.foxminded.krailo.domain;

import java.time.MonthDay;

public class Holiday {

    private String name;
    private MonthDay date;
    
    
    public Holiday(String name, MonthDay date) {
	this.name = name;
	this.date = date;
    }
    
    @Override
    public String toString() {
      return name + " - "+date.getDayOfMonth()+"."+date.getMonthValue();
    }
    
}
