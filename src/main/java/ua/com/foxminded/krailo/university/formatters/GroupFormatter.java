package ua.com.foxminded.krailo.university.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import ua.com.foxminded.krailo.university.model.Group;

public class GroupFormatter implements Formatter<Group> {

    @Override
    public String print(Group group, Locale locale) {
	return String.valueOf(group.getId());
    }

    @Override
    public Group parse(String groupId, Locale locale) throws ParseException {
	return Group.builder().id(Integer.valueOf(groupId)).build();
    }

}
