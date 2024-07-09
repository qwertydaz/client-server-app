package work.mcdermott.client.server.app.util;

import static work.mcdermott.client.server.app.consts.ClientServerConstants.CONNECTED_TO_SERVER;
import static work.mcdermott.client.server.app.consts.ClientServerConstants.ERROR_TEMPLATE;
import static work.mcdermott.client.server.app.consts.ClientServerConstants.REQUEST_RECEIVED;
import static work.mcdermott.client.server.app.consts.ClientServerConstants.REQUEST_SENT;
import static work.mcdermott.client.server.app.consts.ClientServerConstants.RESPONSE_RECEIVED;
import static work.mcdermott.client.server.app.consts.ClientServerConstants.RESPONSE_SENT;

public class DisplayUtil {

	private DisplayUtil() {}

	public static void displayMessages(String ...messages) {
		for (String message : messages) {
			displayText(message);
		}
	}


	// Error messages
	public static void displayErrorMessage(String errorMessage) {
		displayFormattedText(ERROR_TEMPLATE, errorMessage);
	}


	// Client
	public static void displayConnectedServer(String hostName) {
		displayFormattedText(CONNECTED_TO_SERVER, hostName);
	}

	public static void displaySentRequest(String request) {
		displayFormattedText(REQUEST_SENT, request);
	}

	public static void displayReceivedResponse(String response) {
		displayFormattedText(RESPONSE_RECEIVED, response);
	}


	// Server
	public static void displayReceivedRequest(String request) {
		displayFormattedText(REQUEST_RECEIVED, request);
	}

	public static void displaySentResponse(String response) {
		displayFormattedText(RESPONSE_SENT, response);
	}


	// Private methods
	private static void displayFormattedText(String prefix, String text) {
		System.out.println(String.format(prefix, text));
	}

	private static void displayText(String text) {
		System.out.println(text);
	}
}
