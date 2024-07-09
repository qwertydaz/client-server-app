package work.mcdermott.client.server.app;

import work.mcdermott.client.server.app.exception.ClientServerException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

import static work.mcdermott.client.server.app.consts.ClientServerConstants.AWAITING_REQUEST;
import static work.mcdermott.client.server.app.consts.ClientServerConstants.DIVIDER;
import static work.mcdermott.client.server.app.consts.ClientServerConstants.EXIT;
import static work.mcdermott.client.server.app.consts.ClientServerConstants.MESSAGE_TEMPLATE;
import static work.mcdermott.client.server.app.consts.ClientServerConstants.PORT;
import static work.mcdermott.client.server.app.consts.ClientServerConstants.SERVER_SHUTDOWN;
import static work.mcdermott.client.server.app.consts.ClientServerConstants.SERVER_STARTED;
import static work.mcdermott.client.server.app.util.DisplayUtil.displayErrorMessage;
import static work.mcdermott.client.server.app.util.DisplayUtil.displayMessages;
import static work.mcdermott.client.server.app.util.DisplayUtil.displayReceivedRequest;
import static work.mcdermott.client.server.app.util.DisplayUtil.displaySentResponse;
import static work.mcdermott.client.server.app.util.ResponseRequestUtil.handleMessage;

public class Server {

	private static Socket socket;
	private static ObjectInputStream ois;
	private static ObjectOutputStream oos;

	private static void closeResources() throws IOException {
		if (Objects.nonNull(ois) && Objects.nonNull(oos)) {
			ois.close();
			oos.close();
		}
	}

	private static void writeMessage(String request) throws IOException {
		String message = String.format(MESSAGE_TEMPLATE, request.toUpperCase());

		oos.writeObject(message);
		oos.flush();

		displaySentResponse(message);
	}

	private static void shutdownServer() throws IOException {
		closeResources();
		if (Objects.nonNull(socket)) socket.close();
	}

	private static void runServer() throws IOException, ClassNotFoundException {
		// server is started
		displayMessages(SERVER_STARTED, DIVIDER);
		final ServerSocket server = new ServerSocket(PORT);
		boolean isServerRunning = true;

		while (isServerRunning) {
			displayMessages(AWAITING_REQUEST);

			// listens for client requests
			socket = server.accept();
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());

			boolean isClientConnected = true;
			while (isClientConnected) {
				try {
					// reads request from client
					String request = handleMessage(ois.readObject());
					displayReceivedRequest(request);

					// exits server if client sends exit request
					if (request.equalsIgnoreCase(EXIT)) {
						isClientConnected = false;
						isServerRunning = false;
						continue;
					}

					// sends response to client
					writeMessage(request);
				}
				catch (ClientServerException cse) {
					displayMessages(cse.getMessage());
				}
			}
		}

		closeResources();
	}

	public static void main(String[] args) {
		try {
			// runs server
			runServer();

			// loop has ended and server is shutdown
			shutdownServer();
		}
		catch (IOException | ClassNotFoundException e) {
			displayErrorMessage(e.getMessage());
		}
		finally {
			displayMessages("", DIVIDER, SERVER_SHUTDOWN);
		}
	}
}
