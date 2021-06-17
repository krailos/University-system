package ua.com.foxminded.krailo.university.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;

import ua.com.foxminded.krailo.university.controllers.exception.ControllerExceptionHandler;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.service.AudienceService;

@ExtendWith(MockitoExtension.class)
class AudienceControllerTest {

    @Mock
    private AudienceService audienceService;
    @InjectMocks
    private AudienceController audienceController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
	mockMvc = standaloneSetup(audienceController).setControllerAdvice(new ControllerExceptionHandler())
		.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    }

    @Test
    void whenGetAllAudiences_thenFirstPageWithAudiencesReturned() throws Exception {

	int pageNo = 0;
	int pageSize = 3;
	int allAudiencesCount = 6;
	List<Audience> audiences = new ArrayList<>();
	audiences.addAll(buildAudiences());
	Pageable pageable = PageRequest.of(pageNo, pageSize);
	Page<Audience> expected = new PageImpl<>(audiences, pageable, allAudiencesCount);
	when(audienceService.getSelectedPage(pageable)).thenReturn(expected);

	mockMvc.perform(get("/audiences")
		.param("page", "0")
		.param("size", "3"))
		.andExpect(view().name("audiences/all"))
		.andExpect(model().attribute("audiencesPage", expected));
	
    }

    @Test
    void whenGetAllAudiencesWithParameters_thenRightPageWithAudiencesReturned() throws Exception {
	int pageNo = 1;
	int pageSize = 3;
	int allAudiencesCount = 6;
	List<Audience> audiences = new ArrayList<>();
	audiences.addAll(buildAudiences());
	Pageable pageable = PageRequest.of(pageNo, pageSize);
	Page<Audience> expected = new PageImpl<>(audiences, pageable, allAudiencesCount);
	when(audienceService.getSelectedPage(pageable)).thenReturn(expected);

	mockMvc.perform(get("/audiences")
		.param("page", "1")
		.param("size", "3"))
		.andExpect(view().name("audiences/all"))
		.andExpect(model().attribute("audiencesPage", expected));
    }

    @Test
    void givenAudienceId_whenGetAudience_thenAudienceGot() throws Exception {
	Audience expected = buildAudiences().get(0);
	when(audienceService.getById(1)).thenReturn(expected);

	mockMvc.perform(get("/audiences/{id}", "1"))
		.andExpect(view().name("audiences/audience"))
		.andExpect(model().attribute("audience", expected));
    }

    @Test
    void givenWrongAudienceId_whenGetAudience_thenEntityNotFoundExceptionThrown() throws Exception {
	when(audienceService.getById(1)).thenThrow(new EntityNotFoundException("entity not exist"));

	mockMvc.perform(get("/audiences/{id}", "1"))
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
	Audience audience = buildAudiences().get(0);
	audience.setId(0);

	mockMvc.perform(post("/audiences/save")
		.flashAttr("audience", audience))
		.andExpect(view().name("redirect:/audiences"))
		.andExpect(status().is(302));

	verify(audienceService).create(audience);
    }

    @Test
    void givenUpdatedAudience_whenUpdateAudience_thenAudienceUpdated() throws Exception {
	Audience audience = buildAudiences().get(0);

	mockMvc.perform(post("/audiences/save")
		.flashAttr("audience", audience))
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

	mockMvc.perform(post("/audiences/delete")
		.param("id", "1"))
		.andExpect(view().name("redirect:/audiences"))
		.andExpect(status().is(302));

	verify(audienceService).delete(audience);
    }

    private List<Audience> buildAudiences() {
	return Arrays.asList(Audience.builder().id(1).number("1").build(),
		Audience.builder().id(2).number("2").build());
    }
}
