package ua.com.foxminded.krailo.university.model;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "lesson_Times")
@NamedQueries({ @NamedQuery(name = "SelectAllLessonTime", query = "from LessonTime l order by l.startTime"),
	@NamedQuery(name = "SelectLessonTimeByStartOrEnd", query = "from LessonTime l where :startLessonTime between l.startTime and l.endTime or "
		+ ":endLessonTime between l.startTime and l.endTime order by l.startTime") })
public class LessonTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    @Column(name = "order_number")
    private String orderNumber;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @Column(name = "start_time")
    private LocalTime startTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @Column(name = "end_time")
    private LocalTime endTime;

    public LessonTime() {
    }

    public LessonTime(int id, String orderNumber, LocalTime startTime, LocalTime endTime) {
	this.id = id;
	this.orderNumber = orderNumber;
	this.startTime = startTime;
	this.endTime = endTime;

    }

    public static LessonTimeBuilder builder() {
	return new LessonTimeBuilder();
    }

    public int getId() {
	return id;
    }

    public String getOrderNumber() {
	return orderNumber;
    }

    public void setOrderNumber(String lessonOrder) {
	this.orderNumber = lessonOrder;
    }

    public void setId(int id) {
	this.id = id;
    }

    public LocalTime getStartTime() {
	return startTime;
    }

    public void setStartTime(LocalTime startTime) {
	this.startTime = startTime;
    }

    public LocalTime getEndTime() {
	return endTime;
    }

    public void setEndTime(LocalTime endTime) {
	this.endTime = endTime;
    }

    public static class LessonTimeBuilder {

	private int id;
	private String orderNumber;
	private LocalTime startTime;
	private LocalTime endTime;

	public LessonTimeBuilder id(int id) {
	    this.id = id;
	    return this;
	}

	public LessonTimeBuilder orderNumber(String orderNumber) {
	    this.orderNumber = orderNumber;
	    return this;
	}

	public LessonTimeBuilder startTime(LocalTime startTime) {
	    this.startTime = startTime;
	    return this;
	}

	public LessonTimeBuilder endTime(LocalTime endTime) {
	    this.endTime = endTime;
	    return this;
	}

	public LessonTime build() {
	    return new LessonTime(id, orderNumber, startTime, endTime);
	}

    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
	result = prime * result + id;
	result = prime * result + ((orderNumber == null) ? 0 : orderNumber.hashCode());
	result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	LessonTime other = (LessonTime) obj;
	if (endTime == null) {
	    if (other.endTime != null)
		return false;
	} else if (!endTime.equals(other.endTime))
	    return false;
	if (id != other.id)
	    return false;
	if (orderNumber == null) {
	    if (other.orderNumber != null)
		return false;
	} else if (!orderNumber.equals(other.orderNumber))
	    return false;
	if (startTime == null) {
	    if (other.startTime != null)
		return false;
	} else if (!startTime.equals(other.startTime))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "from: " + startTime + " - " + "to: " + endTime;
    }

}
