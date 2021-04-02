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

import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.service.SubjectService;
import ua.com.foxminded.krailo.university.service.TeacherService;

@ExtendWith(MockitoExtension.class)
class SubjectControllerTest {

    @Mock
    private SubjectService subjectService;
    @Mock
    private TeacherService teacherService;
    @InjectMocks
    private SubjectController subjectController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
	mockMvc = standaloneSetup(subjectController).build();
    }

    @Test
    void WhenGetAllSubjects_ThenAllSubjectsReturned() throws Exception {
	List<Subject> expected = getSubjectsForTest();
	when(subjectService.getAll()).thenReturn(expected);

	mockMvc.perform(get("/subjects/all")).andExpect(view().name("subjects/subjectsAll")).andExpect(status().isOk())
		.andExpect(model().attribute("subjects", expected));

    }

    @Test
    void givenSubjecttId_WhenGetSubject_ThenSubjectGot() throws Exception {
	Subject expected = getSubjectsForTest().get(0);
	when(subjectService.getById(1)).thenReturn(expected);
	when(teacherService.getBySubjectId(1))
		.thenReturn(Arrays.asList(Teacher.builder().id(1).firstName("teacher1").build()));

	mockMvc.perform(get("/subjects/findSubjectById/1")).andExpect(view().name("subjects/subjectView"))
		.andExpect(status().isOk()).andExpect(model().attribute("subject", expected));

    }

    private List<Subject> getSubjectsForTest() {
	return Arrays.asList(
		Subject.builder().id(1).name("subject1")
			.teachers(Arrays.asList(Teacher.builder().id(1).firstName("teacher1").build())).build(),
		Subject.builder().id(2).name("subject2")
			.teachers(Arrays.asList(Teacher.builder().id(1).firstName("teacher1").build())).build());
    }

}
