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

import ua.com.foxminded.krailo.university.controllers.exception.ControllerExceptionHandler;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.service.GroupService;
import ua.com.foxminded.krailo.university.util.Paging;

@ExtendWith(MockitoExtension.class)
class GroupControllerTest {

    @Mock
    private GroupService groupService;
    @InjectMocks
    private GroupController groupController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
	mockMvc = standaloneSetup(groupController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    void WhenGetAllGroups_ThenFirstPageGroupsReturned() throws Exception {
	List<Group> expected = buildGroups();
	Paging paging = new Paging(2, 1, 4);
	when(groupService.getQuantity()).thenReturn(4);
	when(groupService.getByPage(paging)).thenReturn(expected);

	mockMvc.perform(get("/groups").param("pageSize", "2")).andExpect(view().name("groups/all"))
		.andExpect(status().isOk()).andExpect(model().attribute("groups", expected));
    }

    @Test
    void whenGetAllroupsWithParameters_thenRightPageWithGroupsReturned() throws Exception {
	List<Group> expected = buildGroups();
	Paging paging = new Paging(2, 3, 6);
	when(groupService.getByPage(paging)).thenReturn(expected);
	when(groupService.getQuantity()).thenReturn(6);

	mockMvc.perform(get("/groups?pageSize=2&pageNumber=3")).andExpect(view().name("groups/all"))
		.andExpect(status().isOk()).andExpect(model().attribute("groups", expected))
		.andExpect(model().attribute("pageQuantity", 3));
    }

    @Test
    void givenGroupId_WhenGetGroup_ThenGroupGot() throws Exception {
	Group expected = buildGroups().get(0);
	when(groupService.getById(1)).thenReturn(expected);

	mockMvc.perform(get("/groups/1")).andExpect(view().name("groups/group")).andExpect(status().isOk())
		.andExpect(model().attribute("group", expected));
    }

    @Test
    void givenWrongGroupId_whenGetGroup_thenEntityNotFoundExceptionThrown() throws Exception {
	when(groupService.getById(1)).thenThrow(new EntityNotFoundException("entity not exist"));

	mockMvc.perform(get("/groups/1")).andExpect(view().name("errors/error"))
		.andExpect(model().attribute("message", "entity not exist"));
    }

    private List<Group> buildGroups() {
	return Arrays.asList(Group.builder().id(1).name("group1").build(),
		Group.builder().id(2).name("group2").build());
    }

}
