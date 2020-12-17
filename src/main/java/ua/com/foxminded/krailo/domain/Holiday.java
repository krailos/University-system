package ua.com.foxminded.krailo.domain;

import java.time.LocalDate;

public class Holiday {

    private String name;
    private LocalDate date;

    public Holiday(String name, LocalDate date) {
	this.name = name;
	this.date = date;
    }

    @Override
    public String toString() {
	return name + " " + date;
    }

}
