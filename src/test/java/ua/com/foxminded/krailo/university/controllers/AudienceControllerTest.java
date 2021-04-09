package ua.com.foxminded.krailo.university.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
	when(audienceService.getByPage(2, 0)).thenReturn(expected);

	mockMvc.perform(get("/audiences")).andExpect(view().name("audiences/all")).andExpect(status().isOk())
		.andExpect(model().attribute("audiences", expected));
    }

    @Test
    void whenGetAllAudiencesWithParameters_thenRightPageWithAudiencesReturned() throws Exception {
	List<Audience> expected = buildAudiences();
	when(audienceService.getByPage(2, 4)).thenReturn(expected);
	when(audienceService.getQuantity()).thenReturn(6);

	mockMvc.perform(get("/audiences?pageSize=2&pageId=3")).andExpect(view().name("audiences/all"))
		.andExpect(status().isOk()).andExpect(model().attribute("audiences", expected))
		.andExpect(model().attribute("pageQuantity", 3));
    }

    @Test
    void givenAudienceId_whenGetAudience_thenAudienceGot() throws Exception {
	Audience expected = buildAudiences().get(0);
	when(audienceService.getById(1)).thenReturn(expected);

	mockMvc.perform(get("/audiences/1")).andExpect(view().name("audiences/audience"))
		.andExpect(model().attribute("audience", expected));
    }

    @Test
    void givenWrongAudienceId_whenGetAudience_thenEntityNotFoundExceptionThrown() throws Exception {
	when(audienceService.getById(1)).thenThrow(new EntityNotFoundException("entity not exist"));

	mockMvc.perform(get("/audiences/1")).andExpect(view().name("errors/error"))
		.andExpect(model().attribute("message", "entity not exist"));
    }

    private List<Audience> buildAudiences() {
	return Arrays.asList(Audience.builder().id(1).number("1").build(),
		Audience.builder().id(2).number("2").build());
    }
}
