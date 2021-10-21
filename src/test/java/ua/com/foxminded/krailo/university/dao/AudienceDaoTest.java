package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.ConfigTest;
import ua.com.foxminded.krailo.university.model.Audience;

@Transactional
@ContextConfiguration(classes = ConfigTest.class)
@DataJpaTest
class AudienceDaoTest {

    @Autowired
    private AudienceDao audienceDao;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void givenNewAudience_whenCreate_thenCreated() throws Exception {
	Audience audience = getAudience();
	audience.setNumber("one");
	audience.setId(0);

	Audience expected = audienceDao.save(audience);

	assertEquals(expected, entityManager.find(Audience.class, expected.getId()));
    }

    @Test
    void givenAudienceWithExistingNumber_whenCreate_thenDaoConstraintViolationExceptionThrown() {
	Audience audience = Audience.builder().number("1").capacity(120).description("description3").build();

	String actual = assertThrows(DataIntegrityViolationException.class, () -> audienceDao.save(audience))
		.getMessage();

	String expected = "could not execute statement; SQL [n/a]; constraint [\"PUBLIC.CONSTRAINT_INDEX_B ON PUBLIC.AUDIENCES(NUMBER) VALUES 1\"; SQL statement:\n"
		+ "insert into audiences (id, capacity, description, number) values (null, ?, ?, ?) [23505-200]]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement";
	assertEquals(expected, actual);
    }

    @Test
    void givenAudience_whenGetByNumber_thenGot() {
	Audience expected = getAudience();

	Audience actual = audienceDao.findByNumber("1").get();

	assertEquals(expected, actual);
    }

    @Test
    void givenAudiences_whenGetAll_thenGot() {
	List<Audience> expected = new ArrayList<>();
	expected.add(Audience.builder().id(1).number("1").capacity(300).description("description1").build());
	expected.add(Audience.builder().id(2).number("2").capacity(120).description("description2").build());
	expected.add(Audience.builder().id(3).number("3").capacity(120).description("description3").build());

	List<Audience> actual = audienceDao.findAll();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenGetdById_thenGot() {
	Audience expected = getAudience();

	Audience actual = audienceDao.findById(1).get();

	assertEquals(expected, actual);
    }

    @Test
    void givenNotExistingId_whenGetById_thenEmptyOptional() {

	Optional<Audience> actual = audienceDao.findById(10);

	assertEquals(Optional.empty(), actual);
    }

    @Test
    void givenAudienceWithNewNumberAndCapacity_whenUpdate_thenUpdated() {
	Audience expected = getAudience();
	expected.setCapacity(111);
	expected.setNumber("new number");

	audienceDao.save(expected);

	assertEquals(expected, entityManager.find(Audience.class, expected.getId()));
    }

    @Test
    void givenAudience_whenDelete_thenDeleted() throws Exception {
	Audience audience = getAudience();

	audienceDao.delete(audience);

	assertNull(entityManager.find(Audience.class, audience.getId()));
    }

    @Test
    void givenAudiences_whenCount_thenCountReturned() {

	int actual = (int) audienceDao.count();

	assertEquals(3, actual);
    }

    @Test
    void givenAudiences_whenGetAllByPage_thenAudiencesReturned() {
	List<Audience> audiences = new ArrayList<>();
	audiences.add(Audience.builder().id(1).number("1").capacity(300).description("description1").build());
	audiences.add(Audience.builder().id(3).number("3").capacity(120).description("description3").build());
	audiences.add(Audience.builder().id(2).number("2").capacity(120).description("description2").build());
	audiences.sort((Audience a1, Audience a2) -> a1.getNumber().compareTo(a2.getNumber()));
	int pageNo = 0;
	int pageSize = 3;
	Pageable pageable = PageRequest.of(pageNo, pageSize);
	Page<Audience> expected = new PageImpl<>(audiences, pageable, 3);

	assertEquals(expected, audienceDao.findAll(pageable));
    }

    private Audience getAudience() {
	return Audience.builder().id(1).number("1").capacity(300).description("description1").build();
    }

}
