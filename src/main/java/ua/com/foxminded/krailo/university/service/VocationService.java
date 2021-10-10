package ua.com.foxminded.krailo.university.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.config.UniversityConfigProperties;
import ua.com.foxminded.krailo.university.dao.jpa.HolidayDaoJpa;
import ua.com.foxminded.krailo.university.dao.jpa.LessonDaoJpa;
import ua.com.foxminded.krailo.university.dao.jpa.VocationDaoJpa;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.exception.VocationEndBoforeStartException;
import ua.com.foxminded.krailo.university.exception.VocationPeriodNotFreeException;
import ua.com.foxminded.krailo.university.exception.VocationPeriodNotSameYearException;
import ua.com.foxminded.krailo.university.exception.VocationPeriodTooLongException;
import ua.com.foxminded.krailo.university.model.Holiday;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Vocation;

@Transactional
@Service
public class VocationService {

    private static final Logger log = LoggerFactory.getLogger(VocationService.class);

    private VocationDaoJpa vocationDao;
    private LessonDaoJpa lessonDao;
    private HolidayDaoJpa holidayDao;
    private UniversityConfigProperties universityConfigProperties;

    public VocationService(VocationDaoJpa vocationDao, LessonDaoJpa lessonDao, HolidayDaoJpa holidayDao,
	    UniversityConfigProperties universityConfigData) {
	this.vocationDao = vocationDao;
	this.lessonDao = lessonDao;
	this.holidayDao = holidayDao;
	this.universityConfigProperties = universityConfigData;
    }
    
    public Vocation getById(int id) {
	log.debug("Get vocation by id={}", id);
	return vocationDao.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(String.format("Vocation whith id=%s not exist", id)));
    }

    public List<Vocation> getAll() {
	log.debug("Get all vocations");
	return (List<Vocation>) vocationDao.findAll();
    }
    
    public void create(Vocation vocation) {
	log.debug("Create vocation={}", vocation);
	checkVocationDuratioMoreThenMaxDuration(vocation);
	checkVocationPeriodIsFree(vocation);
	checkVocationsEndDateMoreThenStart(vocation);
	checkVocationsStartAndEndDateBelongsTheSameYear(vocation);
	vocationDao.save(vocation);
    }

    public void delete(Vocation vocation) {
	log.debug("Delete vocation={}", vocation);
	vocationDao.delete(vocation);
    }

    public List<Vocation> getByTeacherAndYear(Teacher teacher, Year year) {
	log.debug("Get by teacherId={} and year={}", teacher.getId(), year);
	return vocationDao.getByTeacherAndYear(teacher.getId(), year.getValue());
    }

    private void checkVocationPeriodIsFree(Vocation vocation) {
	if (!lessonDao.getByTeacherBetweenDates(vocation.getTeacher().getId(), vocation.getStart(), vocation.getEnd())
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
	List<Vocation> vocations = vocationDao.getByTeacherAndYear(vocation.getTeacher().getId(),
		Year.from(vocation.getStart()).getValue());
	vocations.add(vocation);
	List<LocalDate> vocationDates = getVocationDates(vocations);
	List<LocalDate> holidays = holidayDao.findAll().stream().map(Holiday::getDate).collect(Collectors.toList());
	if (vocationDates.stream().filter(d -> !isDateWeekend(d)).filter(d -> !holidays.contains(d))
		.count() > universityConfigProperties.getVocationDurationBykind().get(vocation.getKind())) {
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
