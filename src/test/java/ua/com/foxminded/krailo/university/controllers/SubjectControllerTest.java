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
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.service.SubjectService;

@ExtendWith(MockitoExtension.class)
class SubjectControllerTest {

    @Mock
    private SubjectService subjectService;
    @InjectMocks
    private SubjectController subjectController;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
	mockMvc = standaloneSetup(subjectController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    void WhenGetAllSubjects_ThenAllSubjectsReturned() throws Exception {
	List<Subject> expected = buildSubjects();
	when(subjectService.getAll()).thenReturn(expected);

	mockMvc.perform(get("/subjects")).andExpect(view().name("subjects/all")).andExpect(status().isOk())
		.andExpect(model().attribute("subjects", expected));
    }

    @Test
    void givenSubjecttId_WhenGetSubject_ThenSubjectGot() throws Exception {
	Subject expected = buildSubjects().get(0);
	when(subjectService.getById(1)).thenReturn(expected);

	mockMvc.perform(get("/subjects/1")).andExpect(view().name("subjects/subject")).andExpect(status().isOk())
		.andExpect(model().attribute("subject", expected));
    }

    @Test
    void givenWrongAudienceId_whenGetAudience_thenEntityNotFoundExceptionThrown() throws Exception {
	when(subjectService.getById(1)).thenThrow(new EntityNotFoundException("entity not exist"));

	mockMvc.perform(get("/subjects/1")).andExpect(view().name("errors/error"))
		.andExpect(model().attribute("message", "entity not exist"));
    }

    private List<Subject> buildSubjects() {
	return Arrays.asList(Subject.builder().id(1).name("subject1").build(),
		Subject.builder().id(2).name("subject2").build());
    }

}
