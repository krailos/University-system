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
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Vocation;
import ua.com.foxminded.krailo.university.model.VocationKind;
import ua.com.foxminded.krailo.university.service.TeacherService;
import ua.com.foxminded.krailo.university.service.VocationService;

@ExtendWith(MockitoExtension.class)
class VocationControllerTest {

    @Mock
    private VocationService vocationService;
    @Mock
    private TeacherService teacherService;
    @InjectMocks
    private VocationController vocationController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
	mockMvc = standaloneSetup(vocationController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    void whenGetAllVocations_thenAllVocationsReturned() throws Exception {
	List<Vocation> expected = buildVocations();
	when(vocationService.getAll()).thenReturn(expected);

	mockMvc.perform(get("/vocations"))
        	.andExpect(view().name("vocations/all"))
        	.andExpect(status().isOk())
        	.andExpect(model().attribute("vocations", expected));

    }

    @Test
    void givenVocationId_whenGetVocation_thenVocationGot() throws Exception {
	Vocation expected = buildVocations().get(0);
	when(vocationService.getById(1)).thenReturn(expected);

	mockMvc.perform(get("/vocations/{id}", "1"))
        	.andExpect(view().name("vocations/vocation"))
        	.andExpect(status().isOk())
        	.andExpect(model().attribute("vocation", expected));

    }

    @Test
    void givenWrongVocatioId_whenGetVocation_thenEntityNotFoundExceptionThrown() throws Exception {
	when(vocationService.getById(1)).thenThrow(new EntityNotFoundException("entity not exist"));

	mockMvc.perform(get("/vocations/{id}", "1"))
        	.andExpect(view().name("errors/error"))
        	.andExpect(model().attribute("message", "entity not exist"));
    }

    @Test
    void whenCreateVocation_thenVocationWithTeachersReturned() throws Exception {
	List<Teacher> teachers = buildTeachers();
	when(teacherService.getAll()).thenReturn(teachers);

	mockMvc.perform(get("/vocations/create"))
        	.andExpect(view().name("vocations/edit"))
        	.andExpect(status().isOk())
        	.andExpect(model().attribute("teachers", teachers))
        	.andExpect(model().attributeExists("vocation"));
    }

    @Test
    void givenNewVocations_whenSaveVocation_thenVocationSaved() throws Exception {
	Vocation vocation = buildVocations().get(0);
	vocation.setId(0);

	mockMvc.perform(post("/vocations/save").flashAttr("vocation", vocation))
		.andExpect(view().name("redirect:/vocations"))
		.andExpect(status().is(302));
	
	verify(vocationService).create(vocation);
    }

    @Test
    void givenUpdatedVocation_whenUpdateVocation_thenVocationUpdated() throws Exception {
	Vocation vocation = buildVocations().get(0);

	mockMvc.perform(post("/vocations/save").flashAttr("vocation", vocation))
		.andExpect(view().name("redirect:/vocations"))
		.andExpect(status().is(302));
	
	verify(vocationService).update(vocation);
    }

    @Test
    void givenVocationId_whenEditVocation_thenVocationReturnedToEdite() throws Exception {
	List<Teacher> teachers = buildTeachers();
	when(teacherService.getAll()).thenReturn(teachers);
	Vocation vocation = buildVocations().get(0);
	when(vocationService.getById(1)).thenReturn(vocation);

	mockMvc.perform(get("/vocations/edit/{id}", "1"))
        	.andExpect(view().name("vocations/edit"))
        	.andExpect(status().isOk()).andExpect(model().attribute("vocation", vocation))
        	.andExpect(model().attribute("teachers", teachers));
    }

    @Test
    void whenDeleteVocation_thenVocationDeleted() throws Exception {
	Vocation vocation = buildVocations().get(0);
	when(vocationService.getById(1)).thenReturn(vocation);

	mockMvc.perform(post("/vocations/delete").param("id", "1"))
		.andExpect(view().name("redirect:/vocations"))
		.andExpect(status().is(302));
	
	verify(vocationService).delete(vocation);
    }

    private List<Vocation> buildVocations() {
	return Arrays.asList(Vocation.builder().id(1).kind(VocationKind.GENERAL).build(),
		Vocation.builder().id(2).kind(VocationKind.PREFERENTIAL).build());
    }

    private List<Teacher> buildTeachers() {
	return Arrays.asList(Teacher.builder().id(1).firstName("teacher1").build(),
		Teacher.builder().id(2).firstName("teacher2").build());
    }

}
