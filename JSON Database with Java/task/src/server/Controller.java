package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import server.model.Command;
import server.model.DeleteCommand;
import server.model.ExitCommand;
import server.model.GetCommand;
import server.model.Response;
import server.model.SetCommand;
import server.repository.JsonRepository;

public class Controller {

	private final JsonObject jsonObject;

	private final JsonRepository jsonRepository;

	private Command command;

	public Controller(JsonObject jsonObject, JsonRepository jsonRepository) {
		this.jsonObject = jsonObject;
		this.jsonRepository = jsonRepository;
	}

	public void setCommand() {
		switch (jsonObject.get("type").getAsString()) {
		  case "get" -> this.command = new GetCommand(jsonObject.get("key"), this.jsonRepository);
			case "delete" -> this.command = new DeleteCommand(jsonObject.get("key"), this.jsonRepository);
			case "set" -> this.command = new SetCommand(jsonObject.get("key"), jsonObject.get("value"),
					this.jsonRepository);
			case "exit" -> this.command = new ExitCommand();
		}
	}

	public Response executeCommand() {
		return this.command.execute();
	}

}
