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
import ua.com.foxminded.krailo.university.model.Holiday;
import ua.com.foxminded.krailo.university.service.HolidayService;

@ExtendWith(MockitoExtension.class)
class HolidayControllerTest {

    @Mock
    private HolidayService holidayService;
    @InjectMocks
    private HolidaysController holidayController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
	mockMvc = standaloneSetup(holidayController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    void WhenGetAllHolidays_ThenAllHolidaysReturned() throws Exception {
	List<Holiday> expected = buildHolidays();
	when(holidayService.getAll()).thenReturn(expected);

	mockMvc.perform(get("/holidays")).andExpect(view().name("holidays/all")).andExpect(status().isOk())
		.andExpect(model().attribute("holidays", expected));

    }

    @Test
    void givenHolidayId_WhenGetHoliday_ThenHolidayGot() throws Exception {
	Holiday expected = buildHolidays().get(0);
	when(holidayService.getById(1)).thenReturn(expected);

	mockMvc.perform(get("/holidays/1")).andExpect(view().name("holidays/holiday")).andExpect(status().isOk())
		.andExpect(model().attribute("holiday", expected));

    }

    @Test
    void givenWrongHolidayId_whenGetHoliday_thenEntityNotFoundExceptionThrown() throws Exception {
	when(holidayService.getById(1)).thenThrow(new EntityNotFoundException("entity not exist"));

	mockMvc.perform(get("/holidays/1")).andExpect(view().name("errors/error"))
		.andExpect(model().attribute("message", "entity not exist"));
    }

    @Test
    void WhenCreateAudience_ThenAudienceReturned() throws Exception {

	mockMvc.perform(get("/holidays/create")).andExpect(view().name("holidays/edit")).andExpect(status().isOk())
		.andExpect(model().attributeExists("holiday"));
    }

    @Test
    void givenNewHoliday_WhenSaveHoliday_ThenHolidaySaved() throws Exception {
	Holiday holiday = new Holiday();

	mockMvc.perform(post("/holidays/save").flashAttr("holiday", holiday))
		.andExpect(view().name("redirect:/holidays")).andExpect(status().is(302));
	verify(holidayService).create(holiday);
    }

    @Test
    void givenUpdatedHoliday_whenUpdateHoliday_ThenHolidayUpdated() throws Exception {
	Holiday holiday = buildHolidays().get(0);

	mockMvc.perform(post("/holidays/save").flashAttr("holiday", holiday))
		.andExpect(view().name("redirect:/holidays")).andExpect(status().is(302));
	verify(holidayService).update(holiday);
    }

    @Test
    void givenholidayId_whenEditholiday_ThenholidayReturnedToEdite() throws Exception {
	Holiday holiday = buildHolidays().get(0);
	when(holidayService.getById(1)).thenReturn(holiday);

	mockMvc.perform(get("/holidays/edit/{id}", "1")).andExpect(view().name("holidays/edit"))
		.andExpect(status().isOk()).andExpect(model().attribute("holiday", holiday));
    }

    @Test
    void whenDeleteHoliday_ThenHolidayDeleted() throws Exception {
	Holiday holiday = buildHolidays().get(0);
	when(holidayService.getById(1)).thenReturn(holiday);

	mockMvc.perform(post("/holidays/delete").param("id", "1")).andExpect(view().name("redirect:/holidays"))
		.andExpect(status().is(302));
	verify(holidayService).delete(holiday);
    }

    private List<Holiday> buildHolidays() {
	return Arrays.asList(Holiday.builder().id(1).name("holiday1").build(),
		Holiday.builder().id(2).name("holiday2").build());
    }

}
