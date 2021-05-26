package ua.com.foxminded.krailo.university.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import ua.com.foxminded.krailo.university.controllers.exception.ControllerExceptionHandler;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.service.AudienceService;
import ua.com.foxminded.krailo.university.util.Paging;

@ExtendWith(MockitoExtension.class)
class AudienceControllerTest {

    @Mock
    private AudienceService audienceService;
    @InjectMocks
    private AudienceController audienceController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
	mockMvc = standaloneSetup(audienceController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    void whenGetAllAudiences_thenFirstPageWithAudiencesReturned() throws Exception {
	List<Audience> expected = buildAudiences();
	Paging paging = new Paging(2, 1, 4);
	when(audienceService.getQuantity()).thenReturn(4);
	when(audienceService.getByPage(paging)).thenReturn(expected);

	mockMvc.perform(get("/audiences").param("pageSize", "2"))
		.andExpect(view().name("audiences/all"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("audiences", expected));
    }

    @Test
    void whenGetAllAudiencesWithParameters_thenRightPageWithAudiencesReturned() throws Exception {
	List<Audience> expected = buildAudiences();
	Paging paging = new Paging(2, 3, 6);
	when(audienceService.getByPage(paging)).thenReturn(expected);
	when(audienceService.getQuantity()).thenReturn(6);

	mockMvc.perform(get("/audiences?pageSize=2&pageNumber=3"))
		.andExpect(view().name("audiences/all"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("audiences", expected))
		.andExpect(model().attribute("pageQuantity", 3));
    }

    @Test
    void givenAudienceId_whenGetAudience_thenAudienceGot() throws Exception {
	Audience expected = buildAudiences().get(0);
	when(audienceService.getById(1)).thenReturn(expected);

	mockMvc.perform(get("/audiences/1"))
		.andExpect(view().name("audiences/audience"))
		.andExpect(model().attribute("audience", expected));
    }

    @Test
    void givenWrongAudienceId_whenGetAudience_thenEntityNotFoundExceptionThrown() throws Exception {
	when(audienceService.getById(1)).thenThrow(new EntityNotFoundException("entity not exist"));

	mockMvc.perform(get("/audiences/1"))
		.andExpect(view().name("errors/error"))
		.andExpect(model().attribute("message", "entity not exist"));
    }

    @Test
    void whenCreateAudience_thenAudienceReturned() throws Exception {

	mockMvc.perform(get("/audiences/create"))
		.andExpect(view().name("audiences/edit"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("audience"));
    }

    @Test
    void givenNewAudience_whenSaveAudience_thenAudienceSaved() throws Exception {
	Audience audience = new Audience();

	mockMvc.perform(post("/audiences/save").flashAttr("audience", audience))
		.andExpect(view().name("redirect:/audiences"))
		.andExpect(status().is(302));
	
	verify(audienceService).create(audience);
    }

    @Test
    void givenUpdatedAudience_whenUpdateAudience_thenAudienceUpdated() throws Exception {
	Audience audience = buildAudiences().get(0);

	mockMvc.perform(post("/audiences/save").flashAttr("audience", audience))
		.andExpect(view().name("redirect:/audiences"))
		.andExpect(status().is(302));
	
	verify(audienceService).update(audience);
    }

    @Test
    void givenAudienceId_whenEditAudience_thenAudienceReturnedToEdite() throws Exception {
	Audience audience = buildAudiences().get(0);
	when(audienceService.getById(1)).thenReturn(audience);

	mockMvc.perform(get("/audiences/edit/{id}", "1"))
		.andExpect(view().name("audiences/edit"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("audience", audience));
    }

    @Test
    void whenDeleteAudience_thenAudienceDeleted() throws Exception {
	Audience audience = buildAudiences().get(0);
	when(audienceService.getById(1)).thenReturn(audience);

	mockMvc.perform(post("/audiences/delete").param("id", "1"))
		.andExpect(view().name("redirect:/audiences"))
		.andExpect(status().is(302));
	
	verify(audienceService).delete(audience);
    }

    private List<Audience> buildAudiences() {
	return Arrays.asList(Audience.builder().id(1).number("1").build(),
		Audience.builder().id(2).number("2").build());
    }
}
