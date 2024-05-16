package server.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import server.repository.JsonRepository;

public class GetCommand implements Command {

	private final JsonElement key;

	private final JsonRepository jsonRepository;
	public GetCommand(JsonElement key, JsonRepository jsonRepository) {
		this.key = key;
		this.jsonRepository = jsonRepository;
	}
	@Override
	public Response execute() {
		return jsonRepository.getMethod(key);
	}
}
