package ua.com.foxminded.krailo.university.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
	mockMvc = MockMvcBuilders.standaloneSetup(audienceController).build();
    }

    @Test
    void whenGetAllAudiences_thenAllAudiencesReturned() throws Exception {
	List<Audience> expected = getAudiencesForTest();
	when(audienceService.getAll()).thenReturn(expected);
	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/audiences/all");
	ResultActions result = mockMvc.perform(request);

	result.andExpect(view().name("audiences/audiencesAll")).andExpect(model().attribute("audiences", expected));
    }

    @Test
    void givenAudienceId_whenGetAudience_thenGotAudience() throws Exception {
	Audience expected = getAudiencesForTest().get(0);
	when(audienceService.getById(1)).thenReturn(expected);
	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/audiences/findAudienceById/1");
	ResultActions result = mockMvc.perform(request);

	result.andExpect(view().name("audiences/audienceView")).andExpect(model().attribute("audience", expected));
    }

    private List<Audience> getAudiencesForTest() {
	return Arrays.asList(Audience.builder().id(1).number("1").build(),
		Audience.builder().id(2).number("2").build());
    }
}
