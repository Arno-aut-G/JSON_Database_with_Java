package server.repository;

import static server.model.ReturnEnum.ERROR;
import static server.model.ReturnEnum.KEYERROR;
import static server.model.ReturnEnum.NOTFOUND;
import static server.model.ReturnEnum.OK;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import server.model.Response;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class JsonRepository {
	private final FileHandler fileHandler;

	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private final Lock readLock = lock.readLock();
	private final Lock writeLock = lock.writeLock();

	private static final JsonElement ERASE = new JsonPrimitive("erase");

	public JsonRepository() {
		this.fileHandler = new FileHandler();
	}

	//	TODO ENSURE THAT ALL INPUTS ARE TURNED INTO JSONARRAYS (also for JSONNULL?)

	public Response getMethod(JsonElement key) {
		if (null == key || key.isJsonNull()) {
			return new Response(ERROR, KEYERROR.getLabel());
		}
		JsonArray keyArray = ensureJsonArray(key);
		readLock.lock();
		try {
			JsonObject data = fileHandler.loadFromFile();
			JsonElement retrievedData = getData(keyArray, data);
			return retrievedData != null ? new Response(OK, retrievedData) : new Response(ERROR, NOTFOUND.getLabel());
		} finally {
			readLock.unlock();
		}
	}

	public Response deleteMethod(JsonElement key) {
		if (null == key || key.isJsonNull()) {
			return new Response(ERROR, KEYERROR.getLabel());
		}
		JsonArray keyArray = ensureJsonArray(key);
		writeLock.lock();
		try {
			JsonObject data = fileHandler.loadFromFile();
			setData(keyArray, data, ERASE);
			fileHandler.saveToFile(data);
			return new Response(OK);
		} finally {
			writeLock.unlock();
		}
	}

	public Response setMethod(JsonElement key, JsonElement value) {
		if (null == key || key.isJsonNull()) {
			return new Response(ERROR, KEYERROR.getLabel());
		}
		JsonArray keyArray = ensureJsonArray(key);
		writeLock.lock();
		try {
			JsonObject data = fileHandler.loadFromFile();
			setData(keyArray, data, value);
			fileHandler.saveToFile(data);
			return new Response(OK);
		} finally {
			writeLock.unlock();
		}
	}

	public JsonElement getData(JsonArray key, JsonObject data) {
		return getDataByPath(data, key, 0);
	}

	public boolean setData(JsonArray key, JsonObject data, JsonElement value) {
		return setDataByPath(data, key, 0, value);
	}

	private JsonElement getDataByPath(JsonElement current, JsonArray path, int index) {
		if (index >= path.size()) {
			return current;
		}

		String key = path.get(index).getAsString();

		if (current instanceof JsonObject jsonObject) {
			if (!jsonObject.has(key)) {
				return null; // Path not found
			}
			return getDataByPath(jsonObject.get(key), path, index + 1);
		}

		return null; // Path not found
	}

	private boolean setDataByPath(JsonElement current, JsonArray path, int index, JsonElement value) {
		if (index >= path.size()) {
			return false;
		}

		String key = path.get(index).getAsString();

		if (current instanceof JsonObject jsonObject) {
			if (index == path.size() - 1) {
				if (ERASE.equals(value)) {
					jsonObject.remove(key);
				} else {
					jsonObject.add(key, value);
				}
				return true;
			} else {
				if (!jsonObject.has(key)) {
					jsonObject.add(key, new JsonObject());
				}
				JsonElement child = jsonObject.get(key);
				setDataByPath(child, path, index + 1, value);
			}
		}

		return false;
	}

	public JsonArray ensureJsonArray(JsonElement jsonElement) {
		if (jsonElement.isJsonArray()) {
			return jsonElement.getAsJsonArray();
		} else if (jsonElement.isJsonPrimitive()) {
			JsonArray jsonArray = new JsonArray();
			jsonArray.add(jsonElement.getAsJsonPrimitive());
			return jsonArray;
		}
		return null;
	}


}
