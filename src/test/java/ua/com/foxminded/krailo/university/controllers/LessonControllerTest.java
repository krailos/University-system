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
	mockMvc = standaloneSetup(lessonController).build();
    }

    @Test
    void WhenGetAllLessons_ThenAllLessonsReturned() throws Exception {
	List<Lesson> expected = buildLessons();
	when(lessonService.getAll()).thenReturn(expected);

	mockMvc.perform(get("/lessons")).andExpect(view().name("lessons/all")).andExpect(status().isOk())
		.andExpect(model().attribute("lessons", expected));

    }

    @Test
    void givenLessonId_WhenGetLesson_ThenLessonGot() throws Exception {
	Lesson expected = buildLessons().get(0);
	when(lessonService.getById(1)).thenReturn(expected);

	mockMvc.perform(get("/lessons/1")).andExpect(view().name("lessons/lesson"))
		.andExpect(status().isOk()).andExpect(model().attribute("lesson", expected));

    }

    private List<Lesson> buildLessons() {
	return Arrays.asList(Lesson.builder().id(1).subject(Subject.builder().id(1).name("subject1").build()).build(),
		Lesson.builder().id(2).subject(Subject.builder().id(2).name("subject2").build()).build());
    }

}
