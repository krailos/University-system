package ua.com.foxminded.krailo.university.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ScriptRunnerDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
	jdbcTemplate.execute(sql);
    }

}
