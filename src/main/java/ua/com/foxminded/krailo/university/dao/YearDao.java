package ua.com.foxminded.krailo.university.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.foxminded.krailo.university.model.Year;

public interface YearDao extends PagingAndSortingRepository<Year, Integer>, JpaRepository<Year, Integer> {

}
