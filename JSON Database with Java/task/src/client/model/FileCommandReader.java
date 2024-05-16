package client.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.imageio.IIOException;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileCommandReader {
private static final String FILE_PATH = System.getProperty("user.dir") + File.separator +
		"src" + File.separator +
		"client" + File.separator +
		"data";
//private static final String FILE_PATH = System.getProperty("user.dir") + File.separator +
//		"JSON Database with Java" + File.separator +
//		"task" + File.separator +
//		"src" + File.separator +
//		"client" + File.separator +
//		"data";




	public static JsonObject loadFromFile(String filename) {
		try {
			String path = FILE_PATH + File.separator + filename;
			File file = new File(path);
			if (file.exists() && !Files.readString(Paths.get(path)).isBlank()) {
				return JsonParser.parseReader(new FileReader(file)).getAsJsonObject();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonObject();
	}

}
