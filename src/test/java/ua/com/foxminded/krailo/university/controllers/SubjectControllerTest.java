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
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.service.SubjectService;
import ua.com.foxminded.krailo.university.service.TeacherService;

@ExtendWith(MockitoExtension.class)
class SubjectControllerTest {

    @Mock
    private SubjectService subjectService;
    @InjectMocks
    private SubjectController subjectController;
    @Mock
    private TeacherService teacherService;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
	mockMvc = standaloneSetup(subjectController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    void whenGetAllSubjects_thenAllSubjectsReturned() throws Exception {
	List<Subject> expected = buildSubjects();
	when(subjectService.getAll()).thenReturn(expected);

	mockMvc.perform(get("/subjects"))
        	.andExpect(view().name("subjects/all"))
        	.andExpect(status().isOk())
		.andExpect(model().attribute("subjects", expected));
    }

    @Test
    void givenSubjecttId_whenGetSubject_thenSubjectGot() throws Exception {
	Subject expected = buildSubjects().get(0);
	when(subjectService.getById(1)).thenReturn(expected);

	mockMvc.perform(get("/subjects/{id}", "1")).andExpect(view().name("subjects/subject"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("subject", expected));
    }

    @Test
    void givenWrongSubjectId_whenGetSubject_thenEntityNotFoundExceptionThrown() throws Exception {
	when(subjectService.getById(1)).thenThrow(new EntityNotFoundException("entity not exist"));

	mockMvc.perform(get("/subjects/{id}", "1"))
		.andExpect(view().name("/error"))
		.andExpect(model().attribute("message", "entity not exist"));
    }

    @Test
    void whenCreateVocation_thenVocationWithTeachersReturned() throws Exception {
	List<Teacher> teachers = buildTeachers();
	when(teacherService.getAll()).thenReturn(teachers);

	mockMvc.perform(get("/subjects/create"))
		.andExpect(view().name("subjects/edit"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("teachers", teachers))
		.andExpect(model().attributeExists("subject"));
    }

    @Test
    void givenNewSubject_whenSaveSubject_thenSubjectSaved() throws Exception {
	Subject subject = buildSubjects().get(0);
	subject.setId(0);

	mockMvc.perform(post("/subjects/save").flashAttr("subject", subject))
		.andExpect(view().name("redirect:/subjects"))
		.andExpect(status().is(302));
	verify(subjectService).create(subject);
    }
    
    @Test
    void givenSubjectWithErrorFields_whenSaveSubject_thenFormWhithErrorReturned() throws Exception {
	Subject subject = buildSubjects().get(0);
	subject.setId(0);
	subject.setName(" ");
	subject.setDescription("1234567891011");

	mockMvc.perform(post("/subjects/save").flashAttr("subject", subject))
		.andExpect(view().name("subjects/edit"))
		.andExpect(model().attributeHasFieldErrors("subject", "name", "description"));
    }

    @Test
    void givenUpdatedSubject_whenUpdateSubject_thenSubjectUpdated() throws Exception {
	Subject subject = buildSubjects().get(0);

	mockMvc.perform(post("/subjects/save").flashAttr("subject", subject))
		.andExpect(view().name("redirect:/subjects"))
		.andExpect(status().is(302));
	verify(subjectService).create(subject);
    }

    @Test
    void givenSubjectId_whenEditSubject_thenSubjectReturnedToEdite() throws Exception {
	List<Teacher> teachers = buildTeachers();
	when(teacherService.getAll()).thenReturn(teachers);
	Subject subject = buildSubjects().get(0);
	when(subjectService.getById(1)).thenReturn(subject);

	mockMvc.perform(get("/subjects/edit/{id}", "1"))
		.andExpect(view().name("subjects/edit"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("subject", subject))
		.andExpect(model().attribute("teachers", teachers));
    }

    @Test
    void whenDeleteVocation_thenVocationDeleted() throws Exception {
	Subject subject = buildSubjects().get(0);
	when(subjectService.getById(1)).thenReturn(subject);

	mockMvc.perform(post("/subjects/delete").param("id", "1"))
		.andExpect(view().name("redirect:/subjects"))
		.andExpect(status().is(302));
	
	verify(subjectService).delete(subject);
    }

    private List<Subject> buildSubjects() {
	return Arrays.asList(Subject.builder().id(1).name("subject1").build(),
		Subject.builder().id(2).name("subject2").build());
    }

    private List<Teacher> buildTeachers() {
	return Arrays.asList(Teacher.builder().id(1).firstName("teacher1").build(),
		Teacher.builder().id(2).firstName("teacher2").build());
    }

}
