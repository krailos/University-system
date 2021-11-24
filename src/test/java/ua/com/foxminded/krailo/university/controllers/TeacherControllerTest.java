package ua.com.foxminded.krailo.university.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.time.LocalDate;
import java.time.Year;
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
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Vocation;
import ua.com.foxminded.krailo.university.model.VocationKind;
import ua.com.foxminded.krailo.university.service.LessonService;
import ua.com.foxminded.krailo.university.service.SubjectService;
import ua.com.foxminded.krailo.university.service.TeacherService;
import ua.com.foxminded.krailo.university.service.VocationService;

@ExtendWith(MockitoExtension.class)
class TeacherControllerTest {

    @Mock
    private TeacherService teacherService;
    @Mock
    private SubjectService subjectService;
    @Mock
    private VocationService vocationService;
    @Mock
    private LessonService lessonService;

    @InjectMocks
    private TeacherController teacherController;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
	mockMvc = standaloneSetup(teacherController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    void whenGetAllTeachers_thenAllTeachersReturned() throws Exception {
	List<Teacher> expected = buildTeachers();
	when(teacherService.getAll()).thenReturn(expected);

	mockMvc.perform(get("/teachers"))
		.andExpect(view().name("teachers/all"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("teachers", expected));
    }

    @Test
    void givenTeacherId_whenGetTeacher_thenTeacherGot() throws Exception {
	Teacher expected = buildTeachers().get(0);
	when(teacherService.getById(1)).thenReturn(expected);

	mockMvc.perform(get("/teachers/{id}", "1"))
		.andExpect(view().name("teachers/teacher"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("teacher", expected));
    }

    @Test
    void givenWrongTeacherId_whenGetTeacher_thenEntityNotFoundExceptionThrown() throws Exception {
	when(teacherService.getById(1)).thenThrow(new EntityNotFoundException("entity not exist"));

	mockMvc.perform(get("/teachers/{id}", "1"))
		.andExpect(view().name("/errors/error"))
		.andExpect(model().attribute("message", "entity not exist"));
    }

    @Test
    void whenCreateTeacher_thenTeacherWithSubjectsReturned() throws Exception {
	List<Subject> subjects = buildSubjects();
	when(subjectService.getAll()).thenReturn(subjects);

	mockMvc.perform(get("/teachers/create"))
		.andExpect(view().name("teachers/edit"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("subjects", subjects))
		.andExpect(model().attributeExists("teacher"));
    }

    @Test
    void givenNewTeacher_whenSaveTeacher_thenTeacherSaved() throws Exception {
	Teacher teacher = buildTeachers().get(0);
	teacher.setId(0);

	mockMvc.perform(post("/teachers/save").flashAttr("teacher", teacher))
		.andExpect(view().name("redirect:/teachers"))
		.andExpect(status().is(302));
	
	verify(teacherService).create(teacher);
    }

    
    @Test
    void givenTeacherWhithWrongFields_whenSaveTeacher_thenFormWhithErrorsReturned() throws Exception {
	Teacher teacher = buildTeachers().get(0);
	teacher.setId(0);
	teacher.setFirstName(" ");
	teacher.setLastName(" ");
	teacher.setEmail("abc");
	teacher.setDegree(" ");
	teacher.setPhone("067");

	mockMvc.perform(post("/teachers/save").flashAttr("teacher", teacher))
		.andExpect(view().name("teachers/edit"))
		.andExpect(model().attributeHasFieldErrors("teacher", "firstName", "lastName", "email", "degree", "phone" ));
	
    }
    
    @Test
    void givenUpdatedTeacher_whenUpdateTeacher_thenTeacherUpdated() throws Exception {
	Teacher teacher = buildTeachers().get(0);

	mockMvc.perform(post("/teachers/save").flashAttr("teacher", teacher))
		.andExpect(view().name("redirect:/teachers"))
		.andExpect(status().is(302));
	
	verify(teacherService).create(teacher);
    }

    @Test
    void givenTeacherId_whenEditTeacher_thenTeacherReturnedToEdite() throws Exception {
	List<Subject> subjects = buildSubjects();
	when(subjectService.getAll()).thenReturn(subjects);
	Teacher teacher = buildTeachers().get(0);
	when(teacherService.getById(1)).thenReturn(teacher);

	mockMvc.perform(get("/teachers/edit/{id}", "1")).andExpect(view().name("teachers/edit"))
		.andExpect(status().isOk()).andExpect(model().attribute("teacher", teacher))
		.andExpect(model().attribute("subjects", subjects));
    }

    @Test
    void whenDeleteTeacher_thenTeacherDeleted() throws Exception {
	Teacher teacher = buildTeachers().get(0);
	when(teacherService.getById(1)).thenReturn(teacher);

	mockMvc.perform(post("/teachers/delete").param("id", "1"))
		.andExpect(view().name("redirect:/teachers"))
		.andExpect(status().is(302));
	
	verify(teacherService).delete(teacher);
    }

    @Test
    void whenGetVocationsByTeacher_thenVocationsReturned() throws Exception {
	List<Vocation> vocations = Arrays.asList(Vocation.builder().id(1).kind(VocationKind.GENERAL).build());
	when(vocationService.getByTeacherAndYear(buildTeachers().get(0), Year.from(LocalDate.now()))).thenReturn(vocations);
	Teacher teacher = buildTeachers().get(0);
	when(teacherService.getById(1)).thenReturn(teacher);

	mockMvc.perform(get("/teachers/vocations/{id}", "1").param("year", Year.from(LocalDate.now()).toString()))
		.andExpect(view().name("teachers/vocations"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("year", Year.from(LocalDate.now()).toString()))
		.andExpect(model().attribute("teacher", teacher))
		.andExpect(model().attribute("vocations", vocations));
    }

    @Test
    void whenGetScheduleByTeacher_thenScheduleReturned() throws Exception {
	Teacher teacher = buildTeachers().get(0);
	List<Lesson> lessons = Arrays.asList(Lesson.builder().id(1).date(LocalDate.now()).build());
	when(lessonService.getLessonsByTeacherByPeriod(teacher, LocalDate.now(), LocalDate.now().plusMonths(1)))
		.thenReturn(lessons);
	when(teacherService.getById(1)).thenReturn(teacher);

	mockMvc.perform(get("/teachers/schedule/{id}", "1").param("startDate", LocalDate.now().toString())
		.param("finishDate", LocalDate.now().plusMonths(1).toString()))
		.andExpect(view().name("teachers/schedule")).andExpect(status().isOk())
		.andExpect(model().attribute("startDate", LocalDate.now()))
		.andExpect(model().attribute("finishDate", LocalDate.now().plusMonths(1)))
		.andExpect(model().attribute("teacher", teacher))
		.andExpect(model().attribute("lessons", lessons));
    }
  

    private List<Teacher> buildTeachers() {
	return Arrays.asList(Teacher.builder().id(1).firstName("Tom").lastName("jon").phone("0670000001").degree("yes").email("abx@gm.com").build(),
		Teacher.builder().id(2).firstName("Tom").lastName("jon").phone("0670000001").degree("yes").email("abx@gm.com").build());
    }

    private List<Subject> buildSubjects() {
	return Arrays.asList(Subject.builder().id(1).name("subjecet1").build(),
		Subject.builder().id(2).name("subjecet2").build());
    }

}
