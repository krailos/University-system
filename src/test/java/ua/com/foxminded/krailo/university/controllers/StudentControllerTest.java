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
	mockMvc = standaloneSetup(studentController).build();
    }

    @Test
    void WhenGetAllStudents_ThenAllStudentsReturned() throws Exception {
	List<Student> expected = getStudentsForTest();
	when(studentService.getAll()).thenReturn(expected);

	mockMvc.perform(get("/students/all")).andExpect(view().name("students/studentsAll")).andExpect(status().isOk())
		.andExpect(model().attribute("students", expected));

    }

    @Test
    void givenStudentId_WhenStudentGroup_ThenStudentGot() throws Exception {
	Student expected = getStudentsForTest().get(0);
	when(studentService.getById(1)).thenReturn(expected);

	mockMvc.perform(get("/students/findStudentById/1")).andExpect(view().name("students/studentView"))
		.andExpect(status().isOk()).andExpect(model().attribute("student", expected));

    }

    private List<Student> getStudentsForTest() {
	return Arrays.asList(Student.builder().id(1).firstName("Jon").build(),
		Student.builder().id(2).firstName("Tom").build());
    }

}
