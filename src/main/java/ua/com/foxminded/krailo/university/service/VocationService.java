package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.dao.interf.HolidayDaoInt;
import ua.com.foxminded.krailo.university.dao.interf.LessonDaoInt;
import ua.com.foxminded.krailo.university.dao.interf.VocationDaoInt;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.exception.VocationEndBoforeStartException;
import ua.com.foxminded.krailo.university.exception.VocationPeriodNotFreeException;
import ua.com.foxminded.krailo.university.exception.VocationPeriodNotSameYearException;
import ua.com.foxminded.krailo.university.exception.VocationPeriodTooLongException;
import ua.com.foxminded.krailo.university.model.Holiday;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Vocation;
import ua.com.foxminded.krailo.university.model.VocationKind;

@Transactional
@Service
public class VocationService {

    private static final Logger log = LoggerFactory.getLogger(VocationService.class);

    private VocationDaoInt vocationDaoInt;
    private LessonDaoInt lessonDaoInt;
    private HolidayDaoInt holidayDaoInt;
    @Value("#{${vocation.durationBykind}}")
    private Map<VocationKind, Integer> vocationDurationBykind;

    public VocationService(VocationDaoInt vocationDaoInt, LessonDaoInt lessonDaoInt, HolidayDaoInt holidayDaoInt) {
	this.vocationDaoInt = vocationDaoInt;
	this.lessonDaoInt = lessonDaoInt;
	this.holidayDaoInt = holidayDaoInt;
    }

    public void create(Vocation vocation) {
	log.debug("Create vocation={}", vocation);
	checkVocationDuratioMoreThenMaxDuration(vocation);
	checkVocationPeriodIsFree(vocation);
	checkVocationsEndDateMoreThenStart(vocation);
	checkVocationsStartAndEndDateBelongsTheSameYear(vocation);
	vocationDaoInt.create(vocation);
    }

    public void update(Vocation vocation) {
	log.debug("Update vocation={}", vocation);
	checkVocationDuratioMoreThenMaxDuration(vocation);
	checkVocationPeriodIsFree(vocation);
	checkVocationsEndDateMoreThenStart(vocation);
	checkVocationsStartAndEndDateBelongsTheSameYear(vocation);
	vocationDaoInt.update(vocation);
    }

    public Vocation getById(int id) {
	log.debug("Get vocation by id={}", id);
	return vocationDaoInt.getById(id)
		.orElseThrow(() -> new EntityNotFoundException(format("Vocation whith id=%s not exist", id)));
    }

    public List<Vocation> getAll() {
	log.debug("Get all vocations");
	return vocationDaoInt.getAll();
    }

    public void delete(Vocation vocation) {
	log.debug("Delete vocation={}", vocation);
	vocationDaoInt.delete(vocation);
    }

    public List<Vocation> getByTeacherIdAndYear(Teacher teacher, Year year) {
	log.debug("Get by teacherId={} and year={}", teacher.getId(), year);
	return vocationDaoInt.getByTeacherAndYear(teacher, year);
    }

    private void checkVocationPeriodIsFree(Vocation vocation) {
	if (!lessonDaoInt.getByTeacherBetweenDates(vocation.getTeacher(), vocation.getStart(), vocation.getEnd())
		.isEmpty()) {
	    throw new VocationPeriodNotFreeException("vocation period is not free from lessons");
	}
    }

    private void checkVocationsEndDateMoreThenStart(Vocation vocation) {
	if (vocation.getStart().isAfter(vocation.getEnd())) {
	    throw new VocationEndBoforeStartException("vocation end date less then start date");
	}
    }

    private void checkVocationsStartAndEndDateBelongsTheSameYear(Vocation vocation) {
	if (vocation.getStart().getYear() != vocation.getEnd().getYear()) {
	    throw new VocationPeriodNotSameYearException("vocation start and end dates not belong the same year");
	}
    }

    private void checkVocationDuratioMoreThenMaxDuration(Vocation vocation) {
	List<Vocation> vocations = vocationDaoInt.getByTeacherAndYear(vocation.getTeacher(),
		Year.from(vocation.getStart()));
	vocations.add(vocation);
	List<LocalDate> vocationDates = getVocationDates(vocations);
	List<LocalDate> holidays = holidayDaoInt.getAll().stream().map(Holiday::getDate).collect(Collectors.toList());
	if (vocationDates.stream().filter(d -> !isDateWeekend(d)).filter(d -> !holidays.contains(d))
		.count() > vocationDurationBykind.get(vocation.getKind())) {
	    throw new VocationPeriodTooLongException("vocation duration more then max duration");
	}
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

}
