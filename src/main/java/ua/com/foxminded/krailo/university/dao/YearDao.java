package ua.com.foxminded.krailo.university.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.foxminded.krailo.university.model.Year;

public interface YearDao extends JpaRepository<Year, Integer> {

}
