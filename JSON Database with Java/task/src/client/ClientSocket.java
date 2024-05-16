package client;

import client.model.ClientParameters;
import client.model.CommandParser;
import client.model.FileCommandReader;
import com.beust.jcommander.JCommander;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSocket {

	private final String ADDRESS = "127.0.0.1";
	private final int PORT = 23456;

	public void process(String[] args) {
		try (Socket socket = new Socket(InetAddress.getByName(ADDRESS), PORT);
				 DataInputStream input = new DataInputStream(socket.getInputStream());
				 DataOutputStream output = new DataOutputStream(socket.getOutputStream());
				 ) {
			System.out.println("Client started!");

			CommandParser commandParser = new CommandParser(args);

			String json;

			if (commandParser.hasFileInput()) {
				String filename = commandParser.getClientParameters().getFilename();
				json = FileCommandReader.loadFromFile(filename).toString();
			} else {
				json = commandParser.serializeToJson();
			}

			output.writeUTF(json);
			System.out.println("Sent: " + json);

			String receivedMsg = input.readUTF();
			System.out.println("Received: " + receivedMsg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
