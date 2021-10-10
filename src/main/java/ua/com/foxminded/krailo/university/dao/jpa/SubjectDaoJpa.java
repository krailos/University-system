package ua.com.foxminded.krailo.university.dao.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.foxminded.krailo.university.model.Subject;

public interface SubjectDaoJpa extends PagingAndSortingRepository<Subject, Integer> {

    	Optional<Subject> findById (int id);
    	
    	List<Subject> findAll ();
    	
    	Subject save (Subject subject);
    	
    	void delete (Subject subject);
    	
    	
    
}
