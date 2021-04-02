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
	mockMvc = standaloneSetup(lessonTimeController).build();
    }

    @Test
    void WhenGetAllLessonsTimes_ThenAllLessonsTimesReturned() throws Exception {
	List<LessonTime> expected = getLessonTimesForTest();
	when(lessonTimeService.getAll()).thenReturn(expected);

	mockMvc.perform(get("/lessonTimes/all")).andExpect(view().name("lessonTimes/lessonTimesAll"))
		.andExpect(status().isOk()).andExpect(model().attribute("lessonTimes", expected));

    }

    @Test
    void givenLessonTimeId_WhenGetLessonTime_ThenLessonTimeGot() throws Exception {
	LessonTime expected = getLessonTimesForTest().get(0);
	when(lessonTimeService.getById(1)).thenReturn(expected);

	mockMvc.perform(get("/lessonTimes/findLessonTimeById/1")).andExpect(view().name("lessonTimes/lessonTimeView"))
		.andExpect(status().isOk()).andExpect(model().attribute("lessonTime", expected));

    }

    private List<LessonTime> getLessonTimesForTest() {
	return Arrays.asList(LessonTime.builder().id(1).orderNumber("first").build(),
		LessonTime.builder().id(2).orderNumber("second").build());
    }

}
