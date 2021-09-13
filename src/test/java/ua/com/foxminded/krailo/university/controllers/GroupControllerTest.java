package ua.com.foxminded.krailo.university.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

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
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Year;
import ua.com.foxminded.krailo.university.service.GroupService;
import ua.com.foxminded.krailo.university.service.YearService;

@ExtendWith(MockitoExtension.class)
class GroupControllerTest {

    @Mock
    private GroupService groupService;
    @Mock
    private YearService yearService;
    @InjectMocks
    private GroupController groupController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
	mockMvc = standaloneSetup(groupController).setControllerAdvice(new ControllerExceptionHandler())
		.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    }

    @Test
    void whenGetAllGroups_thenFirstPageGroupsReturned() throws Exception {
	int pageNo = 0;
	int pageSize = 3;
	int allGroupsCount = 6;
	List<Group> groups = new ArrayList<>();
	groups.addAll(buildGroups());
	Pageable pageable = PageRequest.of(pageNo, pageSize);
	Page<Group> expected = new PageImpl<>(groups, pageable, allGroupsCount);
	when(groupService.getSelectedPage(pageable)).thenReturn(expected);

	mockMvc.perform(get("/groups")
		.param("page", "0")
		.param("size", "3"))
		.andExpect(view().name("groups/all"))
		.andExpect(model().attribute("groupsPage", expected));
    }

    @Test
    void whenGetAllroupsWithParameters_thenRightPageWithGroupsReturned() throws Exception {
	int pageNo = 1;
	int pageSize = 3;
	int allGroupsCount = 6;
	List<Group> groups = new ArrayList<>();
	groups.addAll(buildGroups());
	Pageable pageable = PageRequest.of(pageNo, pageSize);
	Page<Group> expected = new PageImpl<>(groups, pageable, allGroupsCount);
	when(groupService.getSelectedPage(pageable)).thenReturn(expected);

	mockMvc.perform(get("/groups")
		.param("page", "1")
		.param("size", "3"))
		.andExpect(view().name("groups/all"))
		.andExpect(model().attribute("groupsPage", expected));
    }

    @Test
    void givenGroupId_whenGetGroup_thenGroupGot() throws Exception {
	Group expected = buildGroups().get(0);
	when(groupService.getById(1)).thenReturn(expected);

	mockMvc.perform(get("/groups/{id}", "1"))
		.andExpect(view().name("groups/group"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("group", expected));
    }

    @Test
    void givenWrongGroupId_whenGetGroup_thenEntityNotFoundExceptionThrown() throws Exception {
	when(groupService.getById(1)).thenThrow(new EntityNotFoundException("entity not exist"));

	mockMvc.perform(get("/groups/{id}", "1"))
		.andExpect(view().name("errors/error"))
		.andExpect(model().attribute("message", "entity not exist"));
    }

    @Test
    void whenCreateGroup_thenGroupWithYearsReturned() throws Exception {
	List<Year> years = buildYears();
	when(yearService.getAll()).thenReturn(years);

	mockMvc.perform(get("/groups/create"))
		.andExpect(view().name("groups/edit")).andExpect(status().isOk())
		.andExpect(model().attribute("years", years))
		.andExpect(model().attributeExists("group"));
    }

    @Test
    void givenNewGroup_whenSaveGroup_thenGroupSaved() throws Exception {
	Group group = buildGroups().get(0);
	group.setId(0);

	mockMvc.perform(post("/groups/save")
		.flashAttr("group", group))
		.andExpect(view().name("redirect:/groups"))
		.andExpect(status().is(302));

	verify(groupService).create(group);
    }

    @Test
    void givenUpdatedGroup_whenUpdateGroup_thenGroupUpdated() throws Exception {
	Group group = buildGroups().get(0);

	mockMvc.perform(post("/groups/save")
		.flashAttr("group", group))
		.andExpect(view().name("redirect:/groups"))
		.andExpect(status().is(302));

	verify(groupService).update(group);
    }

    @Test
    void givenGroupId_whenEditGroup_thenGroupReturnedToEdite() throws Exception {
	List<Year> years = buildYears();
	when(yearService.getAll()).thenReturn(years);
	Group group = buildGroups().get(0);
	when(groupService.getById(1)).thenReturn(group);

	mockMvc.perform(get("/groups/edit/{id}", "1"))
		.andExpect(view().name("groups/edit"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("group", group))
		.andExpect(model().attribute("years", years));
    }

    @Test
    void whenDeleteGroup_thenGroupDeleted() throws Exception {
	Group group = buildGroups().get(0);
	when(groupService.getById(1)).thenReturn(group);

	mockMvc.perform(post("/groups/delete").param("id", "1"))
		.andExpect(view().name("redirect:/groups"))
		.andExpect(status().is(302));

	verify(groupService).delete(group);
    }

    private List<Group> buildGroups() {
	return Arrays.asList(Group.builder().id(1).name("group1").build(),
		Group.builder().id(2).name("group2").build());
    }

    private List<Year> buildYears() {
	return Arrays.asList(Year.builder().id(1).name("year1").build(), Year.builder().id(2).name("year2").build());
    }

}
