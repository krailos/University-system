package ua.com.foxminded.krailo.university.model;

import java.time.LocalDate;

public class Vocation {

    private int id;
    private VocationKind kind;
    private LocalDate applyingDate;
    private LocalDate start;
    private LocalDate end;
    private Teacher teacher;

    public Vocation() {
    }

    public Vocation(int id, VocationKind kind, LocalDate applyingDate, LocalDate start, LocalDate end,
	    Teacher teacher) {
	this.id = id;
	this.kind = kind;
	this.applyingDate = applyingDate;
	this.start = start;
	this.end = end;
	this.teacher = teacher;
    }

    public static VocationBuilder builder() {
	return new VocationBuilder();
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public VocationKind getKind() {
	return kind;
    }

    public void setKind(VocationKind kind) {
	this.kind = kind;
    }

    public LocalDate getApplyingDate() {
	return applyingDate;
    }

    public void setApplyingDate(LocalDate applyingDate) {
	this.applyingDate = applyingDate;
    }

    public LocalDate getStart() {
	return start;
    }

    public void setStart(LocalDate start) {
	this.start = start;
    }

    public LocalDate getEnd() {
	return end;
    }

    public void setEnd(LocalDate end) {
	this.end = end;
    }

    public Teacher getTeacher() {
	return teacher;
    }

    public void setTeacher(Teacher teacher) {
	this.teacher = teacher;
    }

    public static class VocationBuilder {

	private int id;
	private VocationKind kind;
	private LocalDate applyingDate;
	private LocalDate start;
	private LocalDate end;
	private Teacher teacher;

	public VocationBuilder id(int id) {
	    this.id = id;
	    return this;
	}

	public VocationBuilder kind(VocationKind kind) {
	    this.kind = kind;
	    return this;
	}

	public VocationBuilder applyingDate(LocalDate applyingDate) {
	    this.applyingDate = applyingDate;
	    return this;
	}

	public VocationBuilder startDate(LocalDate startDate) {
	    this.start = startDate;
	    return this;
	}

	public VocationBuilder endDate(LocalDate andDate) {
	    this.end = andDate;
	    return this;
	}

	public VocationBuilder teacher(Teacher teacher) {
	    this.teacher = teacher;
	    return this;
	}

	public Vocation build() {
	    return new Vocation(id, kind, applyingDate, start, end, teacher);
	}

    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((applyingDate == null) ? 0 : applyingDate.hashCode());
	result = prime * result + ((end == null) ? 0 : end.hashCode());
	result = prime * result + id;
	result = prime * result + ((kind == null) ? 0 : kind.hashCode());
	result = prime * result + ((start == null) ? 0 : start.hashCode());
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
	Vocation other = (Vocation) obj;
	if (applyingDate == null) {
	    if (other.applyingDate != null)
		return false;
	} else if (!applyingDate.equals(other.applyingDate))
	    return false;
	if (end == null) {
	    if (other.end != null)
		return false;
	} else if (!end.equals(other.end))
	    return false;
	if (id != other.id)
	    return false;
	if (kind == null) {
	    if (other.kind != null)
		return false;
	} else if (!kind.equals(other.kind))
	    return false;
	if (start == null) {
	    if (other.start != null)
		return false;
	} else if (!start.equals(other.start))
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
	return teacher.toString() + " " + kind + " from " + start + " till " + end;
    }

}
