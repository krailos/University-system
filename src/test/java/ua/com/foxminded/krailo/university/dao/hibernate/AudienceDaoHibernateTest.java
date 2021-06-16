package ua.com.foxminded.krailo.university.dao.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import ua.com.foxminded.krailo.university.dao.interf.AudienceDaoInt;
import ua.com.foxminded.krailo.university.model.Audience;

@Transactional
@SpringJUnitWebConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class AudienceDaoHibernateTest {

    @Autowired
    private AudienceDaoInt audienceDao;
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Test
    void givenNewAudience_whenCreate_thenCreated() throws Exception {
	Audience audience = getAudience();
	audience.setNumber("one");
	audience.setId(0);

	audienceDao.create(audience);

	assertEquals(audience, hibernateTemplate.get(Audience.class, 4));
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

	Audience actual = audienceDao.getByNumber("1").get();

	assertEquals("1", actual.getNumber());
    }

    @Test
    void givenAudiences_whenGetAll_thenGot() {

	int actual = audienceDao.getAll().size();

	assertEquals(3, actual);
    }

    @Test
    void givenId_whenGetdById_thenGot() {

	Audience actual = audienceDao.getById(1).get();

	assertEquals(1, actual.getId());
    }

    @Test
    void givenNotExistingId_whenGetById_thenEmptyOptional() {

	Optional<Audience> actual = audienceDao.getById(10);

	assertEquals(Optional.empty(), actual);
    }

    @Test
    void givenAudienceWithNewNumberAndCapacity_whenUpdate_thenUpdated() {
	Audience audience = getAudience();
	audience.setCapacity(111);
	audience.setNumber("new number");

	audienceDao.update(audience);

	assertEquals(audience.getNumber(), hibernateTemplate.get(Audience.class, 1).getNumber());
	assertEquals(audience.getCapacity(), hibernateTemplate.get(Audience.class, 1).getCapacity());
    }

    @Test
    void givenAudience_whenDelete_thenDeleted() throws Exception {
	Audience audience = getAudience();

	audienceDao.delete(audience);

	assertEquals(null, hibernateTemplate.get(Audience.class, 1));
    }

    @Test
    void givenAudiences_whenCount_thenCountReturned() {

	int actual = audienceDao.count();

	assertEquals(3, actual);
    }

    @Test
    void givenAudiences_whenGetByPage_thenAudiencesReturned() {
	int pageNo = 1;
	int pageSize = 3;
	Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

	assertEquals(3, audienceDao.getByPage(pageable).size());
    }

    private Audience getAudience() {
	return Audience.builder().id(1).number("1").capacity(300).description("description").build();
    }

}
