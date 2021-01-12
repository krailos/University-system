package ua.com.foxminded.krailo.university.util;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;

public class Utility {

    public static FileReader getFileReader(String fileName) {
	FileReader fileReader = null;
	try {
	    fileReader = new FileReader(getFileByNmae(fileName));
	} catch (Exception e) {
	    throw new RuntimeException("file \"" + fileName + "\" not found");
	}
 
	return fileReader;
    }

    public static File getFileByNmae(String fileName) {
	File file = null;
	try {
	    file = Paths.get(Utility.class.getClassLoader().getResource(fileName).toURI()).toFile();
	} catch (Exception e) {
	    throw new RuntimeException("file \"" + fileName + "\" not found");
	}
	return file;
    }

}
