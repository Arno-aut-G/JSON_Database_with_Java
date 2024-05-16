package server.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import server.repository.JsonRepository;

public class SetCommand implements Command {

	private final JsonElement key;

	private final JsonElement value;

	private final JsonRepository jsonRepository;

	public SetCommand(JsonElement key, JsonElement value, JsonRepository jsonRepository) {
		this.key = key;
		this.value = value;
		this.jsonRepository = jsonRepository;
	}

	@Override
	public Response execute() {
		return jsonRepository.setMethod(key, value);
	}
}
