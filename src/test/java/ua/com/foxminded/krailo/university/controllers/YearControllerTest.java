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
import ua.com.foxminded.krailo.university.model.Year;
import ua.com.foxminded.krailo.university.service.SubjectService;
import ua.com.foxminded.krailo.university.service.YearService;

@ExtendWith(MockitoExtension.class)
class YearControllerTest {

    @Mock
    private YearService yearService;
    @Mock
    private SubjectService subjectService;
    @InjectMocks
    private YearController yearController;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
	mockMvc = standaloneSetup(yearController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    void whenGetAllYears_thenAllYearsReturned() throws Exception {
	List<Year> expected = buildYaers();
	when(yearService.getAll()).thenReturn(expected);

	mockMvc.perform(get("/years"))
	.andExpect(view().name("years/all"))
	.andExpect(status().isOk())
	.andExpect(model().attribute("years", expected));

    }

    @Test
    void givenWrongYearId_whenGetYears_thenEntityNotFoundExceptionThrown() throws Exception {
	when(yearService.getById(1)).thenThrow(new EntityNotFoundException("entity not exist"));

	mockMvc.perform(get("/years/{id}", "1"))
	.andExpect(view().name("/errors/error"))
	.andExpect(model().attribute("message", "entity not exist"));
    }

    @Test
    void givenYearId_whenGetYear_thenYearGot() throws Exception {
	Year expected = buildYaers().get(0);
	when(yearService.getById(1)).thenReturn(expected);

	mockMvc.perform(get("/years/{id}", "1"))
	.andExpect(view().name("years/year"))
	.andExpect(status().isOk())
	.andExpect(model().attribute("year", expected));

    }

    @Test
    void whenCreateYear_thenYearWithSubjectsReturned() throws Exception {
	List<Subject> subjects = buildSubjects();
	when(subjectService.getAll()).thenReturn(subjects);

	mockMvc.perform(get("/years/create"))
	.andExpect(view().name("years/edit"))
	.andExpect(status().isOk())
	.andExpect(model().attribute("subjects", subjects))
	.andExpect(model().attributeExists("year"));
    }

    @Test
    void givenNewYear_whenSaveYear_thenYearSaved() throws Exception {
	Year year = buildYaers().get(0);
	year.setId(0);

	mockMvc.perform(post("/years/save").flashAttr("year", year))
	.andExpect(view().name("redirect:/years"))
	.andExpect(status().is(302));
	verify(yearService).create(year);
    }
    
    @Test
    void givenYearWhithWrongField_whenSaveYear_thenFormWhithErrorsReturned() throws Exception {
	Year year = buildYaers().get(0);
	year.setId(0);
	year.setName(" ");

	mockMvc.perform(post("/years/save").flashAttr("year", year))
	.andExpect(view().name("years/edit"))
	.andExpect(model().attributeHasFieldErrors("year", "name"))
	.andExpect(status().is(200));
    }

    @Test
    void givenUpdatedYear_whenUpdateYear_thenYeatUpdated() throws Exception {
	Year year = buildYaers().get(0);

	mockMvc.perform(post("/years/save").flashAttr("year", year))
	.andExpect(view().name("redirect:/years"))
	.andExpect(status().is(302));
	verify(yearService).create(year);
    }

    @Test
    void givenYearId_whenEditYear_thenYearReturnedToEdite() throws Exception {
	List<Subject> subjects = buildSubjects();
	when(subjectService.getAll()).thenReturn(subjects);
	Year year = buildYaers().get(0);
	when(yearService.getById(1)).thenReturn(year);

	mockMvc.perform(get("/years/edit/{id}", "1"))
	.andExpect(view().name("years/edit"))
	.andExpect(status().isOk())
	.andExpect(model().attribute("year", year))
	.andExpect(model().attribute("subjects", subjects));
    }

    @Test
    void whenDeleteYear_thenYearDeleted() throws Exception {
	Year year = buildYaers().get(0);
	when(yearService.getById(1)).thenReturn(year);

	mockMvc.perform(post("/years/delete").param("id", "1"))
	.andExpect(view().name("redirect:/years"))
	.andExpect(status().is(302));
	verify(yearService).delete(year);
    }

    private List<Year> buildYaers() {
	return Arrays.asList(Year.builder().id(1).name("year1").build(), Year.builder().id(2).name("year2").build());
    }

    private List<Subject> buildSubjects() {
	return Arrays.asList(Subject.builder().id(1).name("subjecet1").build(),
		Subject.builder().id(2).name("subjecet2").build());
    }

}
