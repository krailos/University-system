package ua.com.foxminded.krailo.university.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;

public class ScriptRunnerDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
	return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    public void runScript(String fileName) {
	String sql = "";
	try {
	    sql = Files.lines(Paths.get(this.getClass().getClassLoader().getResource(fileName).toURI()))
		    .collect(Collectors.joining());
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (URISyntaxException e) {
	    e.printStackTrace();
	}
	getJdbcTemplate().execute(sql);
    }

}
