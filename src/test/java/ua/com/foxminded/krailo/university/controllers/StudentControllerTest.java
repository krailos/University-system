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
import ua.com.foxminded.krailo.university.model.Gender;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.service.GroupService;
import ua.com.foxminded.krailo.university.service.StudentService;
import ua.com.foxminded.krailo.university.util.Paging;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;
    @Mock
    private GroupService groupService;
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
	Paging paging = new Paging(2, 1, 4);
	when(studentService.getQuantity()).thenReturn(4);
	when(studentService.getByPage(paging)).thenReturn(expected);

	mockMvc.perform(get("/students").param("pageSize", "2")).andExpect(view().name("students/all"))
		.andExpect(status().isOk()).andExpect(model().attribute("students", expected));
    }

    @Test
    void whenGetAllStudentsWithParameters_thenRightPageWithStudentsReturned() throws Exception {
	List<Student> expected = buildStudents();
	Paging paging = new Paging(2, 3, 6);
	when(studentService.getByPage(paging)).thenReturn(expected);
	when(studentService.getQuantity()).thenReturn(6);

	mockMvc.perform(get("/students?pageSize=2&pageNumber=3")).andExpect(view().name("students/all"))
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
    void givenWrongStudentId_whenGetStudent_thenEntityNotFoundExceptionThrown() throws Exception {
	when(studentService.getById(1)).thenThrow(new EntityNotFoundException("entity not exist"));

	mockMvc.perform(get("/students/1")).andExpect(view().name("errors/error"))
		.andExpect(model().attribute("message", "entity not exist"));
    }

    @Test
    void whenCreateStudent_thenReturnNewStudentAndAllGroups() throws Exception {
	List<Group> expected = buildGroups();
	when(groupService.getAll()).thenReturn(expected);

	mockMvc.perform(get("/students/create")).andExpect(view().name("students/edit"))
		.andExpect(model().attribute("groups", expected));
    }

    @Test
    void givenStudent_whenSaveStudent_thenCreateMethodCalled() throws Exception {

	mockMvc.perform(post("/students/save").flashAttr("student", buildStudent()))
		.andExpect(view().name("redirect:/students"));

	verify(studentService).create(buildStudent());
    }

    @Test
    void givenStudent_whenEditStudent_thenReturnStudentAndAllGroups() throws Exception {
	when(studentService.getById(1)).thenReturn(buildStudent());
	when(groupService.getAll()).thenReturn(buildGroups());

	mockMvc.perform(get("/students/edit/1")).andExpect(view().name("students/edit"))
		.andExpect(model().attribute("student", buildStudent()))
		.andExpect(model().attribute("groups", buildGroups()));
    }

    @Test
    void givenUpdatedStudent_whenSaveStudent_thenUpdateMethodCalled() throws Exception {
	Student student = buildStudent();
	student.setId(1);
	mockMvc.perform(post("/students/save").flashAttr("student", student))
		.andExpect(view().name("redirect:/students"));

	verify(studentService).update(student);
    }

    @Test
    void givenStudent_whenDeleteStudent_thenDeleteMethodCalled() throws Exception {
	when(studentService.getById(1)).thenReturn(buildStudent());

	mockMvc.perform(get("/students/delete/1")).andExpect(view().name("redirect:/students"));

	verify(studentService).delete(buildStudent());
    }

    private List<Student> buildStudents() {
	return Arrays.asList(Student.builder().id(1).firstName("Jon").build(),
		Student.builder().id(2).firstName("Tom").build());
    }

    private List<Group> buildGroups() {
	return Arrays.asList(Group.builder().id(1).name("first").build(), Group.builder().id(2).name("second").build());
    }

    private Student buildStudent() {
	return Student.builder().firstName("Tom").gender(Gender.MALE).group(Group.builder().id(1).build()).build();
    }

}
