package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
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
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.dao.AudienceDao;
import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Building;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ConfigTest.class)
class AudienceServiceTest {

    @InjectMocks
    private AudienceService audienceService;
    @Mock
    private AudienceDao audienceDao;

    @Test
    void givenAudienceWithNewNumber_whenCreate_thenCreated() {
	Audience audience = new Audience.AudienceBuilder().withId(1).withNumber("number newNumber")
		.withBuilding(new Building.BuildingBuilder().withId(1).built()).withCapacity(100)
		.withDescription("description").built();
	doNothing().when(audienceDao).create(audience);
	List<Audience> audiences = createAudiences();
	when(audienceDao.findByBuildingId(1)).thenReturn(audiences);

	audienceService.create(audience);

	verify(audienceDao).create(audience);
    }

    @Test
    void givenAudienceWithExistingNumber_whenCreate_thenNotCreated() {
	Audience audience = new Audience.AudienceBuilder().withId(1).withNumber("number 1")
		.withBuilding(new Building.BuildingBuilder().withId(1).built()).withCapacity(100)
		.withDescription("description").built();
	List<Audience> audiences = createAudiences();
	when(audienceDao.findByBuildingId(1)).thenReturn(audiences);

	audienceService.create(audience);

	verify(audienceDao, never()).create(audience);
    }

    @Test
    void givenAudienceWithExistingNumber_whenUpdate_thenUpdated() {
	Audience audience = new Audience.AudienceBuilder().withId(1).withNumber("number 1")
		.withBuilding(new Building.BuildingBuilder().withId(1).built()).withCapacity(100)
		.withDescription("description").built();
	doNothing().when(audienceDao).update(audience);
	List<Audience> audiences = createAudiences();
	when(audienceDao.findByBuildingId(1)).thenReturn(audiences);

	audienceService.update(audience);

	verify(audienceDao).update(audience);
    }

    @Test
    void givenAudienceWithNotExistingNumber_whenUpdate_thenNotUpdated() {
	Audience audience = new Audience.AudienceBuilder().withId(1).withNumber("number newNumber")
		.withBuilding(new Building.BuildingBuilder().withId(1).built()).withCapacity(100)
		.withDescription("description").built();
	List<Audience> audiences = createAudiences();
	when(audienceDao.findByBuildingId(1)).thenReturn(audiences);

	audienceService.update(audience);

	verify(audienceDao, never()).update(audience);
    }

    @Test
    void givenAudienceId_whenGetById_thenGot() {
	Audience audience = new Audience.AudienceBuilder().withId(1).withNumber("number 1")
		.withBuilding(new Building.BuildingBuilder().withId(1).built()).withCapacity(100)
		.withDescription("description").built();
	when(audienceDao.findById(1)).thenReturn(audience);

	Audience actual = audienceService.getById(1);

	Audience expected = new Audience.AudienceBuilder().withId(1).withNumber("number 1")
		.withBuilding(new Building.BuildingBuilder().withId(1).built()).withCapacity(100)
		.withDescription("description").built();
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
	Audience audience = new Audience.AudienceBuilder().withId(1).withNumber("number 1")
		.withBuilding(new Building.BuildingBuilder().withId(1).built()).withCapacity(100)
		.withDescription("description").built();
	doNothing().when(audienceDao).deleteById(1);

	audienceService.delete(audience);

	verify(audienceDao).deleteById(1);
    }

    private List<Audience> createAudiences() {
	List<Audience> audiences = new ArrayList<>();
	Audience audience1 = new Audience.AudienceBuilder().withId(1).withNumber("number 1")
		.withBuilding(new Building.BuildingBuilder().withId(1).built()).withCapacity(100)
		.withDescription("description").built();
	Audience audience2 = new Audience.AudienceBuilder().withId(2).withNumber("number 2")
		.withBuilding(new Building.BuildingBuilder().withId(1).built()).withCapacity(100)
		.withDescription("description").built();
	audiences.add(audience1);
	audiences.add(audience2);
	return audiences;
    }

}
