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

import ua.com.foxminded.krailo.university.model.Vocation;
import ua.com.foxminded.krailo.university.model.VocationKind;
import ua.com.foxminded.krailo.university.service.VocationService;

@ExtendWith(MockitoExtension.class)
class VocationControllerTest {

    @Mock
    private VocationService vocationService;
    @InjectMocks
    private VocationController vocationController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
	mockMvc = standaloneSetup(vocationController).build();
    }

    @Test
    void WhenGetAllVocations_ThenAllVocationsReturned() throws Exception {
	List<Vocation> expected = getVocationsForTest();
	when(vocationService.getAll()).thenReturn(expected);

	mockMvc.perform(get("/vocations/all")).andExpect(view().name("vocations/vocationsAll"))
		.andExpect(status().isOk()).andExpect(model().attribute("vocations", expected));

    }

    @Test
    void givenVocationId_WhenGetVocation_ThenVocationGot() throws Exception {
	Vocation expected = getVocationsForTest().get(0);
	when(vocationService.getById(1)).thenReturn(expected);

	mockMvc.perform(get("/vocations/findVocationById/1")).andExpect(view().name("vocations/vocationView"))
		.andExpect(status().isOk()).andExpect(model().attribute("vocation", expected));

    }

    private List<Vocation> getVocationsForTest() {
	return Arrays.asList(Vocation.builder().id(1).kind(VocationKind.GENERAL).build(),
		Vocation.builder().id(2).kind(VocationKind.PREFERENTIAL).build());
    }

}
