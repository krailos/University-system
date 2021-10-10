package ua.com.foxminded.krailo.university.dao.jpa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.foxminded.krailo.university.model.Holiday;

public interface HolidayDaoJpa extends PagingAndSortingRepository<Holiday, Integer> {

    	Optional<Holiday> findById (int id);
    	
    	List<Holiday> findAll (); 
    	
    	void delete (Holiday holiday);
    	
    	Holiday save (Holiday holiday);
    	
    	@Query("from Holiday where date = ?1")
    	Optional<Holiday> getByDate (LocalDate date);
    	
    	
    
}
