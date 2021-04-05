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
	mockMvc = standaloneSetup(holidayController).build();
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

	mockMvc.perform(get("/holidays/1")).andExpect(view().name("holidays/holiday"))
		.andExpect(status().isOk()).andExpect(model().attribute("holiday", expected));

    }

    private List<Holiday> buildHolidays() {
	return Arrays.asList(Holiday.builder().id(1).name("holiday1").build(),
		Holiday.builder().id(2).name("holiday2").build());
    }

}
