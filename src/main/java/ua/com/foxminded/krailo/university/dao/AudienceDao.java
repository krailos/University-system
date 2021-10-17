package ua.com.foxminded.krailo.university.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.foxminded.krailo.university.model.Audience;

public interface AudienceDao extends PagingAndSortingRepository<Audience, Integer>, JpaRepository<Audience, Integer> {

    Optional<Audience> findByNumber(String number);
}
