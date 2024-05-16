package client.model;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;

public class CommandParser {

	ClientParameters clientParameters = new ClientParameters();

	public CommandParser(String[] args) {
		JCommander.newBuilder()
				.addObject(clientParameters)
				.build()
				.parse(args);
	}

	public String serializeToJson() {
		return new Gson().toJson(clientParameters);
	}

	public boolean hasFileInput() {
		return this.clientParameters.getFilename() != null;
	}

	public ClientParameters getClientParameters() {
		return clientParameters;
	}
}
