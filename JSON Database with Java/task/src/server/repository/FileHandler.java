package server.repository;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileHandler {

	private static final String dbFilePath = System.getProperty("user.dir") + File.separator +
			"src" + File.separator +
			"server" + File.separator +
			"data";
	private static final String path = dbFilePath + File.separator + "db.json";

	public JsonObject loadFromFile() {
		try {
			File file = new File(path);
			if (file.exists() && !Files.readString(Paths.get(path)).isBlank()) {
				return JsonParser.parseReader(new FileReader(file)).getAsJsonObject();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonObject();
	}

	public void saveToFile(JsonObject jsonObject) {
		try (FileWriter writer = new FileWriter(path)) {
			writer.write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
