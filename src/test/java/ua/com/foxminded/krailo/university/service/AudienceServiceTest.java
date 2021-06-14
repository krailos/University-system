package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.krailo.university.dao.AudienceDao;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.exception.NotUniqueNameException;
import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.util.Paging;

@ExtendWith(MockitoExtension.class)
class AudienceServiceTest {

    @InjectMocks
    private AudienceService audienceService;
    @Mock
    private AudienceDao audienceDao;

    @Test
    void givenAudience_whenCreate_thenCreated() {
	Audience audience = createAudience();
	when(audienceDao.findByNumber(audience.getNumber())).thenReturn(Optional.empty());

	audienceService.create(audience);

	verify(audienceDao).create(audience);
    }

    @Test
    void givenAudience_whenUpdate_thenUpdated() {
	Audience audience = createAudience();
	when(audienceDao.findByNumber(audience.getNumber())).thenReturn(Optional.empty());
	audienceService.update(audience);

	verify(audienceDao).update(audience);
    }

    @Test
    void givenAudienceWithExistingNumber_whenCreate_thenNotUniqueNameExceptionThrown() {
	Audience audience = createAudience();
	audience.setId(0);
	when(audienceDao.findByNumber(audience.getNumber())).thenReturn(Optional.of(Audience.builder().id(1).build()));

	Exception exception = assertThrows(NotUniqueNameException.class, () -> audienceService.create(audience));

	String expectedMessage = "audiences number=number 1  not unique";
	String actualMessage = exception.getMessage();
	assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void givenAudienceWithExistingNumberAndDiffrentId_whenUpdate_thenNotUniqueNameExceptionThrown() {
	Audience audience = createAudience();
	when(audienceDao.findByNumber(audience.getNumber())).thenReturn(Optional.of(Audience.builder().id(9).build()));

	Exception exception = assertThrows(NotUniqueNameException.class, () -> audienceService.update(audience));

	String expectedMessage = "audiences number=number 1  not unique";
	String actualMessage = exception.getMessage();
	assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void givenAudienceId_whenGetById_thenGot() {
	Optional<Audience> audience = Optional.of(createAudience());
	when(audienceDao.findById(1)).thenReturn(audience);

	Audience actual = audienceService.getById(1);

	Audience expected = createAudience();
	assertEquals(expected, actual);
    }

    @Test
    void givenEmptyOptional_whenGetById_thenEntityNotFoundExceptionThrown() {
	when(audienceDao.findById(10)).thenReturn(Optional.empty());

	Exception exception = assertThrows(EntityNotFoundException.class, () -> audienceService.getById(10));

	String expectedMessage = "Audience whith id=10 not exist";
	String actualMessage = exception.getMessage();
	assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void givenNumber_whenGetByNumber_thenGot() {
	Optional<Audience> audience = Optional.of(createAudience());
	when(audienceDao.findByNumber("1")).thenReturn(audience);

	Audience actual = audienceService.getByNumber("1");

	Audience expected = createAudience();
	assertEquals(expected, actual);
    }

    @Test
    void givenAudiences_whenGetAll_thenGot() {
	List<Audience> audiences = createAudiences();
	when(audienceDao.findAll()).thenReturn(audiences);

	List<Audience> actual = audienceService.getAll();

	List<Audience> expected = createAudiences();
	assertEquals(expected, actual);
    }

    @Test
    void givenAudience_whenDelete_thenDeleted() {
	Audience audience = createAudience();

	audienceService.delete(audience);

	verify(audienceDao).deleteById(1);
    }

    @Test
    void whenGetQuantity_thenGot() {
	when(audienceDao.findQuantity()).thenReturn(10);
	
	int actual = audienceService.getQuantity();

	assertEquals(10, actual);
    }

    @Test
    void givenAudiences_whenGetAudiencesByPage_thenGot() {
	List<Audience> audiences = createAudiences();
	Paging paging = new Paging (4, 2, 16);
	when(audienceDao.findWithLimit(4, 4)).thenReturn(audiences);

	List<Audience> actual = audienceService.getSelectedPage(paging);

	List<Audience> expected = createAudiences();
	assertEquals(expected, actual);
    }

    private Audience createAudience() {
	return Audience.builder().id(1).number("number 1").capacity(100).description("description").build();
    }

    private List<Audience> createAudiences() {
	List<Audience> audiences = new ArrayList<>();
	Audience audience1 = Audience.builder().id(1).number("number 1").capacity(100).description("description")
		.build();
	Audience audience2 = Audience.builder().id(1).number("number 1").capacity(100).description("description")
		.build();
	audiences.add(audience1);
	audiences.add(audience2);
	return audiences;
    }

}
