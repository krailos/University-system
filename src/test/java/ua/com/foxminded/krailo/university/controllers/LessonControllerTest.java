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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;

import ua.com.foxminded.krailo.university.controllers.exception.ControllerExceptionHandler;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.exception.NoTeachersForSubstitute;
import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.service.AudienceService;
import ua.com.foxminded.krailo.university.service.GroupService;
import ua.com.foxminded.krailo.university.service.LessonService;
import ua.com.foxminded.krailo.university.service.LessonTimeService;
import ua.com.foxminded.krailo.university.service.SubjectService;
import ua.com.foxminded.krailo.university.service.TeacherService;

@ExtendWith(MockitoExtension.class)
class LessonControllerTest {

    @Mock
    private LessonService lessonService;
    @Mock
    private LessonTimeService lessonTimeService;
    @Mock
    private SubjectService subjectService;
    @Mock
    private AudienceService audienceService;
    @Mock
    private GroupService groupService;
    @Mock
    private TeacherService teacherService;
    @InjectMocks
    private LessonController lessonController;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
	mockMvc = standaloneSetup(lessonController).setControllerAdvice(new ControllerExceptionHandler())
		.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    }

    @Test
    void whenGetAllLessons_thenFirstPageLessonsReturned() throws Exception {
	int pageNo = 0;
	int pageSize = 3;
	int allLessonsCount = 6;
	List<Lesson> lessons = new ArrayList<>();
	lessons.addAll(buildLessons());
	Pageable pageable = PageRequest.of(pageNo, pageSize);
	Page<Lesson> expected = new PageImpl<>(lessons, pageable, allLessonsCount);
	when(lessonService.getSelectedPage(pageable)).thenReturn(expected);

	mockMvc.perform(get("/lessons")
		.param("page", "0")
		.param("size", "3"))
		.andExpect(view().name("lessons/all"))
		.andExpect(model().attribute("lessonsPage", expected));
    }

    @Test
    void whenGetAllLessonsWithParameters_thenRightPageWithLessonsReturned() throws Exception {
	int pageNo = 1;
	int pageSize = 3;
	int allLessonsCount = 6;
	List<Lesson> lessons = new ArrayList<>();
	lessons.addAll(buildLessons());
	Pageable pageable = PageRequest.of(pageNo, pageSize);
	Page<Lesson> expected = new PageImpl<>(lessons, pageable, allLessonsCount);
	when(lessonService.getSelectedPage(pageable)).thenReturn(expected);

	mockMvc.perform(get("/lessons")
		.param("page", "1")
		.param("size", "3"))
		.andExpect(view().name("lessons/all"))
		.andExpect(model().attribute("lessonsPage", expected));
    }

    @Test
    void givenLessonId_whenGetLesson_thenLessonGot() throws Exception {
	Lesson expected = buildLessons().get(0);
	when(lessonService.getById(1)).thenReturn(expected);

	mockMvc.perform(get("/lessons/{id}", "1")
		.param("pageSize", "2"))
		.andExpect(view().name("lessons/lesson"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("lesson", expected));

    }

    @Test
    void givenWrongLessonId_whenGetLesson_thenEntityNotFoundExceptionThrown() throws Exception {
	when(lessonService.getById(1)).thenThrow(new EntityNotFoundException("entity not exist"));

	mockMvc.perform(get("/lessons/{id}", "1"))
		.andExpect(view().name("/error"))
		.andExpect(model().attribute("message", "entity not exist"));
    }

    @Test
    void whenCreateLesson_thenLessonWithTimesSubjectsAudiencesTeachersGroupsReturned() throws Exception {
	List<LessonTime> lessonTimes = buildLessonTimes();
	when(lessonTimeService.getAll()).thenReturn(lessonTimes);
	List<Subject> subjects = buildSubjects();
	when(subjectService.getAll()).thenReturn(subjects);
	List<Audience> audiences = buildAudiences();
	when(audienceService.getAll()).thenReturn(audiences);
	List<Group> groups = buildGroups();
	when(groupService.getAll()).thenReturn(groups);
	List<Teacher> teachers = buildTeachers();
	when(teacherService.getAll()).thenReturn(teachers);

	mockMvc.perform(get("/lessons/create")).andExpect(view().name("lessons/edit")).andExpect(status().isOk())
		.andExpect(model().attribute("lesson", new Lesson())).andExpect(model().attribute("subjects", subjects))
		.andExpect(model().attribute("lessonTimes", lessonTimes))
		.andExpect(model().attribute("audiences", audiences)).andExpect(model().attribute("teachers", teachers))
		.andExpect(model().attribute("groups", groups));
    }

    @Test
    void givenNewLesson_whenSaveLesson_thenLessonSaved() throws Exception {
	Lesson lesson = buildLessons().get(0);
	lesson.setId(0);
	LessonTime lessonTime = buildLessonTimes().get(0);
	when(lessonTimeService.getById(1)).thenReturn(lessonTime);
	Subject subject = buildSubjects().get(0);
	when(subjectService.getById(1)).thenReturn(subject);
	Audience audience = buildAudiences().get(0);
	when(audienceService.getById(1)).thenReturn(audience);
	Group group = buildGroups().get(0);
	when(groupService.getById(1)).thenReturn(group);
	Teacher teacher = buildTeachers().get(0);
	when(teacherService.getById(1)).thenReturn(teacher);

	mockMvc.perform(post("/lessons/save")
		.flashAttr("lesson", lesson))
		.andExpect(view().name("redirect:/lessons"))
		.andExpect(status().is(302));

	verify(lessonService).create(lesson);
    }

    @Test
    void givenUpdatedLesson_whenUpdateLesson_thenLessonUpdated() throws Exception {
	Lesson lesson = buildLessons().get(0);
	LessonTime lessonTime = buildLessonTimes().get(0);
	when(lessonTimeService.getById(1)).thenReturn(lessonTime);
	Subject subject = buildSubjects().get(0);
	when(subjectService.getById(1)).thenReturn(subject);
	Audience audience = buildAudiences().get(0);
	when(audienceService.getById(1)).thenReturn(audience);
	Group group = buildGroups().get(0);
	when(groupService.getById(1)).thenReturn(group);
	Teacher teacher = buildTeachers().get(0);
	when(teacherService.getById(1)).thenReturn(teacher);

	mockMvc.perform(post("/lessons/save")
		.flashAttr("lesson", lesson))
		.andExpect(view().name("redirect:/lessons"))
		.andExpect(status().is(302));

	verify(lessonService).create(lesson);
    }

    @Test
    void givenLessonId_whenEditLesson_thenLessonReturnedToEdite() throws Exception {
	Lesson lesson = buildLessons().get(0);
	when(lessonService.getById(1)).thenReturn(lesson);
	List<LessonTime> lessonTimes = buildLessonTimes();
	when(lessonTimeService.getAll()).thenReturn(lessonTimes);
	List<Subject> subjects = buildSubjects();
	when(subjectService.getAll()).thenReturn(subjects);
	List<Audience> audiences = buildAudiences();
	when(audienceService.getAll()).thenReturn(audiences);
	List<Group> groups = buildGroups();
	when(groupService.getAll()).thenReturn(groups);
	List<Teacher> teachers = buildTeachers();
	when(teacherService.getAll()).thenReturn(teachers);

	mockMvc.perform(get("/lessons/edit/{id}", "1")).andExpect(view().name("lessons/edit"))
		.andExpect(status().isOk()).andExpect(model().attributeExists("lesson"))
		.andExpect(model().attribute("subjects", subjects))
		.andExpect(model().attribute("lessonTimes", lessonTimes))
		.andExpect(model().attribute("audiences", audiences)).andExpect(model().attribute("teachers", teachers))
		.andExpect(model().attribute("groups", groups));
    }

    @Test
    void whenDeleteLesson_thenLessonDeleted() throws Exception {
	Lesson lesson = buildLessons().get(0);
	when(lessonService.getById(1)).thenReturn(lesson);

	mockMvc.perform(post("/lessons/delete")
		.param("id", "1"))
		.andExpect(view().name("redirect:/lessons"))
		.andExpect(status().is(302));

	verify(lessonService).delete(lesson);
    }

    @Test
    void givenTeacherStartDateFinishDate_whenFindTeacherForSubstitute_thensubstituteTeacherFormGot() throws Exception {
	List<Teacher> teachers = buildTeachers();
	LocalDate startDate = LocalDate.now();
	LocalDate finishDate = LocalDate.now().plusWeeks(1);
	Teacher teacher = buildTeachers().get(0);
	when(teacherService.findTeachersForSubstitute(teacher, startDate, finishDate)).thenReturn(teachers);
	when(teacherService.getById(1)).thenReturn(teacher);

	mockMvc.perform(post("/lessons/findTeacherForSubstitute").param("startDate", startDate.toString())
		.param("finishDate", finishDate.toString()).param("teacherId", "1"))
		.andExpect(view().name("lessons/substituteTeacher"))
		.andExpect(model().attribute("startDate", startDate))
		.andExpect(model().attribute("finishDate", finishDate))
		.andExpect(model().attribute("teachersForSubstitite", teachers))
		.andExpect(model().attribute("teacher", teacher));
    }

    @Test
    void givenNoTeachersForSubstitute_whenFindTeacherForSubstitute_thenNoTeachersForSubstituteThrown()
	    throws Exception {
	LocalDate startDate = LocalDate.now();
	LocalDate finishDate = LocalDate.now().plusWeeks(1);
	Teacher teacher = buildTeachers().get(0);
	when(teacherService.findTeachersForSubstitute(teacher, startDate, finishDate))
		.thenThrow(new NoTeachersForSubstitute("there is no free teachers"));
	when(teacherService.getById(1)).thenReturn(teacher);

	mockMvc.perform(post("/lessons/findTeacherForSubstitute").param("startDate", startDate.toString())
		.param("finishDate", finishDate.toString()).param("teacherId", "1"))
		.andExpect(view().name("/error"))
		.andExpect(model().attribute("message", "there is no free teachers"));
    }

    @Test
    void whenSubstituteTeacherGetForm_thensubstituteTeacherFormGot() throws Exception {
	List<Teacher> teachers = buildTeachers();
	when(teacherService.getAll()).thenReturn(teachers);

	mockMvc.perform(get("/lessons/substituteTeacherForm")).andExpect(view().name("lessons/substituteTeacherForm"))
		.andExpect(model().attribute("teachers", teachers));
    }

    @Test
    void givenOldTeacherIdNewTeacherIdPeriodSubstitute_whenSubstituteTeacher_thenTeaceherSubstituted()
	    throws Exception {
	LocalDate startDate = LocalDate.now();
	LocalDate finishDate = LocalDate.now().plusWeeks(1);
	Teacher teacher = buildTeachers().get(0);
	when(teacherService.getById(1)).thenReturn(teacher);

	mockMvc.perform(post("/lessons/substituteTeacher").param("startDate", startDate.toString())
		.param("finishDate", finishDate.toString()).param("oldId", "1").param("newId", "1"))
		.andExpect(view().name("redirect:/lessons"));
	verify(lessonService).substituteTeacher(teacher, teacher, startDate, finishDate);
    }

    private List<Lesson> buildLessons() {
	return Arrays.asList(Lesson.builder().id(1).lessonTime(LessonTime.builder().id(1).build())
		.audience(Audience.builder().id(1).build()).subject(Subject.builder().id(1).name("subject1").build())
		.teacher(Teacher.builder().id(1).build()).groups(buildGroups()).build(),
		Lesson.builder().id(2).lessonTime(LessonTime.builder().id(2).build())
			.audience(Audience.builder().id(2).build())
			.subject(Subject.builder().id(2).name("subject1").build())
			.teacher(Teacher.builder().id(2).build()).groups(buildGroups()).build());
    }

    private List<LessonTime> buildLessonTimes() {
	return Arrays.asList(LessonTime.builder().id(1).orderNumber("first").build(),
		LessonTime.builder().id(2).orderNumber("second").build());
    }

    private List<Subject> buildSubjects() {
	return Arrays.asList(Subject.builder().id(1).name("subjecet1").build(),
		Subject.builder().id(2).name("subjecet2").build());
    }

    private List<Audience> buildAudiences() {
	return Arrays.asList(Audience.builder().id(1).number("1").build(),
		Audience.builder().id(2).number("2").build());
    }

    private List<Teacher> buildTeachers() {
	return Arrays.asList(Teacher.builder().id(1).firstName("Jon").build(),
		Teacher.builder().id(2).firstName("Tom").build());
    }

    private List<Group> buildGroups() {
	return Arrays.asList(Group.builder().id(1).name("group1").build(),
		Group.builder().id(2).name("group2").build());
    }

}
