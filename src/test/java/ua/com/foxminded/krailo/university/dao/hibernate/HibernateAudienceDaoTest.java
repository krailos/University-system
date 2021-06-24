package ua.com.foxminded.krailo.university.dao.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.dao.AudienceDao;
import ua.com.foxminded.krailo.university.model.Audience;

@Transactional
@SpringJUnitWebConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class HibernateAudienceDaoTest {

    @Autowired
    private AudienceDao audienceDao;
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Test
    void givenNewAudience_whenCreate_thenCreated() throws Exception {
	Audience audience = getAudience();
	audience.setNumber("one");
	audience.setId(0);

	audienceDao.create(audience);

	assertEquals(audience, hibernateTemplate.get(Audience.class, audience.getId()));
    }

    @Test
    void givenAudienceWithExistingNumber_whenCreate_thenDaoConstraintViolationExceptionThrown() {
	Audience audience = Audience.builder().number("1").capacity(120).description("description3").build();

	String actual = assertThrows(ConstraintViolationException.class, () -> audienceDao.create(audience))
		.getMessage();

	String expected = "could not execute statement";
	assertEquals(expected, actual);
    }

    @Test
    void givenAudience_whenGetByNumber_thenGot() {
	Audience expected = getAudience();
		
	Audience actual = audienceDao.getByNumber("1").get();

	assertEquals(expected, actual);
    }

    @Test
    void givenAudiences_whenGetAll_thenGot() {
	List<Audience> expected = new ArrayList<>();
	expected.add(Audience.builder().id(1).number("1").capacity(300).description("description1").build());
	expected.add(Audience.builder().id(2).number("2").capacity(120).description("description2").build());
	expected.add(Audience.builder().id(3).number("3").capacity(120).description("description3").build());
	
	List<Audience> actual = audienceDao.getAll();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenGetdById_thenGot() {
	Audience expected = getAudience();
	
	Audience actual = audienceDao.getById(1).get();

	assertEquals(expected, actual);
    }

    @Test
    void givenNotExistingId_whenGetById_thenEmptyOptional() {

	Optional<Audience> actual = audienceDao.getById(10);

	assertEquals(Optional.empty(), actual);
    }

    @Test
    void givenAudienceWithNewNumberAndCapacity_whenUpdate_thenUpdated() {
	Audience expected = getAudience();
	expected.setCapacity(111);
	expected.setNumber("new number");

	audienceDao.update(expected);

	assertEquals(expected, hibernateTemplate.get(Audience.class, expected.getId()));
    }

    @Test
    void givenAudience_whenDelete_thenDeleted() throws Exception {
	Audience audience = getAudience();

	audienceDao.delete(audience);

	assertEquals(null, hibernateTemplate.get(Audience.class, audience.getId()));
    }

    @Test
    void givenAudiences_whenCount_thenCountReturned() {

	int actual = audienceDao.count();

	assertEquals(3, actual);
    }

    @Test
    void givenAudiences_whenGetByPage_thenAudiencesReturned() {
	List<Audience> expected = new ArrayList<>();
	expected.add(Audience.builder().id(1).number("1").capacity(300).description("description1").build());
	expected.add(Audience.builder().id(2).number("2").capacity(120).description("description2").build());
	expected.add(Audience.builder().id(3).number("3").capacity(120).description("description3").build());	
	int pageNo = 1;
	int pageSize = 3;
	Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

	assertEquals(expected, audienceDao.getByPage(pageable));
    }

    private Audience getAudience() {
	return Audience.builder().id(1).number("1").capacity(300).description("description1").build();
    }

}
