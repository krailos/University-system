package ua.com.foxminded.krailo.university.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.foxminded.krailo.university.model.Vocation;

public interface VocationDao extends PagingAndSortingRepository<Vocation, Integer> {

    @Query("from Vocation v where v.teacher.id = ?1 and" + " extract (year from v.start) = ?2 order by v.start ")
    List<Vocation> getByTeacherAndYear(int teacherId, int year);

    @Query("from Vocation v where v.teacher.id = ?1 and ?2 between v.start and v.end")
    Optional<Vocation> getByTeacherAndDate(int teacherId, LocalDate date);

}
