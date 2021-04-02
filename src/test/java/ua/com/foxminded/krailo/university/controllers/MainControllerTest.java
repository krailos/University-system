package ua.com.foxminded.krailo.university.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
class MainControllerTest {

    @InjectMocks
    private MainController mainController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
	mockMvc = standaloneSetup(mainController).build();
    }

    @Test
    void whenGetUniversity_thenUniversityReturned() throws Exception {

	mockMvc.perform(get("/")).andExpect(view().name("index")).andExpect(status().isOk());
    }

}
