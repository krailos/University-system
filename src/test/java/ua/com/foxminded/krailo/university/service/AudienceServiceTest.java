package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
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
    void givenAudience_whenCreate_thenCreated() {
	Audience audience = new Audience(1, "new", null, 100, "");
	doNothing().when(audienceDao).create(audience);

	audienceService.create(audience);

	verify(audienceDao).create(audience);
    }

    @Test
    void givenAudience_whenUpdate_thenUpdated() {
	Audience audience = new Audience(1, "new", null, 100, "");
	doNothing().when(audienceDao).update(audience);

	audienceService.update(audience);

	verify(audienceDao).update(audience);
    }

    @Test
    void givenAudienceId_whenGetById_thenGot() {
	Audience audience = new Audience(1, "new", null, 100, "");
	when(audienceDao.findById(1)).thenReturn(audience);

	Audience actual = audienceService.getById(1);
	Audience expected = new Audience(1, "new", null, 100, "");

	assertEquals(expected, actual);
    }

    @Test
    void givenAudiences_whenGetAll_thenGot() {
	List<Audience> audiences = new ArrayList<>(Arrays.asList(new Audience(1, "new", null, 100, "")));
	when(audienceDao.findAll()).thenReturn(audiences);

	List<Audience> actual = audienceService.getAll();
	List<Audience> expected = new ArrayList<>(Arrays.asList(new Audience(1, "new", null, 100, "")));
	assertEquals(expected, actual);
    }
    
    @Test
    void givenBuildingId_whenGetAll_thenGot() {
	List<Audience> audiences = new ArrayList<>(Arrays.asList(new Audience(1, "new", new Building(1, "", ""), 100, "")));
	when(audienceDao.findByBuildingId(1)).thenReturn(audiences);

	List<Audience> actual = audienceService.getByBuildingId(1);
	List<Audience> expected = new ArrayList<>(Arrays.asList(new Audience(1, "new", new Building(1, "", ""), 100, "")));
	assertEquals(expected, actual);
    }

    @Test
    void givenAudienceId_whenDelete_thenDeleted() {
	Audience audience = new Audience(1, "new", null, 100, "");
	doNothing().when(audienceDao).deleteById(1);

	audienceService.delete(audience);

	verify(audienceDao).deleteById(1);
    }

}
