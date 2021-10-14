package ua.com.foxminded.krailo.university.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.foxminded.krailo.university.model.Subject;

public interface SubjectDao extends PagingAndSortingRepository<Subject, Integer> {

}
