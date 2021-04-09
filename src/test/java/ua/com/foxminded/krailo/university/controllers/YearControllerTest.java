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
import ua.com.foxminded.krailo.university.model.Year;
import ua.com.foxminded.krailo.university.service.YearService;

@ExtendWith(MockitoExtension.class)
class YearControllerTest {

    @Mock
    private YearService yearService;
    @InjectMocks
    private YearController yearController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
	mockMvc = standaloneSetup(yearController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    void WhenGetAllYears_ThenAllYearsReturned() throws Exception {
	List<Year> expected = buildYaers();
	when(yearService.getAll()).thenReturn(expected);

	mockMvc.perform(get("/years")).andExpect(view().name("years/all")).andExpect(status().isOk())
		.andExpect(model().attribute("years", expected));

    }

    @Test
    void givenWrongAudienceId_whenGetAudience_thenEntityNotFoundExceptionThrown() throws Exception {
	when(yearService.getById(1)).thenThrow(new EntityNotFoundException("entity not exist"));

	mockMvc.perform(get("/years/1")).andExpect(view().name("errors/error"))
		.andExpect(model().attribute("message", "entity not exist"));
    }

    @Test
    void givenYearId_WhenGetYear_ThenYearGot() throws Exception {
	Year expected = buildYaers().get(0);
	when(yearService.getById(1)).thenReturn(expected);

	mockMvc.perform(get("/years/1")).andExpect(view().name("years/year")).andExpect(status().isOk())
		.andExpect(model().attribute("year", expected));

    }

    private List<Year> buildYaers() {
	return Arrays.asList(Year.builder().id(1).name("year1").build(), Year.builder().id(2).name("year2").build());
    }

}
