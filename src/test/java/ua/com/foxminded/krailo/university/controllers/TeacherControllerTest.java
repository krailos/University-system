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
    void WhenGetAllTeachers_ThenAllTeachersReturned() throws Exception {
	List<Teacher> expected = buildTeachers();
	when(teacherService.getAll()).thenReturn(expected);

	mockMvc.perform(get("/teachers"))
		.andExpect(view().name("teachers/all"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("teachers", expected));
    }

    @Test
    void givenTeacherId_WhenGetTeacher_ThenTeacherGot() throws Exception {
	Teacher expected = buildTeachers().get(0);
	when(teacherService.getById(1)).thenReturn(expected);

	mockMvc.perform(get("/teachers/1"))
		.andExpect(view().name("teachers/teacher"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("teacher", expected));
    }

    @Test
    void givenWrongTeacherId_whenGetTeacher_thenEntityNotFoundExceptionThrown() throws Exception {
	when(teacherService.getById(1)).thenThrow(new EntityNotFoundException("entity not exist"));

	mockMvc.perform(get("/teachers/1"))
		.andExpect(view().name("errors/error"))
		.andExpect(model().attribute("message", "entity not exist"));
    }

    @Test
    void WhenCreateTeacher_ThenTeacherWithSubjectsReturned() throws Exception {
	List<Subject> subjects = buildSubjects();
	when(subjectService.getAll()).thenReturn(subjects);

	mockMvc.perform(get("/teachers/create"))
		.andExpect(view().name("teachers/edit"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("subjects", subjects))
		.andExpect(model().attributeExists("teacher"));
    }

    @Test
    void givenNewTeacher_WhenSaveTeacher_ThenTeacherSaved() throws Exception {
	Teacher teacher = new Teacher();

	mockMvc.perform(post("/teachers/save").flashAttr("teacher", teacher))
		.andExpect(view().name("redirect:/teachers"))
		.andExpect(status().is(302));
	
	verify(teacherService).create(teacher);
    }

    @Test
    void givenUpdatedTeacher_whenUpdateTeacher_ThenTeacherUpdated() throws Exception {
	Teacher teacher = buildTeachers().get(0);

	mockMvc.perform(post("/teachers/save").flashAttr("teacher", teacher))
		.andExpect(view().name("redirect:/teachers"))
		.andExpect(status().is(302));
	
	verify(teacherService).update(teacher);
    }

    @Test
    void givenTeacherId_whenEditTeacher_ThenTeacherReturnedToEdite() throws Exception {
	List<Subject> subjects = buildSubjects();
	when(subjectService.getAll()).thenReturn(subjects);
	Teacher teacher = buildTeachers().get(0);
	when(teacherService.getById(1)).thenReturn(teacher);

	mockMvc.perform(get("/teachers/edit/{id}", "1")).andExpect(view().name("teachers/edit"))
		.andExpect(status().isOk()).andExpect(model().attribute("teacher", teacher))
		.andExpect(model().attribute("subjects", subjects));
    }

    @Test
    void whenDeleteTeacher_ThenTeacherDeleted() throws Exception {
	Teacher teacher = buildTeachers().get(0);
	when(teacherService.getById(1)).thenReturn(teacher);

	mockMvc.perform(post("/teachers/delete").param("id", "1"))
		.andExpect(view().name("redirect:/teachers"))
		.andExpect(status().is(302));
	
	verify(teacherService).delete(teacher);
    }

    @Test
    void whenGetVocationsByTeacher_ThenVocationsReturned() throws Exception {
	List<Vocation> vocations = Arrays.asList(Vocation.builder().id(1).kind(VocationKind.GENERAL).build());
	when(vocationService.getByTeacherIdAndYear(1, Year.from(LocalDate.now()))).thenReturn(vocations);
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
    void whenGetScheduleByTeacher_ThenScheduleReturned() throws Exception {
	Teacher teacher = buildTeachers().get(0);
	List<Lesson> lessons = Arrays.asList(Lesson.builder().id(1).date(LocalDate.now()).build());
	when(lessonService.getLessonsForTeacherByPeriod(teacher, LocalDate.now(), LocalDate.now().plusMonths(1)))
		.thenReturn(lessons);
	when(teacherService.getById(1)).thenReturn(teacher);

	mockMvc.perform(get("/teachers/schedule/{id}", "1").param("startDate", LocalDate.now().toString())
		.param("finishDate", LocalDate.now().plusMonths(1).toString()))
		.andExpect(view().name("teachers/schedule")).andExpect(status().isOk())
		.andExpect(model().attribute("startDate", LocalDate.now()))
		.andExpect(model().attribute("finishDate", LocalDate.now().plusMonths(1)))
		.andExpect(model().attribute("teacher", teacher)).andExpect(model().attribute("lessons", lessons));
    }

    private List<Teacher> buildTeachers() {
	return Arrays.asList(Teacher.builder().id(1).firstName("Jon").build(),
		Teacher.builder().id(2).firstName("Tom").build());
    }

    private List<Subject> buildSubjects() {
	return Arrays.asList(Subject.builder().id(1).name("subjecet1").build(),
		Subject.builder().id(2).name("subjecet2").build());
    }

}
