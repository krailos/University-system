package ua.com.foxminded.krailo.university.util;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class PropertyLoader {

    private static Properties properties = new Properties();

    public static String getProperties(String name) {
	if (properties.isEmpty()) {
	    try (FileReader fileReader = new FileReader(
		    new File(PropertyLoader.class.getClassLoader().getResource("jdbc.properties").getPath()));) {
		properties.load(fileReader);
	    } catch (Exception e) {
		e.printStackTrace();
		new RuntimeException(e);
	    }
	}
	return properties.getProperty(name);
    }

}
