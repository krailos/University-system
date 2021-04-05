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

import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.service.TeacherService;

@ExtendWith(MockitoExtension.class)
class TeacherControllerTest {

    @Mock
    private TeacherService teacherService;
    @InjectMocks
    private TeacherController teacherController;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
	mockMvc = standaloneSetup(teacherController).build();
    }

    @Test
    void WhenGetAllTeachers_ThenAllTeachersReturned() throws Exception {
	List<Teacher> expected = buildTeachers();
	when(teacherService.getAll()).thenReturn(expected);

	mockMvc.perform(get("/teachers")).andExpect(view().name("teachers/all")).andExpect(status().isOk())
		.andExpect(model().attribute("teachers", expected));
    }

    @Test
    void givenTeacherId_WhenGetTeacher_ThenTeacherGot() throws Exception {
	Teacher expected = buildTeachers().get(0);
	when(teacherService.getById(1)).thenReturn(expected);

	mockMvc.perform(get("/teachers/1")).andExpect(view().name("teachers/teacher"))
		.andExpect(status().isOk()).andExpect(model().attribute("teacher", expected));
    }

    private List<Teacher> buildTeachers() {
	return Arrays.asList(Teacher.builder().id(1).firstName("Jon").build(),
		Teacher.builder().id(2).firstName("Tom").build());
    }

}
