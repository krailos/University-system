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
import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.service.LessonTimeService;

@ExtendWith(MockitoExtension.class)
class LessonTimeControllerTest {

    @Mock
    private LessonTimeService lessonTimeService;
    @InjectMocks
    private LessonTimeController lessonTimeController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
	mockMvc = standaloneSetup(lessonTimeController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    void whenGetAllLessonsTimes_thenAllLessonsTimesReturned() throws Exception {
	List<LessonTime> expected = buildLessonTimes();
	when(lessonTimeService.getAll()).thenReturn(expected);

	mockMvc.perform(get("/lessonTimes"))
		.andExpect(view().name("lessonTimes/all"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("lessonTimes", expected));

    }

    @Test
    void givenLessonTimeId_whenGetLessonTime_thenLessonTimeGot() throws Exception {
	LessonTime expected = buildLessonTimes().get(0);
	when(lessonTimeService.getById(1)).thenReturn(expected);

	mockMvc.perform(get("/lessonTimes/{id}", "1"))
		.andExpect(view().name("lessonTimes/lessonTime"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("lessonTime", expected));

    }

    @Test
    void givenWrongLessonTimeId_whenGetLessonTime_thenEntityNotFoundExceptionThrown() throws Exception {
	when(lessonTimeService.getById(1)).thenThrow(new EntityNotFoundException("entity not exist"));

	mockMvc.perform(get("/lessonTimes/{id}", "1"))
		.andExpect(view().name("/error"))
		.andExpect(model().attribute("message", "entity not exist"));
    }

    @Test
    void whenCreateLessonTime_thenLessonTimeReturned() throws Exception {

	mockMvc.perform(get("/lessonTimes/create"))
		.andExpect(view().name("lessonTimes/edit"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("lessonTime"));
    }

    @Test
    void givenNewLessonTime_whenSaveLessonTime_thenLessonTimeSaved() throws Exception {
	LessonTime lessonTime = buildLessonTimes().get(0);
	lessonTime.setId(0);

	mockMvc.perform(post("/lessonTimes/save").flashAttr("lessonTime", lessonTime))
		.andExpect(view().name("redirect:/lessonTimes"))
		.andExpect(status().is(302));
	
	verify(lessonTimeService).create(lessonTime);
    }

    @Test
    void givenUpdatedLessonTime_whenUpdateLessonTime_thenLessonTimeUpdated() throws Exception {
	LessonTime lessonTime = buildLessonTimes().get(0);

	mockMvc.perform(post("/lessonTimes/save").flashAttr("lessonTime", lessonTime))
		.andExpect(view().name("redirect:/lessonTimes"))
		.andExpect(status().is(302));
	
	verify(lessonTimeService).create(lessonTime);
    }

    @Test
    void givenLessonTimeWithBlankOrderNumber_whenSvaeLessonTime_thenLessonTimeFromWithErrorReturned() throws Exception {
	LessonTime lessonTime = buildLessonTimes().get(0);
	lessonTime.setOrderNumber(" ");

	mockMvc.perform(post("/lessonTimes/save").flashAttr("lessonTime", lessonTime))
		.andExpect(view().name("lessonTimes/edit"))
		.andExpect(model().attributeHasFieldErrors("lessonTime", "orderNumber"));	
    }
    
    @Test
    void givenLessonTimeId_whenEditLessonTime_thenLessonTimeReturnedToEdite() throws Exception {
	LessonTime lessonTime = buildLessonTimes().get(0);
	when(lessonTimeService.getById(1)).thenReturn(lessonTime);

	mockMvc.perform(get("/lessonTimes/edit/{id}", "1"))
		.andExpect(view().name("lessonTimes/edit"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("lessonTime", lessonTime));
    }

    @Test
    void whenDeleteLessonTime_thenLessonTimeDeleted() throws Exception {
	LessonTime lessonTime = buildLessonTimes().get(0);
	when(lessonTimeService.getById(1)).thenReturn(lessonTime);

	mockMvc.perform(post("/lessonTimes/delete").param("id", "1"))
		.andExpect(view().name("redirect:/lessonTimes"))
		.andExpect(status().is(302));
	
	verify(lessonTimeService).delete(lessonTime);
    }

    private List<LessonTime> buildLessonTimes() {
	return Arrays.asList(LessonTime.builder().id(1).orderNumber("first").build(),
		LessonTime.builder().id(2).orderNumber("second").build());
    }

}
