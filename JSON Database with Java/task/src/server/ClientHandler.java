package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import server.model.Response;
import server.repository.JsonRepository;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler implements Runnable {
	private final Socket socket;
	private final MessageServer messageServer;
	private final ServerSocket server;
	private final JsonRepository jsonRepository = new JsonRepository();

	private final Gson gson = new Gson();

	public ClientHandler(ServerSocket server, Socket socket, MessageServer messageServer) {
		this.socket = socket;
		this.messageServer = messageServer;
		this.server = server;
	}

	@Override
	public void run() {
		try (DataInputStream input = new DataInputStream(socket.getInputStream());
				 DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
			String inputJson = input.readUTF(); // Read a message from the client
			JsonObject jsonCommand = JsonParser.parseString(inputJson).getAsJsonObject();

			Controller controller = new Controller(jsonCommand, jsonRepository);
						controller.setCommand();
						Response response = controller.executeCommand();
						output.writeUTF(gson.toJson(response));

			if (jsonCommand.get("type").getAsString().equals("exit")) {
				messageServer.getRunning().set(false);
				server.close();
			}
		} catch (IOException ignored) {}
	}
}
