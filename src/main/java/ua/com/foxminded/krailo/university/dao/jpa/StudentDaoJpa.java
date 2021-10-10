package ua.com.foxminded.krailo.university.dao.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.foxminded.krailo.university.model.Student;

public interface StudentDaoJpa extends PagingAndSortingRepository<Student, Integer> {

    	Optional<Student> findById (int id);
    	
    	Page<Student> findAll (Pageable pageable);
    	
    	Student save (Student student);
    	
    	void delete (Student student);
    	
    	@Query("select s from Student s inner join s.group as g  where  g.id = ?1")
    	List<Student> getByGroup (int groupId);
    	
    	
    
}
