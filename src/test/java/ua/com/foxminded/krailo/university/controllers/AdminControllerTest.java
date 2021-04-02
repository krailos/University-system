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
class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
	mockMvc = standaloneSetup(adminController).build();
    }

    @Test
    void whenGetAdmin_thenAdminPageReturned() throws Exception {

	mockMvc.perform(get("/admin")).andExpect(view().name("adminPage")).andExpect(status().isOk());
    }

}
