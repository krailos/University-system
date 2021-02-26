package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.krailo.university.dao.AudienceDao;
import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Building;

@ExtendWith(MockitoExtension.class)
class AudienceServiceTest {

    @InjectMocks
    private AudienceService audienceService;
    @Mock
    private AudienceDao audienceDao;

    @Test
    void givenAudienceWithNewNumber_whenCreate_thenCreated() {
	Audience audience = createAudience();
	when(audienceDao.findByNumberAndBuildingId(audience.getNumber(), audience.getBuilding().getId()))
		.thenReturn(null);

	audienceService.create(audience);

	verify(audienceDao).create(audience);
    }

    @Test
    void givenAudienceWithExistingNumber_whenCreate_thenNotCreated() {
	Audience audience = createAudience();
	audience.setId(0);
	when(audienceDao.findByNumberAndBuildingId(audience.getNumber(), audience.getBuilding().getId()))
		.thenReturn(Audience.builder().id(1).build());

	audienceService.create(audience);

	verify(audienceDao, never()).create(audience);
    }

    @Test
    void givenAudienceWithNewNumber_whenUpdate_thenUpdated() {
	Audience audience = createAudience();
	when(audienceDao.findByNumberAndBuildingId(audience.getNumber(), audience.getBuilding().getId()))
		.thenReturn(null);
	audienceService.update(audience);

	verify(audienceDao).update(audience);
    }

    @Test
    void givenAudienceWithNotChangedNumber_whenUpdate_thenUpdated() {
	Audience audience = createAudience();
	when(audienceDao.findByNumberAndBuildingId(audience.getNumber(), audience.getBuilding().getId()))
		.thenReturn(Audience.builder().id(1).build());

	audienceService.update(audience);

	verify(audienceDao).update(audience);
    }

    @Test
    void givenAudienceWithExistingNumber_whenUpdate_thenNotUpdated() {
	Audience audience = createAudience();
	when(audienceDao.findByNumberAndBuildingId(audience.getNumber(), audience.getBuilding().getId()))
		.thenReturn(Audience.builder().id(9).build());

	audienceService.update(audience);

	verify(audienceDao, never()).update(audience);
    }

    @Test
    void givenAudienceId_whenGetById_thenGot() {
	Audience audience = createAudience();
	when(audienceDao.findById(1)).thenReturn(audience);

	Audience actual = audienceService.getById(1);

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
    void givenBuildingId_whenGetByBuilding_thenGot() {
	List<Audience> audiences = createAudiences();
	when(audienceDao.findByBuildingId(1)).thenReturn(audiences);

	List<Audience> actual = audienceService.getByBuildingId(1);

	List<Audience> expected = createAudiences();
	assertEquals(expected, actual);
    }

    @Test
    void givenAudience_whenDelete_thenDeleted() {
	Audience audience = createAudience();

	audienceService.delete(audience);

	verify(audienceDao).deleteById(1);
    }

    private Audience createAudience() {
	return Audience.builder().id(1).number("number 1").building(Building.builder().id(1).build()).capacity(100)
		.description("description").build();
    }

    private List<Audience> createAudiences() {
	List<Audience> audiences = new ArrayList<>();
	Audience audience1 = Audience.builder().id(1).number("number 1").building(Building.builder().id(1).build())
		.capacity(100).description("description").build();
	Audience audience2 = Audience.builder().id(1).number("number 1").building(Building.builder().id(2).build())
		.capacity(100).description("description").build();
	audiences.add(audience1);
	audiences.add(audience2);
	return audiences;
    }

}
