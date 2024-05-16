package server.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import server.repository.JsonRepository;

public class DeleteCommand implements Command {
		private final JsonElement key;

		private final JsonRepository jsonRepository;

		public DeleteCommand(JsonElement key, JsonRepository jsonRepository) {
			this.key = key;
			this.jsonRepository = jsonRepository;
		}

		@Override
		public Response execute() {
			return jsonRepository.deleteMethod(key);
		}
}
