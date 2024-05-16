package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


public class MessageServer {

	private final String ADDRESS = "127.0.0.1";
	private final int PORT = 23456;

	private AtomicBoolean running = new AtomicBoolean(true);

	public void listenAndProcess() {
		try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
			System.out.println("Server started!");
			ExecutorService executorService = Executors.newFixedThreadPool(5);


			while (!Thread.interrupted() && running.get()) {
				Socket socket = acceptClient(server);
				if (socket == null) {
					executorService.shutdownNow();
					break;
				}
				executorService.submit(() -> new ClientHandler(server, socket, this).run());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Socket acceptClient(ServerSocket server) {
		try {
			return server.accept();
		} catch (IOException e) {
			return null;
		}
	}

	public AtomicBoolean getRunning() {
		return running;
	}
}

