package ua.com.foxminded.krailo.university.dao.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.ConfigTest;
import ua.com.foxminded.krailo.university.dao.AudienceDao;
import ua.com.foxminded.krailo.university.model.Audience;

@ExtendWith(SpringExtension.class)
@Transactional
@Import(ConfigTest.class)
@WebAppConfiguration
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

	assertNull(hibernateTemplate.get(Audience.class, audience.getId()));
    }

    @Test
    void givenAudiences_whenCount_thenCountReturned() {

	int actual = audienceDao.count();

	assertEquals(hibernateTemplate.execute(session -> session.createQuery("from Audience").list().size()), actual);
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

	assertEquals(expected, audienceDao.getAll(pageable));
    }

    private Audience getAudience() {
	return Audience.builder().id(1).number("1").capacity(300).description("description1").build();
    }

}
