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

import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.service.GroupService;
import ua.com.foxminded.krailo.university.service.StudentService;

@ExtendWith(MockitoExtension.class)
class GroupControllerTest {

    @Mock
    private GroupService groupService;
    @Mock
    private StudentService studentService;
    @InjectMocks
    private GroupController groupController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
	mockMvc = standaloneSetup(groupController).build();
    }

    @Test
    void WhenGetAllGroups_ThenAllGroupsReturned() throws Exception {
	List<Group> expected = buildGroups();
	when(groupService.getAll()).thenReturn(expected);

	mockMvc.perform(get("/groups")).andExpect(view().name("groups/all")).andExpect(status().isOk())
		.andExpect(model().attribute("groups", expected));

    }

    @Test
    void givenGroupId_WhenGetGroup_ThenGroupGot() throws Exception {
	Group expected = buildGroups().get(0);
	when(groupService.getById(1)).thenReturn(expected);
	when(studentService.getByGroupId(1))
		.thenReturn(Arrays.asList(Student.builder().id(1).firstName("Jon").build()));

	mockMvc.perform(get("/groups/1")).andExpect(view().name("groups/group"))
		.andExpect(status().isOk()).andExpect(model().attribute("group", expected));

    }

    private List<Group> buildGroups() {
	return Arrays.asList(
		Group.builder().id(1).name("group1")
			.students(Arrays.asList(Student.builder().id(1).firstName("Jon").build())).build(),
		Group.builder().id(2).name("group2")
			.students(Arrays.asList(Student.builder().id(2).firstName("Tom").build())).build());
    }

}
