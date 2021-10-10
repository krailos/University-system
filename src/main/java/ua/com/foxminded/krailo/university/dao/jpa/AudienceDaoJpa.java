package ua.com.foxminded.krailo.university.dao.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.foxminded.krailo.university.model.Audience;

public interface AudienceDaoJpa extends PagingAndSortingRepository<Audience, Integer> {

    Optional<Audience> findById(Integer id);

    Optional<Audience> findByNumber(String number);

    Page<Audience> findAll(Pageable pageable);
    
    List<Audience> findAll();

    Audience save(Audience audience);

    void delete(Audience audience);

}
