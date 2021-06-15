package ua.com.foxminded.krailo.university.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "lessons")
@NamedQueries({ @NamedQuery(name = "SelectAllLessons", query = "from Lesson l order by l.date"),
	@NamedQuery(name = "SelectLessonsByDate", query = "from Lesson l where l.date = :date "),
	@NamedQuery(name = "SelectLessonsByTeacherBetweenDates", query = "from Lesson l where l.teacher.id = :teacherId and "
		+ " l.date between :startDate and :finishDate"),
	@NamedQuery(name = "SelectLessonsByTeacherAndDate", query = "from Lesson l where l.teacher.id = :teacherId and "
		+ " l.date = :date"),
	@NamedQuery(name = "SelectLessonsByDateAndTeacherAndLessonTime", query = "from Lesson l where l.date = :date"
		+ " and l.teacher.id = :teacherId and l.lessonTime.id = :lessonTimeId "),
	@NamedQuery(name = "SelectLessonsByDateAndAudienceAndLessonTime", query = "from Lesson l where l.date = :date"
		+ " and l.audience.id = :audienceId and l.lessonTime.id = :lessonTimeId "),
	@NamedQuery(name = "SelectLessonsByDateAndLessonTimeAndGroup", query = "select l from Lesson l inner join l.groups as g"
		+ " where l.date = :date and l.lessonTime.id = :lessonTimeId and g.id = :groupId "),
	@NamedQuery(name = "CountAllLessons", query = "select count(id) from Lesson") })
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "lesson_time_id")
    private LessonTime lessonTime;
    @ManyToOne
    private Subject subject;
    @ManyToOne
    private Audience audience;
    @ManyToOne
    private Teacher teacher;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "lessons_groups", joinColumns = { @JoinColumn(name = "lesson_id") }, inverseJoinColumns = {
	    @JoinColumn(name = "group_id") })
    private List<Group> groups;

    public Lesson() {
    }

    public Lesson(int id, LocalDate date, LessonTime lessonTime, Subject subject, Audience audience, Teacher teacher,
	    List<Group> groups) {
	this.id = id;
	this.date = date;
	this.lessonTime = lessonTime;
	this.subject = subject;
	this.audience = audience;
	this.teacher = teacher;
	this.groups = groups;
    }

    public static LessonBuilder builder() {
	return new LessonBuilder();
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public LocalDate getDate() {
	return date;
    }

    public void setDate(LocalDate date) {
	this.date = date;
    }

    public Subject getSubject() {
	return subject;
    }

    public void setSubject(Subject subject) {
	this.subject = subject;
    }

    public Audience getAudience() {
	return audience;
    }

    public void setAudience(Audience audience) {
	this.audience = audience;
    }

    public LessonTime getLessonTime() {
	return lessonTime;
    }

    public void setLessonTime(LessonTime lessonTime) {
	this.lessonTime = lessonTime;
    }

    public Teacher getTeacher() {
	return teacher;
    }

    public void setTeacher(Teacher teacher) {
	this.teacher = teacher;
    }

    public List<Group> getGroups() {
	return groups;
    }

    public void setGroups(List<Group> groups) {
	this.groups = groups;
    }

    public static class LessonBuilder {

	private int id;
	private LocalDate date;
	private LessonTime lessonTime;
	private Subject subject;
	private Audience audience;
	private Teacher teacher;
	private List<Group> groups = new ArrayList<>();

	public LessonBuilder id(int id) {
	    this.id = id;
	    return this;
	}

	public LessonBuilder date(LocalDate date) {
	    this.date = date;
	    return this;
	}

	public LessonBuilder lessonTime(LessonTime lessonTime) {
	    this.lessonTime = lessonTime;
	    return this;
	}

	public LessonBuilder subject(Subject subject) {
	    this.subject = subject;
	    return this;
	}

	public LessonBuilder audience(Audience audience) {
	    this.audience = audience;
	    return this;
	}

	public LessonBuilder teacher(Teacher teacher) {
	    this.teacher = teacher;
	    return this;
	}

	public LessonBuilder groups(List<Group> groups) {
	    this.groups = groups;
	    return this;
	}

	public Lesson build() {
	    return new Lesson(id, date, lessonTime, subject, audience, teacher, groups);
	}

    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((audience == null) ? 0 : audience.hashCode());
	result = prime * result + ((date == null) ? 0 : date.hashCode());
	result = prime * result + ((groups == null) ? 0 : groups.hashCode());
	result = prime * result + id;
	result = prime * result + ((lessonTime == null) ? 0 : lessonTime.hashCode());
	result = prime * result + ((subject == null) ? 0 : subject.hashCode());
	result = prime * result + ((teacher == null) ? 0 : teacher.hashCode());
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
	Lesson other = (Lesson) obj;
	if (audience == null) {
	    if (other.audience != null)
		return false;
	} else if (!audience.equals(other.audience))
	    return false;
	if (date == null) {
	    if (other.date != null)
		return false;
	} else if (!date.equals(other.date))
	    return false;
	if (groups == null) {
	    if (other.groups != null)
		return false;
	} else if (!groups.equals(other.groups))
	    return false;
	if (id != other.id)
	    return false;
	if (lessonTime == null) {
	    if (other.lessonTime != null)
		return false;
	} else if (!lessonTime.equals(other.lessonTime))
	    return false;
	if (subject == null) {
	    if (other.subject != null)
		return false;
	} else if (!subject.equals(other.subject))
	    return false;
	if (teacher == null) {
	    if (other.teacher != null)
		return false;
	} else if (!teacher.equals(other.teacher))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Lesson [id=" + id + ", date=" + date + ", lessonTime=" + lessonTime.getId() + ", subject="
		+ subject.getId() + ", audience=" + audience.getId() + ", teacher=" + teacher.getId() + ", groupsSize="
		+ groups.size() + "]";
    }

}
