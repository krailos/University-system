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
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.service.LessonService;

@ExtendWith(MockitoExtension.class)
class LessonControllerTest {

    @Mock
    private LessonService lessonService;
    @InjectMocks
    private LessonController lessonController;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
	mockMvc = standaloneSetup(lessonController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    void WhenGetAllLessons_ThenFirstPageLessonsReturned() throws Exception {
	List<Lesson> expected = buildLessons();
	when(lessonService.getByPage(2, 0)).thenReturn(expected);

	mockMvc.perform(get("/lessons")).andExpect(view().name("lessons/all")).andExpect(status().isOk())
		.andExpect(model().attribute("lessons", expected));
    }

    @Test
    void whenGetAllLessonsWithParameters_thenRightPageWithLessonsReturned() throws Exception {
	List<Lesson> expected = buildLessons();
	when(lessonService.getByPage(2, 4)).thenReturn(expected);
	when(lessonService.getQuantity()).thenReturn(6);

	mockMvc.perform(get("/lessons?pageSize=2&pageId=3")).andExpect(view().name("lessons/all"))
		.andExpect(status().isOk()).andExpect(model().attribute("lessons", expected))
		.andExpect(model().attribute("pageQuantity", 3));
    }

    @Test
    void givenLessonId_WhenGetLesson_ThenLessonGot() throws Exception {
	Lesson expected = buildLessons().get(0);
	when(lessonService.getById(1)).thenReturn(expected);

	mockMvc.perform(get("/lessons/1")).andExpect(view().name("lessons/lesson")).andExpect(status().isOk())
		.andExpect(model().attribute("lesson", expected));

    }

    @Test
    void givenWrongAudienceId_whenGetAudience_thenEntityNotFoundExceptionThrown() throws Exception {
	when(lessonService.getById(1)).thenThrow(new EntityNotFoundException("entity not exist"));

	mockMvc.perform(get("/lessons/1")).andExpect(view().name("errors/error"))
		.andExpect(model().attribute("message", "entity not exist"));
    }

    private List<Lesson> buildLessons() {
	return Arrays.asList(Lesson.builder().id(1).subject(Subject.builder().id(1).name("subject1").build()).build(),
		Lesson.builder().id(2).subject(Subject.builder().id(2).name("subject2").build()).build());
    }

}
