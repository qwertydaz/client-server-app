package work.mcdermott.client.server.app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import static work.mcdermott.client.server.app.consts.ClientServerConstants.DISCONNECTED_FROM_SERVER;
import static work.mcdermott.client.server.app.consts.ClientServerConstants.DIVIDER;
import static work.mcdermott.client.server.app.consts.ClientServerConstants.EXIT;
import static work.mcdermott.client.server.app.consts.ClientServerConstants.PORT;
import static work.mcdermott.client.server.app.consts.ClientServerConstants.SENDING_REQUESTS;
import static work.mcdermott.client.server.app.util.DisplayUtil.displayConnectedServer;
import static work.mcdermott.client.server.app.util.DisplayUtil.displayErrorMessage;
import static work.mcdermott.client.server.app.util.DisplayUtil.displayMessages;
import static work.mcdermott.client.server.app.util.DisplayUtil.displayReceivedResponse;
import static work.mcdermott.client.server.app.util.DisplayUtil.displaySentRequest;
import static work.mcdermott.client.server.app.util.ResponseRequestUtil.handleMessage;

public class Client {

	private static final List<String> names = List.of("Sandra", "Alex", "Ringo");
	private static final int NUM_OF_NAMES = names.size();

	private static void sendMessages(Socket socket) throws IOException, ClassNotFoundException, InterruptedException {
		try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
			oos.flush();
			try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
				for (int i = 0; i <= NUM_OF_NAMES; i++) {

					// prepares request
					String request;
					if (i == NUM_OF_NAMES) request = EXIT; // no more names to send
					else request = names.get(i);

					// sends request to server
					oos.writeObject(request);
					oos.flush();
					displaySentRequest(request);

					if (request.equals(EXIT)) break; // no more names to send (exit loop)

					// receives response from server
					String message = handleMessage(ois.readObject());
					displayReceivedResponse(message);

					// closes resources and wait before sending next request
					Thread.sleep(300);
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		try {
			// finds host and connects to server
			InetAddress host = InetAddress.getLocalHost();
			Socket socket = new Socket(host.getHostName(), PORT);
			displayConnectedServer(host.getHostName());
			displayMessages(DIVIDER);

			// sends messages to server
			displayMessages(SENDING_REQUESTS);
			sendMessages(socket);

			// disconnects from server
			socket.close();
		}
		catch (IOException | ClassNotFoundException e) {
			displayErrorMessage(e.getMessage());
		}
		finally {
			displayMessages("", DIVIDER, DISCONNECTED_FROM_SERVER);
		}
	}
}
