package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.config.WebConfig;
import ua.com.foxminded.krailo.university.exception.DaoConstraintViolationException;
import ua.com.foxminded.krailo.university.model.Audience;

@SpringJUnitWebConfig(classes = { WebConfig.class, ConfigTest.class })
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class AudienceDaoTest {

    @Autowired
    private AudienceDao audienceDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewAudience_whenCreate_thenCreated() throws Exception {
	Audience audience = Audience.builder().number("4").capacity(120).description("description3").build();

	audienceDao.create(audience);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "Audiences", "id =" + audience.getId());
	assertEquals(1, actual);
    }

    @Test
    void givenAudienceWithExistingNumber_whenCreate_thenDaoConstraintViolationExceptionThrown() {
	Audience audience = Audience.builder().number("1").capacity(120).description("description3").build();

	String actual = assertThrows(DaoConstraintViolationException.class, () -> audienceDao.create(audience))
		.getMessage();

	String expected = "Audiences not created, audience: id=0; number=1; capacity=120; description=description3";
	assertEquals(actual, expected);
    }

    @Test
    void givenAudience_whenFindByNumber_thenFound() {

	Audience actual = audienceDao.findByNumber("1").get();

	assertEquals("1", actual.getNumber());
    }

    @Test
    void givenAudiences_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Audiences");

	int actual = audienceDao.findAll().size();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {

	Audience actual = audienceDao.findById(1).get();

	assertEquals(1, actual.getId());
    }

    @Test
    void givenNotExistingId_whenFindById_thenEmptyOptional() {

	Optional<Audience> actual = audienceDao.findById(10);

	assertEquals(Optional.empty(), actual);
    }

    @Test
    void givenNewFieldsOfAudience_whenUpdate_thenUpdated() {
	Audience audience = Audience.builder().id(1).number("new").capacity(1).description("new").build();

	audienceDao.update(audience);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "audiences",
		"number = 'new'  AND capacity = 1 AND description = 'new'");
	assertEquals(1, actual);
    }

    @Test
    void givenAudienceWithExistingNumber_whenUpdate_thenUpdated() {
	Audience audience = Audience.builder().id(1).number("4").capacity(120).description("New").build();

	audienceDao.update(audience);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "audiences",
		" number = '4' AND description = 'New'");
	assertEquals(1, actual);
    }

    @Test
    void givenId_whenDelete_thenDeleted() throws Exception {

	audienceDao.deleteById(1);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Audiences");
	assertEquals(2, actual);
    }

}
