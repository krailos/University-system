package ua.com.foxminded.krailo.university.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.HolidayDao;
import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.VocationDao;
import ua.com.foxminded.krailo.university.model.Holiday;
import ua.com.foxminded.krailo.university.model.Vocation;

@Service
public class VocationService {

    private VocationDao vocationDao;
    private LessonDao lessonDao;
    private HolidayDao holidayDao;
    @Value("${model.vocationDurationGeneral}")
    private int vocationDurationGeneral;
    @Value("${model.vocationDurationPreferential}")
    private int vocationDurationPreferential;

    public VocationService(VocationDao vocationDao, LessonDao lessonDao, HolidayDao holidayDao) {
	this.vocationDao = vocationDao;
	this.lessonDao = lessonDao;
	this.holidayDao = holidayDao;
    }

    public void create(Vocation vocation) {
	if (!isVocationDuratioMoreThenMaxDuration(vocation) && isVocationPeriodFreeFromLessons(vocation)
		&& isVocationsEndDateMoreThenStart(vocation) && isVocationsStartAndEndDateBelongsSameYear(vocation)) {
	    vocationDao.create(vocation);
	}
    }

    public void update(Vocation vocation) {
	if (isVocationPeriodFreeFromLessons(vocation) && isVocationsEndDateMoreThenStart(vocation)
		&& isVocationDuratioMoreThenMaxDuration(vocation)
		&& isVocationsStartAndEndDateBelongsSameYear(vocation)) {
	    vocationDao.update(vocation);
	}
    }

    public Vocation getById(int id) {
	return vocationDao.findById(id);
    }

    public List<Vocation> getAll() {
	return vocationDao.findAll();
    }

    public void delete(Vocation vocation) {
	vocationDao.deleteById(vocation.getId());
    }

    private boolean isVocationPeriodFreeFromLessons(Vocation vocation) {
	return lessonDao.findByTeacherBetweenDates(vocation.getTeacher(), vocation.getStart(), vocation.getEnd())
		.isEmpty();
    }

    private boolean isVocationsEndDateMoreThenStart(Vocation vocation) {
	return vocation.getStart().isBefore(vocation.getEnd());
    }

    private boolean isVocationsStartAndEndDateBelongsSameYear(Vocation vocation) {
	return vocation.getStart().getYear() == vocation.getEnd().getYear();
    }

    private boolean isVocationDuratioMoreThenMaxDuration(Vocation vocation) {
	List<Vocation> vocations = vocationDao.findByTeacherIdAndYear(vocation.getTeacher().getId(),
		Year.from(vocation.getStart()));
	vocations.add(vocation);
	List<LocalDate> vocationDates = getVocationDates(vocations);
	List<LocalDate> holidays = holidayDao.findAll().stream().map(Holiday::getDate).collect(Collectors.toList());
	return vocationDates.stream().filter(d -> !isDateWeekend(d) || !holidays.contains(d))
		.count() > getMaxVocationDuration(vocation);
    }

    private boolean isDateWeekend(LocalDate date) {
	return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private List<LocalDate> getVocationDates(List<Vocation> vocations) {
	List<LocalDate> dates = new ArrayList<>();
	for (Vocation tempVocation : vocations) {
	    LocalDate tempDate = tempVocation.getStart();
	    while (tempDate.isBefore(tempVocation.getEnd().plusDays(1))) {
		dates.add(tempDate);
		tempDate = tempDate.plusDays(1);
	    }
	}
	return dates;
    }

    private int getMaxVocationDuration(Vocation vocation) {
	switch (vocation.getKind()) {
	case GENERAL:
	    return vocationDurationGeneral;
	case PREFERENTIAL:
	    return vocationDurationPreferential;
	default:
	    return 0;
	}
    }

}
