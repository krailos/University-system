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
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.service.StudentService;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
	mockMvc = standaloneSetup(studentController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    void WhenGetAllStudents_ThenAllStudentsReturned() throws Exception {
	List<Student> expected = buildStudents();
	when(studentService.getByPage(2, 0)).thenReturn(expected);

	mockMvc.perform(get("/students")).andExpect(view().name("students/all")).andExpect(status().isOk())
		.andExpect(model().attribute("students", expected));
    }

    @Test
    void whenGetAllLessonsWithParameters_thenRightPageWithLessonsReturned() throws Exception {
	List<Student> expected = buildStudents();
	when(studentService.getByPage(2, 4)).thenReturn(expected);
	when(studentService.getQuantity()).thenReturn(6);

	mockMvc.perform(get("/students?pageSize=2&pageId=3")).andExpect(view().name("students/all"))
		.andExpect(status().isOk()).andExpect(model().attribute("students", expected))
		.andExpect(model().attribute("pageQuantity", 3));
    }

    @Test
    void givenStudentId_WhenGetStudent_ThenStudentGot() throws Exception {
	Student expected = buildStudents().get(0);
	when(studentService.getById(1)).thenReturn(expected);

	mockMvc.perform(get("/students/1")).andExpect(view().name("students/student")).andExpect(status().isOk())
		.andExpect(model().attribute("student", expected));
    }

    @Test
    void givenWrongAudienceId_whenGetAudience_thenEntityNotFoundExceptionThrown() throws Exception {
	when(studentService.getById(1)).thenThrow(new EntityNotFoundException("entity not exist"));

	mockMvc.perform(get("/students/1")).andExpect(view().name("errors/error"))
		.andExpect(model().attribute("message", "entity not exist"));
    }

    private List<Student> buildStudents() {
	return Arrays.asList(Student.builder().id(1).firstName("Jon").build(),
		Student.builder().id(2).firstName("Tom").build());
    }

}
